package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;

import edu.wpi.first.wpilibj.Preferences;

public class DriveTester implements Tester{
	
	DriveTrain driveTrain;
	TankDrive tankDrive;
	double distance;
	double bearing;
	Encoder encoder;
	Preferences prefs;
	PickerUpper pickerUpper;
	
//	DriverStation.prefDouble("distance", 1);
//	DriverStation.prefDouble("bearing", 90);
	
	public DriveTester(DriveTrain driveTrain, TankDrive tankDrive, Encoder encoder, PickerUpper pickerUpper){
		this.driveTrain = driveTrain;
		this.tankDrive = tankDrive;
		this.encoder = encoder;
		this.pickerUpper = pickerUpper;
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
		
		double beltDistance = encoder.getDisplacement(47.0/700.0);
		DriverStation.sendData("Belt Distance", beltDistance);
		
		if (DriverStation.auxStick.getRawButton(4))
			pickerUpper.runAuto(5.0);
			
		
			
	}
}