package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;

public class HDrive extends DriveTrain{
	AirSystem drivePistons;
	TankDrive tankDrive;
	MotorGroup hMotors;
	int triggerButton;
	/**
	 * Constructor
	 * @param tankDrive
	 * @param rightPiston
	 * @param leftPiston
	 * @param rightMotor
	 * @param leftMotor
	 * @param motorType
	 * @param triggerButton
	 */
	public HDrive(TankDrive tankDrive, int rightPiston, int leftPiston, int rightMotor, int leftMotor, 
			Class motorType, int triggerButton) {
		int[] channelNumbers = {leftMotor, rightMotor};
		int[] pistons = {rightPiston, leftPiston};
		drivePistons = new AirSystem(new Compressor(), pistons);
		this.tankDrive = tankDrive;
		hMotors = new MotorGroup(channelNumbers, motorType, false);
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
		tankDrive.driveMode(DriverStation.rightStick, DriverStation.leftStick);
		if (DriverStation.rightStick.getRawButton(triggerButton) && DriverStation.leftStick.getRawButton(triggerButton)) {
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
	 * drives robot straight given distance
	 * @param distance - positive/negative determines direction
	 */
	public void goStraight(double distance){
		tankDrive.goStraight(distance);
	}
	
	/**
	 * turns robot specific angle using the gyro
	 * @param bearing - determines where to turn to
	 */
	public void turnAngle(double bearing){
		tankDrive.turnAngle(bearing);
	}
	
	/**
	 * sets robot back to original orientation
	 * Status: Not-Completed
	 */
	public void setBackToOriginalPos(){
		tankDrive.setBackToOriginalPos();
	}
	
	/**
	 * uses gyro to correct path drifting
	 */
	public void antiDrift(){
		tankDrive.antiDrift();
	}
	
	/**
	 * stops the drive train
	 */
	public void stop(){
		tankDrive.stop();
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
