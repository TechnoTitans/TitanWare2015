package org.usfirst.frc.team1683.robot.drivetrain;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.TechnoTitan;
import org.usfirst.frc.team1683.robot.main.DriverStation;

import edu.wpi.first.wpilibj.Timer;

/**
 * 
 * @author Animesh Koratana
 *
 */
public class MotorGroup implements Runnable{
	String groupName;
	List<Motor> motors;
	Encoder encoder;
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
		if (TechnoTitan.POSTENCODERVALUES){
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
		if (TechnoTitan.POSTENCODERVALUES){
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
			new Thread(mover).start();
		}
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
			motor.stop();
		} 
	}
	@Override
	public void run() {
		while (true){
			Timer.delay(0.25);
			if (encoder != null){
				if (TechnoTitan.POSTENCODERVALUES){
					System.out.println(groupName + encoder.getDisplacement(encoder.getDistancePerPulse()));
				}
				DriverStation.sendData(groupName , encoder.getDisplacement(encoder.getDistancePerPulse()));
			}
			for(Motor motor: motors){
				if (motor.hasEncoder()){
					String name = "Encoder for motor "+ motor.getChannel();
					if (TechnoTitan.debug){
						System.out.println(name + motor.getEncoder().getDisplacement(encoder.getDistancePerPulse()));
					}
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
		@Override
		public void run() {
			encoder.reset();
			double speed;
			if (distanceInMeters>0)
				speed = HWR.MEDIUM_SPEED;
			else
				speed = -HWR.MEDIUM_SPEED;
			while (Math.abs(encoder.getDistanceMeters()) < distanceInMeters){
				for (Motor motor: motors){
					motor.set(speed);
				} 
			}
			stop();
			encoder.reset();
		}

	}

}
