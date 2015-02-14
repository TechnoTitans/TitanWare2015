package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.Joystick;

public class ArcadeDrive extends HDrive{
	
	@SuppressWarnings("rawtypes")
	public ArcadeDrive(int[] leftMotorInputs,boolean leftInverse, int[] rightMotorInputs, boolean rightInverse, 
			Class motorType, int gyroChannel, int leftChannelA, int leftChannelB, int rightChannelA, int rightChannelB,
			int backChannelA, int backChannelB,
			int frontChannelA, int frontChannelB,
			int rightPiston, int leftPiston, PressureSensor pressure,
			int frontMotor, int backMotor, Class hMotorType, 
			int triggerButton, double driveWDPP, double HdriveWDPP, boolean leftReverseDirection, boolean rightReverseDirection,
			boolean backReverseDirection, boolean frontReverseDirection){
		super(leftMotorInputs, leftInverse, rightMotorInputs, rightInverse,
				motorType, gyroChannel, leftChannelA, leftChannelB, rightChannelA, rightChannelB,
				backChannelA, backChannelB, frontChannelA, frontChannelB,
				rightPiston, leftPiston, pressure, frontMotor, backMotor, hMotorType,
				triggerButton, driveWDPP, HdriveWDPP, leftReverseDirection, rightReverseDirection,
				backReverseDirection, frontReverseDirection);
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
