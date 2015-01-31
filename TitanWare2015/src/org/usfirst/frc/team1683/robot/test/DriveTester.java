package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.main.DriverStation;

public class DriveTester implements Tester{
	
	DriveTrain driveTrain;
	double distance;
	double bearing;
	
//	DriverStation.prefDouble("distance", 1);
//	DriverStation.prefDouble("bearing", 90);
	
	public DriveTester(DriveTrain driveTrain){
		this.driveTrain = driveTrain;
		//distance = DriverStation.getDouble("distance");
		//bearing = DriverStation.getDouble("bearing");
		distance = 1.5;
		bearing = 180;
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