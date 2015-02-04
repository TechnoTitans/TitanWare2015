package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.Motor;
import org.usfirst.frc.team1683.robot.drivetrain.MotorGroup;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;


public class PickerUpper {
	MotorGroup motors;
	AirSystem liftPistons;
	MotorGroup liftMotors;
	
	/**
	 * Constructor
	 * @param pickerUpperChannels
	 * @param talonSRX
	 * @param inverseDirection
	 */
	public PickerUpper(int[] pickerUpperChannels, Class<Motor> talonSRX, boolean inverseDirection){
		this.motors = new MotorGroup(pickerUpperChannels, talonSRX, inverseDirection);
		
	}
/**
 * Constructor
 * @param pickerUpperChannels
 * @param talonSRX
 * @param inverseDirection
 * @param beltChannelA
 * @param beltChannelB
 * @param rightPiston
 * @param leftPiston
 * @param leftMotor
 * @param rightMotor
 * @param motorType
 */
	public PickerUpper(int[] pickerUpperChannels, Class<Motor> talonSRX, boolean inverseDirection,
			int beltChannelA, int beltChannelB, int rightPiston, int leftPiston, int leftMotor, int rightMotor, Class<Motor> motorType){
		this.motors = new MotorGroup(pickerUpperChannels, talonSRX, inverseDirection, 
				new Encoder(beltChannelA, beltChannelB, inverseDirection));
		int[] channelNumbers = {leftMotor, rightMotor};
		int[] pistons = {rightPiston, leftPiston};
		liftPistons = new AirSystem(new Compressor(), pistons);
		liftMotors = new MotorGroup(channelNumbers, motorType , false);
	}
	/**
	 * Lifts the pickerupper device into the straight position
	 */
	public void uprightPickerUpper() {
		liftPistons.extend();
	}
	
	/**
	 * Brings back the pickerupper device into an angle
	 */
	public void angledPickerUpper() {
		liftPistons.retract();
	}
	
	
	/**
	 * runs the motors for the pickerupper
	 */
	public void run(){
		motors.set(DriverStation.auxStick.getRawAxis(DriverStation.YAxis));
	}
	
	
}
