package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;

public class DriveTester implements Tester{
	
	DriveTrain driveTrain;
	TankDrive tankDrive;
	double distance;
	double bearing;
	
//	DriverStation.prefDouble("distance", 1);
//	DriverStation.prefDouble("bearing", 90);
	
	public DriveTester(DriveTrain driveTrain, TankDrive tankDrive){
		this.driveTrain = driveTrain;
		this.tankDrive = tankDrive;
		//distance = DriverStation.getDouble("distance");
		//bearing = DriverStation.getDouble("bearing");
		distance = 1.5;
		bearing = 180;
	}
	
	public void test(){
		if (DriverStation.rightStick.getRawButton(4)){
			driveTrain.goStraight(distance);
		    while(tankDrive.leftEncoder.getDistanceMeters()<distance)
		    	driveTrain.antiDrift();
		}
		if (DriverStation.rightStick.getRawButton(5))
			driveTrain.turnAngle(bearing);
		if (DriverStation.rightStick.getRawButton(6))
			driveTrain.setBackToOriginalPos();
	}
}