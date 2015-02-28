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
	List<PIDController> pidControllers;
	MotorMover mover;
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
		mover = new MotorMover();
		if (TechnoTitan.postEncoder){
			new Thread(this, "EncoderPost").start();
		}
		pidControllers = null;
	}
	/**
	 * Constructor
	 * @param channelNumbers
	 * @param motorType
	 * @param inverseDirection
	 * @param encoder
	 */

	public MotorGroup(String groupName, int[] channelNumbers, Class<Motor> motorType, boolean inverseDirection, Encoder encoder){
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
		mover = new MotorMover();
		if (TechnoTitan.postEncoder){
			new Thread(this, "EncoderPost").start();
		}
		pidControllers = null;
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
	
	public void enablePIDController(double p, double i, double d, Encoder encoder){
		pidControllers = new ArrayList<PIDController>();
		for (int index = 0; index < motors.size(); index++){
			PIDController pidControl = new PIDController(p, i, d, encoder, motors.get(index));
			pidControl.setOutputRange(0, 1);
			pidControl.setPercentTolerance(HWR.PID_PERCENT_TOLERANCE);
			pidControllers.add(pidControl);
		}
	}
	
	public Encoder getEncoder(){
		return this.encoder;
	}

	public void moveDistanceInches(double distanceInInches){
		if (encoder != null){
			mover.setDistanceInMeters(distanceInInches * 0.0254);
			currentThread = new Thread(mover);
			currentThread.setPriority(Thread.MAX_PRIORITY);
			currentThread.start();
		}
	}
	
	public void enableBrakeMode(boolean enable){
		if (TalonSRX.class.equals(motors.get(0).getClass())){
			for (Motor motor: motors){
				motor.enableBrakeMode(enable);
			}
		}
	}
	
	public Thread getCurrentThread(){
		return currentThread;
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
			motor.enableBrakeMode(true);
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
		double targetLocation;
		double initialLocation;
		double home = 0.0;

		
		public void setDistanceInMeters(double distanceInMeters){
			this.distanceInMeters = distanceInMeters;
			initialLocation = encoder.getDistanceMeters();
			this.targetLocation = initialLocation + distanceInMeters;
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			double speed;
			if (pidControllers == null){
				if (targetLocation > initialLocation){
					speed = HWR.MEDIUM_SPEED;
				}else{
					speed = -HWR.MEDIUM_SPEED;
				}
				while (Math.abs(Math.abs(initialLocation) - Math.abs(encoder.getDisplacement(encoder.getDistancePerPulse())))
						< Math.abs(distanceInMeters)){
					for (Motor motor: motors){
						motor.set(speed);
					} 
				}
			}else{
				for (PIDController pidControl : pidControllers){
					pidControl.enable();
					pidControl.setSetpoint(targetLocation);
				}
				while (!pidControllers.get(0).onTarget()){
					Thread.yield();
				}
				for (PIDController pidControl : pidControllers){
					pidControl.disable();
				}
			}
			stop();
			currentThread.notifyAll();
			currentThread.destroy();
		}

	}

}
