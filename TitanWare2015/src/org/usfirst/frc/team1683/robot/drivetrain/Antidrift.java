package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.Gyro;

public class Antidrift {
	
	private final int antidriftangle = 0;
	private final double kp;
	private Gyro gyro;
	
	MotorGroup left;
	MotorGroup right;
	
	public Antidrift(MotorGroup left, MotorGroup right, Gyro gyro, double kp) {
		this.left = left;
		this.right = right;
		this.kp = kp;
		this.gyro = gyro;
	}
	
	public double antiDrift(double speed, MotorGroup yourself){
		double error = antidriftangle - gyro.getAngle();
		double correction = kp*error/2.0;
		sendValues(correction, speed+correction, speed-correction);
		if (left.equals(yourself)){
			double leftSpeed = limitSpeed(speed+correction);
			return leftSpeed;
		}else if (right.equals(yourself)){
			double rightSpeed = limitSpeed(speed-correction);
			return rightSpeed;
		}else{
			return speed;
		}
		
	}
	
	public static double limitSpeed(double speed){
		if (speed>1.0){
			return 1.0;
		}
		else if (speed<-1.0){
			return -1.0;
		}
		else{
			return speed;
		}
	}
	
	public void sendValues(double correction, double leftSpeed, double rightSpeed){
		DriverStation.sendData("Gyro Value", gyro.getAngle());
		DriverStation.sendData("Correction", correction);	
		DriverStation.sendData("LeftSpeed", leftSpeed);
		DriverStation.sendData("RightSpeed", rightSpeed);
	}
	

}
