package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.DoubleActionSolenoid;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.Joystick;

public class HDrive extends TankDrive{
	MotorGroup hBackMotors,hFrontMotors;
	int triggerButton;
	DoubleActionSolenoid pistons;
	Encoder backEncoder;
	Encoder frontEncoder;
	Antidrift sidewaysAntiDrift;
	
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
	 * @param frontPiston
	 * @param backPiston
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
			int frontPiston, int backPiston, PressureSensor pressure,
			int frontMotor, int backMotor, Class hMotorType, 
			int triggerButton, double driveWDPP, double HdriveWDPP, boolean leftEncoderReverseDirection, boolean rightEncoderReverseDirection,
			boolean backEncoderReverseDirection, boolean frontEncoderReverseDirection,
			boolean backInverse, boolean frontInverse) {
		super(leftMotorInputs, leftInverse, rightMotorInputs, rightInverse, 
				motorType, gyroChannel, leftChannelA, leftChannelB, rightChannelA, rightChannelB, 
				driveWDPP, leftEncoderReverseDirection, rightEncoderReverseDirection);
		frontEncoder = new Encoder(frontChannelA, frontChannelB, frontEncoderReverseDirection, HdriveWDPP);
//		backEncoder = new Encoder(backChannelA, backChannelB, backEncoderReverseDirection, HdriveWDPP);
		backEncoder = frontEncoder; //WE ARE PASSING IN THE FRONT ENCODER TWICE JUST THIS TIME FIX WHEN ANTHER ENCODER IS ADDED
		hBackMotors = new MotorGroup("HBackMotors", new int[] {backMotor}, hMotorType, backInverse, backEncoder); 
		hFrontMotors= new MotorGroup("HFrontMotors", new int[]{frontMotor},hMotorType, frontInverse, frontEncoder);
		this.triggerButton = triggerButton;
		sidewaysAntiDrift = new Antidrift(hBackMotors, hFrontMotors, gyro, DriverStation.getDouble("kpside"));
		hBackMotors.enableAntiDrift(true, sidewaysAntiDrift);
		hFrontMotors.enableAntiDrift(true, sidewaysAntiDrift);
		pistons = new DoubleActionSolenoid(new int[]{backPiston, frontPiston}, pressure);
		pistons.retract();
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
		pistons.extend();
	}
	
	/**
	 * brings the middle wheels back up
	 */
	public void liftWheels(){
		pistons.retract();
	}
	
	/**
	 * checks if the middle wheels are down
	 * @return whether the H-drive pistons are extended or not
	 */
	public boolean isDeployed(){
		return pistons.isExtended();
	}
	
	/**
	 * deploys H-drive wheels if not deployed and moves sideways given distance in meters
	 * @param distance (meters) - positive/negative affects direction
	 */
	public void goSideways(double distance)
	{
		deployWheels();
		hBackMotors.moveDistance(distance);
		hFrontMotors.moveDistance(distance);
		currentThread = hBackMotors.getCurrentThread();
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
		resetHEncoders();
		hBackMotors.moveDistanceInches(distanceInInches);
		hFrontMotors.moveDistanceInches(distanceInInches);
		currentThread = hFrontMotors.getCurrentThread();
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
}
