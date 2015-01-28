package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.drivetrain.Motor;
import org.usfirst.frc.team1683.robot.drivetrain.MotorGroup;
import org.usfirst.frc.team1683.robot.main.DriverStation;

public class PickerUpper {
	MotorGroup motors;
	
	public PickerUpper(int[] pickerUpperChannels, Class<Motor> talonSRX){
		this.motors = new MotorGroup(pickerUpperChannels, talonSRX);
	}
	
	public void run(){
		motors.set(DriverStation.auxStick.getRawAxis(DriverStation.YAxis));
	}
}
