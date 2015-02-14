package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;

import edu.wpi.first.wpilibj.Preferences;

public class DriveTester implements Tester{
	
	HDrive hDrive;
	DriveTrain driveTrain;
	TankDrive tankDrive;
	double distance;
	double bearing;
	Encoder beltEncoder;
	Preferences prefs;
	PickerUpper pickerUpper;
	
//	DriverStation.prefDouble("distance", 1);
//	DriverStation.prefDouble("bearing", 90);
	
	public DriveTester(PickerUpper pickerUpper, HDrive hDrive){
		this.pickerUpper = pickerUpper;
		this.hDrive = hDrive;
		distance = DriverStation.getDouble("distance");
		//bearing = DriverStation.getDouble("bearing");
		//distance = 1.5;
		bearing = 180;
		beltEncoder = pickerUpper.beltEncoder;
	}
	
	public double averageDistance(){
		return ((tankDrive.leftEncoder.getDistanceMeters()
				+tankDrive.rightEncoder.getDistanceMeters())/2);
	}
	
	public void test(){
//		if (DriverStation.rightStick.getRawButton(4)){
//			driveTrain.goStraight(distance);
//		    while(averageDistance()<distance)
//		    	driveTrain.antiDrift();
//		}
		if (DriverStation.antiBounce(HWR.LEFT_JOYSTICK, 8)){
			hDrive.goForward(12);
		}
		if (DriverStation.antiBounce(HWR.LEFT_JOYSTICK, 9)){
			hDrive.goForward(6);
		}
		if (DriverStation.antiBounce(HWR.LEFT_JOYSTICK, 10)){
			hDrive.goForward(18);
		}
		if (DriverStation.antiBounce(HWR.LEFT_JOYSTICK, 11)){
			hDrive.goForward(distance);
		}
//		if (DriverStation.rightStick.getRawButton(5))
//			driveTrain.turnAngle(bearing, tankDrive.left, tankDrive.right);
//		if (DriverStation.rightStick.getRawButton(6))
//			driveTrain.setBackToOriginalPos();
		
		double beltDistance = beltEncoder.getDisplacement(HWR.liftEncoderWDPP);
		DriverStation.sendData("Belt Distance", beltDistance);	
	}
}