package org.usfirst.frc.team1683.robot;

public class HWR {
	
	// Joysticks
	public static final int AUX_JOYSTICK 			= HWP.JOY_0;
	public static final int LEFT_JOYSTICK 			= HWP.JOY_1;
	public static final int RIGHT_JOYSTICK 			= HWP.JOY_2;
	
	public static final int LEFT_MOTOR				= HWP.PWM_0;
	public static final int RIGHT_MOTOR				= HWP.PWM_1;
	public static final int BELT_MOTOR				= HWP.PWM_2;
	public static final int LEFT_H_MOTOR			= HWP.PWM_3;
	public static final int RIGHT_H_MOTOR			= HWP.PWM_4;

	// Gyro MUST BE 0 OR 1
	public static final int GYRO					= HWP.ANALOG_1;
	
	//Encoders
	public static final int LEFT_CHANNEL_A			= HWP.DIO_1;
	public static final int LEFT_CHANNEL_B			= HWP.DIO_2;
	public static final int RIGHT_CHANNEL_A			= HWP.DIO_3;
	public static final int RIGHT_CHANNEL_B			= HWP.DIO_4;
	public static final int BELT_CHANNEL_A          = HWP.DIO_5;
	public static final int BELT_CHANNEL_B          = HWP.DIO_6;
	
	public static final int PRESSURE_SENSOR 		= HWP.ANALOG_0;
	
	public static final int LEFT_H_PISTON		= PCM.SOLENOID_0;
	public static final int RIGHT_H_PISTON		= PCM.SOLENOID_1;

}
