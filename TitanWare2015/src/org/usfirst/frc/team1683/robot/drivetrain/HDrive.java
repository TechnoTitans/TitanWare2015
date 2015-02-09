package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;

public class HDrive extends TankDrive{
	AirSystem drivePistons;
	MotorGroup hMotors;
	int triggerButton;
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
	 * @param rightPiston
	 * @param leftPiston
	 * @param rightMotor
	 * @param leftMotor
	 * @param hMotorType
	 * @param triggerButton
	 */
	public HDrive(int[] leftMotorInputs,boolean leftInverse, int[] rightMotorInputs, boolean rightInverse, 
			Class<Motor> motorType, int gyroChannel, int leftChannelA, int leftChannelB, int rightChannelA, int rightChannelB, 
			int rightPiston, int leftPiston, int rightMotor, int leftMotor, 
			Class<Motor> hMotorType, int triggerButton, double wheelDistancePerPulse) {
		super(leftMotorInputs, leftInverse, rightMotorInputs, rightInverse, 
				motorType, gyroChannel, leftChannelA, leftChannelB, rightChannelA, rightChannelA, wheelDistancePerPulse);
		int[] channelNumbers = {leftMotor, rightMotor};
		int[] pistons = {rightPiston, leftPiston};
		drivePistons = new AirSystem(new Compressor(), pistons);
		hMotors = new MotorGroup(channelNumbers, hMotorType, false);
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
		hMotors.set(speed);
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
		drivePistons.extend();
	}
	
	/**
	 * brings the middle wheels back up
	 */
	public void liftWheels(){
		drivePistons.retract();
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
		hMotors.moveDistance(distance);
	}
}
