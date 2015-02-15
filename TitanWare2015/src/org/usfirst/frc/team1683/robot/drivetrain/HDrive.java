package org.usfirst.frc.team1683.robot.drivetrain;

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
