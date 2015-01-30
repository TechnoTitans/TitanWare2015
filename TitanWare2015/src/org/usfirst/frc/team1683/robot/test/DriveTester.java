package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.main.DriverStation;

public class DriveTester implements Tester{
	
	DriveTrain driveTrain;
	double distance;
	double bearing;
	
	public DriveTester(DriveTrain driveTrain){
		this.driveTrain = driveTrain;
		distance = DriverStation.getDouble("distance");
		bearing = DriverStation.getDouble("bearing");
	}
	
	public void test(){
		if (DriverStation.rightStick.getRawButton(1))
			driveTrain.goStraight(distance);
		if (DriverStation.rightStick.getRawButton(2))
			driveTrain.turnAngle(bearing);
		if (DriverStation.rightStick.getRawButton(3))
			driveTrain.setBackToOriginalPos();
	}
}