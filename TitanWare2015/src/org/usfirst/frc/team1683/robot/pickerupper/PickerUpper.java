package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.Motor;
import org.usfirst.frc.team1683.robot.drivetrain.MotorGroup;
import org.usfirst.frc.team1683.robot.main.DriverStation;


public class PickerUpper {
	MotorGroup motors;
	/**
	 * Constructor
	 * @param pickerUpperChannels
	 * @param motorType
	 * @param inverseDirection
	 */
	public PickerUpper(int[] pickerUpperChannels, Class motorType, boolean inverseDirection){
		this.motors = new MotorGroup(pickerUpperChannels, motorType, inverseDirection);
	}
	/**
	 * Constructor
	 * @param pickerUpperChannels
	 * @param talonSRX
	 * @param inverseDirection
	 * @param beltChannelA
	 * @param beltChannelB
	 */
	public PickerUpper(int[] pickerUpperChannels, Class motorType, boolean inverseDirection,
			int beltChannelA, int beltChannelB){
		this.motors = new MotorGroup(pickerUpperChannels, motorType, inverseDirection, 
				new Encoder(beltChannelA, beltChannelB, inverseDirection));
	}
	/**
	 * runs the motors for the pickerupper
	 */
	public void run(){
		motors.set(DriverStation.auxStick.getRawAxis(DriverStation.YAxis));
	}
}
