package org.usfirst.frc.team1683.robot.drivetrain;

import java.util.ArrayList;

public abstract class DriveTrain{

	ArrayList<Motor> motors = new ArrayList<>();
	
//	final int frontLeftMotor; 
//	final int rearLeftMotor;
//	final int frontRightMotor; 
//	final int rearRightMotor;
//
//	public DriveTrain(final int frontLeftMotor, final int rearLeftMotor, final int frontRightMotor, final int rearRightMotor) {
//		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
//		this.frontLeftMotor = frontLeftMotor;
//		this.rearLeftMotor = rearLeftMotor;
//		this.frontRightMotor = frontRightMotor;
//		this.rearRightMotor = rearRightMotor;
//	}
	
	public abstract void goStraight(double distance);
	public abstract void turnAngle(double bearing);
	public abstract void setBackToOriginalPos();


}
