package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.Gyro;

import edu.wpi.first.wpilibj.Joystick;
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
	public Gyro gyro;
	Thread currentThread;
	protected Antidrift forwardAntiDrift;

	public double kp = 0.03;
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
		gyro.setPost(true);
		kp = DriverStation.getDouble("kpforward");
		forwardAntiDrift = new Antidrift(left, right, gyro, kp);
		enableForwardAntiDrift(true);
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
			boolean leftReverseDirection, boolean rightReverseDirection) {
		leftEncoder = new Encoder(leftChannelA, leftChannelB, leftReverseDirection, wheelDistancePerPulse);
		rightEncoder = new Encoder(rightChannelA, rightChannelB, rightReverseDirection, wheelDistancePerPulse);
		left = new MotorGroup("Left Drive",leftMotorInputs, motorType, leftInverse, leftEncoder);
		right = new MotorGroup("Right Drive",rightMotorInputs, motorType, rightInverse, rightEncoder);
		gyro = new Gyro(gyroChannel);
		startAngle = gyro.getAngle();
		kp = DriverStation.getDouble("kpforward");
		forwardAntiDrift = new Antidrift(left, right, gyro, kp);
		left.enableAntiDrift(true, forwardAntiDrift);
		right.enableAntiDrift(true, forwardAntiDrift);
	}
	/**
	 * runs the driving sequence
	 */
	public void driveMode(Joystick rightStick, Joystick leftStick) {
		//The negative Y Axis is due to joysticks taking forward as negative
		left.enableBrakeMode(false);
		right.enableBrakeMode(false);
		double leftSpeed = -leftStick.getRawAxis(DriverStation.YAxis);
		double rightSpeed = -rightStick.getRawAxis(DriverStation.YAxis);
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
		resetTankEncoders();
		left.moveDistanceInches(distanceInInches);
		right.moveDistanceInches(distanceInInches);
		currentThread = right.getCurrentThread();
	}
	
	/**
	 * to reset the gyro from outside the drive code
	 */
	public void resetGyro() {
		gyro.reset();
	}
	
	public void setTankDrive(double speed){
		left.set(speed);
		right.set(speed);
	}
	
	public void stop() {
		left.enableBrakeMode(true);
		right.enableBrakeMode(true);
		left.stop();
		right.stop();
	}
	public void stop(boolean brakeMode){
		left.enableBrakeMode(brakeMode);
		right.enableBrakeMode(brakeMode);
		left.stop();
		right.stop();
	}
	
	public void goSideways(double distance) {
		//placeholder
	}
	
	public void moveSideways(double distanceInInches){
		//placeholder
	}
	
	public void resetTankEncoders(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public void EncoderValues (){
		DriverStation.sendData("leftEncoder", leftEncoder.get());
		DriverStation.sendData("rightEncoder", rightEncoder.get());
	}
	
	public void enableForwardAntiDrift(boolean enable){
		left.enableAntiDrift(enable, forwardAntiDrift);
		right.enableAntiDrift(enable, forwardAntiDrift);
	}


	public Encoder getLeftEncoder(){
		return left.encoder;
	}
	
	public Encoder getRightEncoder(){
		return right.encoder;
	}
}
