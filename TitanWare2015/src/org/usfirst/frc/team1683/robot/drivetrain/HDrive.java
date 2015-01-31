package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;

import edu.wpi.first.wpilibj.Compressor;

public class HDrive {
	AirSystem drivePistons;
	TankDrive tankDrive;
	MotorGroup hMotors;
	int triggerButton;
	
	public HDrive(TankDrive tankDrive, int rightPiston, int leftPiston, int rightMotor, int leftMotor, 
			Class<Motor> motorType, int triggerButton) {
		int[] channelNumbers = {leftMotor, rightMotor};
		int[] pistons = {rightPiston, leftPiston};
		drivePistons = new AirSystem(new Compressor(), pistons);
		this.tankDrive = tankDrive;
		hMotors = new MotorGroup(channelNumbers, motorType, false);
		this.triggerButton = triggerButton;
	}
	
	public void driveMode(){
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
	
	public void deployWheels(){
		drivePistons.extend();
	}
	
	public void liftWheels(){
		drivePistons.retract();
	}
	
	public boolean isDeployed(){
		return drivePistons.isExtended();
	}
	
}
