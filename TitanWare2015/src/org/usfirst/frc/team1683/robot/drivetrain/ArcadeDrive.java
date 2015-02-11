package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.Joystick;

public class ArcadeDrive extends HDrive{
	
	public ArcadeDrive(int[] leftMotorInputs,boolean leftInverse, int[] rightMotorInputs, boolean rightInverse, 
			Class<Motor> motorType, int gyroChannel, int leftChannelA, int leftChannelB, int rightChannelA, int rightChannelB, 
			int rightPiston, int leftPiston, int rightMotor, int leftMotor, 
			Class<Motor> hMotorType, int triggerButton, double wheelDistancePerPulse, PressureSensor pressure){
		super(leftMotorInputs, leftInverse, rightMotorInputs, rightInverse, 
				motorType, gyroChannel, leftChannelA, leftChannelB, rightChannelA, rightChannelB, 
				rightPiston, leftPiston,pressure, rightMotor, leftMotor, hMotorType, triggerButton, wheelDistancePerPulse);
	}
	
	public void driveMode(Joystick joystick){
		double driveSpeed = joystick.getRawAxis(DriverStation.YAxis);
		double strafeSpeed = joystick.getRawAxis(DriverStation.XAxis);
		double turnSpeed = joystick.getRawAxis(DriverStation.ZAxis);
		super.left.set(driveSpeed);
		super.right.set(driveSpeed);
		super.hBackMotors.set(strafeSpeed);
		super.hFrontMotors.set(strafeSpeed);
		if (joystick.getRawButton(1)){
			deployWheels();
		}
		else{
			liftWheels();
		}
		turn(turnSpeed);
	}
	
	public void turn(double turnSpeed){
		left.set(turnSpeed);
		right.set(-turnSpeed);
	}
}
