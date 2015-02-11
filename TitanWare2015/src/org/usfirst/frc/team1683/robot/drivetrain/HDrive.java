package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;
import org.usfirst.frc.team1683.robot.sensors.Gyro;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;

public class HDrive extends TankDrive{
	AirSystem drivePistons;
	MotorGroup hBackMotors,hFrontMotors;
	int triggerButton;
	double angleBeforeDeploy;
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
			int rightPiston, int leftPiston, 
			int frontMotor, int backMotor, Class hMotorType, 
			int triggerButton, double wheelDistancePerPulse) {
		super(leftMotorInputs, leftInverse, rightMotorInputs, rightInverse, 
				motorType, gyroChannel, leftChannelA, leftChannelB, rightChannelA, rightChannelB, wheelDistancePerPulse);
		int[] channelNumbers = {frontMotor, backMotor};
		int[] pistons = {rightPiston, leftPiston};
		drivePistons = new AirSystem(new Compressor(), pistons);
		hBackMotors = new MotorGroup(new int[] {backMotor}, hMotorType, false);
		hFrontMotors= new MotorGroup(new int[]{frontMotor},hMotorType, true);
		this.triggerButton = triggerButton;
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
		super.driveMode(rightStick, leftStick);
		if (DriverStation.rightStick.getRawButton(triggerButton) && 
				DriverStation.leftStick.getRawButton(triggerButton)) {
			deployWheels();
		}
		else {
			liftWheels();
		}
	}
	
	/**
	 * puts down the middle wheels
	 */
	public void deployWheels(){
		angleBeforeDeploy=super.gyro.getAngle();
		drivePistons.extend();
		if(Math.abs(gyro.getAngle()-angleBeforeDeploy)>Gyro.HDRIVE_THRESHOLD){
			super.turnAngle(angleBeforeDeploy, hBackMotors, hFrontMotors);
		}
	}
	
	/**
	 * brings the middle wheels back up
	 */
	public void liftWheels(){
		angleBeforeDeploy=super.gyro.getAngle();
		drivePistons.retract();
		if(Math.abs(gyro.getAngle()-angleBeforeDeploy)>Gyro.HDRIVE_THRESHOLD){
			super.turnAngle(angleBeforeDeploy, hBackMotors, hFrontMotors);
		}
	}
	
	/**
	 * checks if the middle wheels are down
	 * @return whether the H-drive pistons are extended or not
	 */
	public boolean isDeployed(){
		return drivePistons.isExtended();
	}
	
	/**
	 * deploys H-drive wheels if not deployed and moves sideways given distance
	 * @param distance - positive/negative affects direction
	 */
	public void goSideways(double distance)
	{
		if(!isDeployed())
			deployWheels();
		hBackMotors.moveDistance(distance);
		hFrontMotors.moveDistance(distance);
	}
}
