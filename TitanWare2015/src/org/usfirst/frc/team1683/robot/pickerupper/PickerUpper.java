package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.Motor;
import org.usfirst.frc.team1683.robot.drivetrain.MotorGroup;
import org.usfirst.frc.team1683.robot.main.DriverStation;


public class PickerUpper {
	MotorGroup motors;
	
	public PickerUpper(int[] pickerUpperChannels, Class<Motor> talonSRX, boolean inverseDirection){
		this.motors = new MotorGroup(pickerUpperChannels, talonSRX, inverseDirection);
	}
	
	public PickerUpper(int[] pickerUpperChannels, Class<Motor> talonSRX, boolean inverseDirection,
			int beltChannelA, int beltChannelB){
		this.motors = new MotorGroup(pickerUpperChannels, talonSRX, inverseDirection, 
				new Encoder(beltChannelA, beltChannelB, inverseDirection));
	}
	
	public void run(){
		motors.set(DriverStation.auxStick.getRawAxis(DriverStation.YAxis));
	}
}
