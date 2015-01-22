package org.usfirst.frc.team1683.robot.drivetrain;

import org.usfirst.frc.team1683.robot.sensors.Gyro;

public class TankDrive extends DriveTrain{

	MotorGroup left;
	MotorGroup right;
	
	Gyro gyro;
	
	double startAngle;
	
	public TankDrive(int[] leftMotorInputs, int[] rightMotorInputs, Class<Motor> motorType, int gyroChannel) {
		left = new MotorGroup(leftMotorInputs, motorType);
		right = new MotorGroup(rightMotorInputs, motorType);
		gyro = new Gyro(gyroChannel);
		startAngle = gyro.getAngle();
	}
	
	@Override
	public void goStraight(double distanceInMeters) {
		left.moveDistance(distanceInMeters);
		right.moveDistance(distanceInMeters);
	}

	@Override
	public void turnAngle(double bearing) {
		gyro.reset();
		if (bearing >= 0 && bearing <= 180){
			while(gyro.getAngle()<bearing){
				left.set(lowSpeed);
				right.set(-lowSpeed);
			}
			left.stop();
			right.stop();
		}else if (bearing>180 && bearing<360){
			while(gyro.getAngle()<bearing){
				left.set(-lowSpeed);
				right.set(lowSpeed);
			}
			left.stop();
			right.stop();
		}
	}

	@Override
	public void setBackToOriginalPos() {
	}

}
