package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;

public class HDrive extends TankDrive{
	MotorGroup hBackMotors,hFrontMotors;
	int triggerButton;
	double angleBeforeDeploy;
	DrivePistons pistons;
	boolean isLifted;
	Encoder backEncoder;
	Encoder frontEncoder;
	/**
	 * Constructor
	 * @param leftMotorInputs - left side of drive train
	 * @param leftInverse
	 * @param rightMotorInputs - right side of drive train
	 * @param rightInverse
	 * @param motorType
	 * @param gyroChannel
	 * @param leftChannelA
	 * @param leftChannelB
	 * @param rightChannelA
	 * @param rightChannelB
	 * @param rightPiston
	 * @param leftPiston
	 * @param frontMotor - right H motor
	 * @param backMotor - left H motor
	 * @param hMotorType
	 * @param triggerButton
	 * @param wheelDistancePerPulse
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HDrive(int[] leftMotorInputs,boolean leftInverse, int[] rightMotorInputs, boolean rightInverse, 
			Class motorType, int gyroChannel, int leftChannelA, int leftChannelB, int rightChannelA, int rightChannelB,
			int backChannelA, int backChannelB,
			int frontChannelA, int frontChannelB,
			int rightPiston, int leftPiston, PressureSensor pressure,
			int frontMotor, int backMotor, Class hMotorType, 
			int triggerButton, double driveWDPP, double HdriveWDPP, boolean leftReverseDirection, boolean rightReverseDirection,
			boolean backReverseDirection, boolean frontReverseDirection) {
		super(leftMotorInputs, leftInverse, rightMotorInputs, rightInverse, 
				motorType, gyroChannel, leftChannelA, leftChannelB, rightChannelA, rightChannelB, 
				driveWDPP, leftReverseDirection, rightReverseDirection);
		pistons = new DrivePistons(new int[]{rightPiston, leftPiston}, pressure);
		backEncoder = new Encoder(backChannelA, backChannelB, backReverseDirection, HdriveWDPP);
		frontEncoder = new Encoder(frontChannelA, frontChannelB, frontReverseDirection, HdriveWDPP);
		hBackMotors = new MotorGroup("HBackMotors", new int[] {backMotor}, hMotorType, false, backEncoder);
		hFrontMotors= new MotorGroup("HFrontMotors", new int[]{frontMotor},hMotorType, true, frontEncoder);
		this.triggerButton = triggerButton;
		isLifted = true;
//		pistons = new DoubleActionSolenoid(new int[]{rightPiston, leftPiston}, pressure);
	}
	
	
	
	/**
	 * Runs driving sequence periodically
	 * @param leftStick
	 * @param rightStick
	 */
	public void driveMode(Joystick leftStick, Joystick rightStick){
		double speed = (DriverStation.rightStick.getRawAxis(DriverStation.XAxis) 
				+ DriverStation.leftStick.getRawAxis(DriverStation.XAxis))/2 ;
		hBackMotors.set(speed);
		hFrontMotors.set(speed);
		if (DriverStation.rightStick.getRawButton(triggerButton) && 
				DriverStation.leftStick.getRawButton(triggerButton)) {
			deployWheels();
		}
		else {
			liftWheels();
			super.driveMode(rightStick, leftStick);
		}
		if (DriverStation.leftStick.getRawButton(HWR.CALIBRATE_DRIVE))
		{
			leftEncoder.reset();
			rightEncoder.reset();
		}
	}
	
	/**
	 * puts down the middle wheels
	 */
	public void deployWheels(){
		if (isLifted){
			angleBeforeDeploy=super.gyro.getAngle();
			pistons.changeState();
			if(Math.abs(gyro.getAngle()-angleBeforeDeploy)>Gyro.HDRIVE_THRESHOLD){
				super.turnAngle(angleBeforeDeploy, hBackMotors, hFrontMotors);
			}
		}
		isLifted = false;
	}
	
	/**
	 * brings the middle wheels back up
	 */
	public void liftWheels(){
		if (!isLifted){
			angleBeforeDeploy=super.gyro.getAngle();
			pistons.changeState();
			if(Math.abs(gyro.getAngle()-angleBeforeDeploy)>Gyro.HDRIVE_THRESHOLD){
				super.turnAngle(angleBeforeDeploy, hBackMotors, hFrontMotors);
			}
		}
		isLifted = true;
	}
	
	/**
	 * checks if the middle wheels are down
	 * @return whether the H-drive pistons are extended or not
	 */
	public boolean isDeployed(){
		return pistons.getBackAirSystem().isExtended();
	}
	
	/**
	 * deploys H-drive wheels if not deployed and moves sideways given distance in meters
	 * @param distance (meters) - positive/negative affects direction
	 */
	public void goSideways(double distance)
	{
//		if(!isDeployed())
			deployWheels();
		hBackMotors.moveDistance(distance);
		hFrontMotors.moveDistance(distance);
		currentThread = hBackMotors.getCurrentThread();
	}
	
	
	public double getAntiDriftAngle(){
		double targetAngle;
		if(isDeployed()){
			targetAngle = -90;
		}
		else{
			targetAngle = 0;
		}
		return targetAngle;
	}
	
	public void antiDrift(double speed){
		super.antiDrift(speed, getAntiDriftAngle());
	}
	
	public void antiDrift(double speed, MotorGroup front, MotorGroup back){
		double error = 0 - gyro.getAngle();
		double correction = kp*error/2.0;
		front.set(limitSpeed(speed+correction));
		back.set(limitSpeed(speed-correction));
		DriverStation.sendData("Gyro Value", gyro.getAngle());
		DriverStation.sendData("Correction", correction);	
		DriverStation.sendData("FrontSpeed", limitSpeed(speed - correction));
		DriverStation.sendData("BackSpeed", limitSpeed(speed + correction));
	}
	
	public MotorGroup getFrontHMotor(){
		return hFrontMotors;
	}
	
	public MotorGroup getBackHMotor(){
		return hBackMotors;
	}
	
	public Thread getCurrentThread(){
		return currentThread;
	}
	/**
	 * deploys H-drive wheels if not deployed and moves sideways given distance in inches
	 * @param distanceInInches - positive/negative affects direction
	 */
	public void moveSideways(double distanceInInches)
	{
		deployWheels();
		hBackMotors.moveDistanceInches(distanceInInches);
		hFrontMotors.moveDistanceInches(distanceInInches);
		currentThread = hBackMotors.getCurrentThread();
	}
	
	public void set(double speed){
		deployWheels();
		hBackMotors.set(speed);
		hFrontMotors.set(speed);
	}
	
	public void stopSide(){
		hBackMotors.stop();
		hFrontMotors.stop();
	}
	
	public void resetHEncoders(){
		backEncoder.reset();
		frontEncoder.reset();
	}
	
	private class DrivePistons{
		AirSystem frontAirSystem;
		AirSystem backAirSystem;
		public DrivePistons(int[] pistons, PressureSensor pressure) { //front piston, back Piston
			Compressor compressor = new Compressor();
			frontAirSystem = new AirSystem(compressor, new int[]{pistons[0]}, pressure);
			backAirSystem = new AirSystem(compressor, new int[]{pistons[1]}, pressure);
			isLifted = false;
		}
		
		@SuppressWarnings("unused")
		public AirSystem getFrontAirSystem(){
			return frontAirSystem;
		}
		public AirSystem getBackAirSystem(){
			return backAirSystem;
		}
		@SuppressWarnings("unused")
		public void liftWheels(){
			
		}
		
		public void changeState(){
			if (frontAirSystem.isExtended()){
				frontAirSystem.retract();
				backAirSystem.extend();
			}else{
				frontAirSystem.extend();
				backAirSystem.retract();
			}
		}
		
	}
}
