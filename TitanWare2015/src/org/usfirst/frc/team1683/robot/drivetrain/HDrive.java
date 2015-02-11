package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;
import org.usfirst.frc.team1683.robot.sensors.Gyro;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;

public class HDrive extends TankDrive{
	AirSystem drivePistons;
	MotorGroup hLeftMotors,hRightMotors;
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
	 * @param rightMotor - right H motor
	 * @param leftMotor - left H motor
	 * @param hMotorType
	 * @param triggerButton
	 * @param wheelDistancePerPulse
	 */
	public HDrive(int[] leftMotorInputs,boolean leftInverse, int[] rightMotorInputs, boolean rightInverse, 
			Class motorType, int gyroChannel, int leftChannelA, int leftChannelB, int rightChannelA, int rightChannelB, 
			int rightPiston, int leftPiston, int rightMotor, int leftMotor, 
			Class hMotorType, int triggerButton, double wheelDistancePerPulse) {
		super(leftMotorInputs, leftInverse, rightMotorInputs, rightInverse, 
				motorType, gyroChannel, leftChannelA, leftChannelB, rightChannelA, rightChannelB, wheelDistancePerPulse);
		int[] channelNumbers = {leftMotor, rightMotor};
		int[] pistons = {rightPiston, leftPiston};
		drivePistons = new AirSystem(new Compressor(), pistons);
		hLeftMotors = new MotorGroup(new int[] {leftMotor}, hMotorType, false);
		hRightMotors= new MotorGroup(new int[]{rightMotor},hMotorType, false);
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
		hLeftMotors.set(speed);
		hRightMotors.set(speed);
		super.driveMode(leftStick, rightStick);
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
			super.turnAngle(angleBeforeDeploy, hLeftMotors, hRightMotors);
		}
	}
	
	/**
	 * brings the middle wheels back up
	 */
	public void liftWheels(){
		angleBeforeDeploy=super.gyro.getAngle();
		drivePistons.retract();
		if(Math.abs(gyro.getAngle()-angleBeforeDeploy)>Gyro.HDRIVE_THRESHOLD){
			super.turnAngle(angleBeforeDeploy, hLeftMotors, hRightMotors);
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
		hLeftMotors.moveDistance(distance);
		hRightMotors.moveDistance(distance);
	}
}
