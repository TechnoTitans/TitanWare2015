package org.usfirst.frc.team1683.robot.drivetrain;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.TechnoTitan;
import org.usfirst.frc.team1683.robot.main.DriverStation;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author Animesh Koratana
 *
 */
public class MotorGroup implements Runnable{
	String groupName;
	Thread currentThread;
	List<Motor> motors;
	Encoder encoder;
	PIDController PIDcontrol;
	/**
	 * Constructor
	 * @param channelNumbers
	 * @param motorType
	 * @param inverseDirection
	 */
	public MotorGroup(String groupName, int[] channelNumbers, Class<Motor> motorType, boolean inverseDirection)  {
		this.groupName = groupName;
		motors = new ArrayList<Motor>();
		for (int i = 0; i < channelNumbers.length; i++) {
			int j = channelNumbers[i];
			if (motorType.equals(Talon.class)){
				motors.add(new Talon(j, inverseDirection));
			}else if (motorType.equals(TalonSRX.class)){
				motors.add(new TalonSRX(j, inverseDirection));
			}
		}
		if (TechnoTitan.postEncoder){
			new Thread(this, "EncoderPost").start();
		}
	}
	/**
	 * Constructor
	 * @param channelNumbers
	 * @param motorType
	 * @param inverseDirection
	 * @param encoder
	 */

	public MotorGroup(String groupName, int[] channelNumbers, Class<Motor> motorType, boolean inverseDirection, Encoder encoder)
	{
		this.groupName = groupName;
		motors = new ArrayList<Motor>();
		for (int i = 0; i < channelNumbers.length; i++) {
			int j = channelNumbers[i];
			if (motorType.equals(Talon.class)){
				motors.add(new Talon(j, inverseDirection));
			}else if (motorType.equals(TalonSRX.class)){
				motors.add(new TalonSRX(j, inverseDirection));
			}
		}
		this.encoder = encoder;
		if (TechnoTitan.postEncoder){
			new Thread(this, "EncoderPost").start();
		}
	}
	
	public MotorGroup(String groupName, int[] channelNumbers, Class<Motor> motorType, boolean inverseDirection, 
			Encoder encoder, double P, double I, double D, double F, int index)
	{
		this.groupName = groupName;
		motors = new ArrayList<Motor>();
		for (int i = 0; i < channelNumbers.length; i++) {
			int j = channelNumbers[i];
			if (motorType.equals(Talon.class)){
				motors.add(new Talon(j, inverseDirection));
				if (j==index){
					PIDcontrol = new PIDController(P, I, D, F, encoder, new Talon(j, inverseDirection));
				}
			}else if (motorType.equals(TalonSRX.class)){
				motors.add(new TalonSRX(j, inverseDirection));
				if (j==index){
					PIDcontrol = new PIDController(P, I, D, F, encoder, new TalonSRX(j, inverseDirection));
				}
			}
		}
		this.encoder = encoder;
		if (TechnoTitan.postEncoder){
			new Thread(this, "EncoderPost").start();
		}
		
	}

	/**
	 * moves the robot a certain distance
	 * @param distanceInMeters
	 */
	public void moveDistance(double distanceInMeters){
		for (Motor motor: motors){
			motor.moveDistance(distanceInMeters);
		}
	}

	public void moveDistanceInches(double distanceInInches){
		if (encoder != null){
			MotorMover mover = new MotorMover(distanceInInches * 0.0254);
			currentThread = new Thread(mover);
			currentThread.start();
		}
	}
	
	public Thread getCurrentThread(){
		return currentThread;
	}
	
	public PIDController getPID(){
		return PIDcontrol;
	}
	/**
	 * moves the robot a certain amount of degrees 
	 * @param degrees
	 */
	public void moveDegrees(double degrees){
		for (Motor motor: motors){
			motor.moveDegrees(degrees);
		}
	}
	/**
	 * sets the speed of the motor
	 * @param speed
	 */
	public void set(double speed){
		for (Motor motor: motors){
			motor.set(speed);
		} 
	}
	/**
	 * stops the motor
	 */
	public void stop() {
		for (Motor motor: motors){
			motor.set(0);
		} 
	}
	
	@Override
	public void run() {
		while (true){
			Timer.delay(0.25);
			if (encoder != null){
				DriverStation.sendData(groupName , encoder.getDisplacement(encoder.getDistancePerPulse()));
			}
			for(Motor motor: motors){
				if (motor.hasEncoder()){
					String name = "Encoder for motor "+ motor.getChannel();
					DriverStation.sendData(name , motor.getEncoder().getDisplacement(encoder.getDistancePerPulse()));
				}
			}
		}

	}

	public class MotorMover implements Runnable{

		double distanceInMeters;

		public MotorMover(double distanceInMeters) {
			this.distanceInMeters = distanceInMeters;
		}
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			//synchronized(this){
			encoder.reset();
			double speed;
			if (distanceInMeters>0)
				speed = HWR.MEDIUM_SPEED;
			else
				speed = -HWR.MEDIUM_SPEED;
			while (Math.abs(encoder.getDisplacement(encoder.getDistancePerPulse())) < Math.abs(distanceInMeters)){
				for (Motor motor: motors){
					motor.set(speed);
					Timer.delay(0.5);
				} 
			}
			stop();
			encoder.reset();
			currentThread.notifyAll();
			//}
			currentThread.destroy();
		}

	}

}
