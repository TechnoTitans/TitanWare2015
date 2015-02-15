package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.Gyro;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
/**
 * 
 * @author Animesh Koratana & Seung-Seok
 *
 */
public class TankDrive extends DriveTrain{

	public MotorGroup left;
	public MotorGroup right;
	public Encoder leftEncoder;
	public Encoder rightEncoder;
	Gyro gyro;
	Thread currentThread;

	static final double	 kp=0.03;
	double waitTime=0.2;
	double startAngle;
	/**
	 * Constructor
	 * @param leftMotorInputs
	 * @param leftInverse
	 * @param rightMotorInputs
	 * @param rightInverse
	 * @param motorType
	 * @param gyroChannel
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TankDrive(int[] leftMotorInputs,boolean leftInverse, int[] rightMotorInputs, boolean rightInverse, Class motorType, int gyroChannel) {
		left = new MotorGroup("Left Drive",leftMotorInputs, motorType, leftInverse);
		right = new MotorGroup("Right Drive",rightMotorInputs, motorType, rightInverse);
		gyro = new Gyro(gyroChannel);
		startAngle = gyro.getAngle();
	}
	/**
	 * Constructor
	 * @param leftMotorInputs
	 * @param leftInverse
	 * @param rightMotorInputs
	 * @param rightInverse
	 * @param motorType
	 * @param gyroChannel
	 * @param leftChannelA
	 * @param leftChannelB
	 * @param rightChannelA
	 * @param rightChannelB
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TankDrive(int[] leftMotorInputs,boolean leftInverse, int[] rightMotorInputs, boolean rightInverse, 
			Class motorType, int gyroChannel, int leftChannelA, int leftChannelB, 
			int rightChannelA, int rightChannelB, double wheelDistancePerPulse,
			boolean leftReversesDirection, boolean rightReverseDirection) {
		leftEncoder = new Encoder(leftChannelA, leftChannelB, leftReversesDirection, wheelDistancePerPulse);
		rightEncoder = new Encoder(rightChannelA, rightChannelB, rightReverseDirection, wheelDistancePerPulse);
		left = new MotorGroup("Left Drive",leftMotorInputs, motorType, leftInverse, leftEncoder);
		right = new MotorGroup("Right Drive",rightMotorInputs, motorType, rightInverse, rightEncoder);
		gyro = new Gyro(gyroChannel);
		startAngle = gyro.getAngle();
	}
	/**
	 * runs the driving sequence
	 */
	public void driveMode(Joystick rightStick, Joystick leftStick) {
		double leftSpeed = leftStick.getRawAxis(DriverStation.YAxis);
		double rightSpeed = rightStick.getRawAxis(DriverStation.YAxis);
		left.set(leftSpeed);
		right.set(rightSpeed);
		DriverStation.sendData("Gyro Direction", gyro.getDirection());
		DriverStation.sendData("Gyro Displacement", gyro.getAngle());
	}
	
	/**
	 * drives the robot straight over a set number of meters
	 * @param distanceInMeters
	 */
	@Override
	public void goStraight(double distanceInMeters) {
		left.moveDistance(distanceInMeters);
		right.moveDistance(distanceInMeters);
		currentThread = left.getCurrentThread();
	}
	/**
	 * drives the robot straight over a set number of inches
	 * @param distanceInInches
	 */
	public void goForward(double distanceInInches){
		left.moveDistanceInches(distanceInInches);
		right.moveDistanceInches(distanceInInches);
		currentThread = right.getCurrentThread();
	}
	/**
	 * turns the robot for a certain angle
	 */
	@Override
	public void turnAngle(double bearing, MotorGroup left, MotorGroup right) {
		gyro.add(startAngle);
		gyro.reset();
		if (bearing >= 0 && bearing <= 180){
			while(gyro.getAngle()<bearing){
				left.set(LOW_SPEED);
				right.set(-LOW_SPEED);
			}
			left.stop();
			right.stop();
		}else if (bearing>180 && bearing<360){
			while(gyro.getAngle()<bearing){
				left.set(-LOW_SPEED);
				right.set(LOW_SPEED);
			}
			left.stop();
			right.stop();
		}
	}
	/**
	 * turns the robot back to its original position
	 */
	@Override
	public void setBackToOriginalPos() {
		startAngle = gyro.add(startAngle)%360;
		gyro.reset();
		turnAngle(startAngle,left,right);
		startAngle = 0;
	}
	@Override
	public void antiDrift() {
		// TODO Auto-generated method stub
		double angle=gyro.getAngle();
		turnAngle(-angle*kp,left,right);
		Timer.delay(waitTime);
		
	}	
	
	public void stop() {
		left.stop();
		right.stop();
	}
	
	public void goSideways(double distance) {
		//placeholder
	}
	
	public void moveSideways(double distanceInInches){
		//placeholder
	}
	
	public void EncoderValues (){
		DriverStation.sendData("leftEncoder", leftEncoder.get());
		DriverStation.sendData("rightEncoder", rightEncoder.get());
	}
}
