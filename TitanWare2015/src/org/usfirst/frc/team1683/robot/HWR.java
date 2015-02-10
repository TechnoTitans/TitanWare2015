package org.usfirst.frc.team1683.robot;

public class HWR {
	
	// Joysticks
	public static final int AUX_JOYSTICK 			= HWP.JOY_0;
	public static final int LEFT_JOYSTICK 			= HWP.JOY_1;
	public static final int RIGHT_JOYSTICK 			= HWP.JOY_2;
	
	//Motors
	public static final int LEFT_MOTOR				= HWP.PWM_0;
	public static final int RIGHT_MOTOR				= HWP.PWM_1;
	public static final int LEFT_BELT_MOTOR			= HWP.PWM_2;
	public static final int RIGHT_BELT_MOTOR		= HWP.PWM_5;
	public static final int LEFT_H_MOTOR			= HWP.PWM_3;
	public static final int RIGHT_H_MOTOR			= HWP.PWM_4;
	
	public static final int BELT_MOTOR				= HWP.PWM_2; //temporary - testing purposes

	// Gyro MUST BE 0 OR 1
	public static final int GYRO					= HWP.ANALOG_1;
	
	//Encoders
	public static final int LEFT_CHANNEL_A			= HWP.DIO_1;
	public static final int LEFT_CHANNEL_B			= HWP.DIO_2;
	public static final int RIGHT_CHANNEL_A			= HWP.DIO_3;
	public static final int RIGHT_CHANNEL_B			= HWP.DIO_4;
	public static final int BELT_CHANNEL_A          = HWP.DIO_5;
	public static final int BELT_CHANNEL_B          = HWP.DIO_6;
	public static final int H_CHANNEL_A				= HWP.DIO_7;
	public static final int H_CHANNEL_B				= HWP.DIO_8;
	
	//Encoder Wheel Distance Per Pulse
	public static double driveEncoderWDPP 			= 47/700;
	public static double liftEncoderWDPP 			= 47/700;
		
	//Encoder Reverse Directions
	public static boolean leftDriveEncoderReverse 	= false;
	public static boolean rightDriveEncoderReverse 	= false;
	public static boolean leftHEncoderReverse 		= false;
	public static boolean rightHEncoderReverse 		= false;
	public static boolean beltEncoderReverse 		= false;
	
	//Pneumatics
	public static final int PRESSURE_SENSOR 		= HWP.ANALOG_0;
	
	//Pistons
	public static final int LEFT_H_PISTON			= PCM.SOLENOID_0;
	public static final int RIGHT_H_PISTON			= PCM.SOLENOID_1;
	public static final int LEFT_LIFT_PISTON		= PCM.SOLENOID_2;
	public static final int RIGHT_LIFT_PISTON		= PCM.SOLENOID_3;
	
	//Robot Dimensions
	public static final double H_DRIVE_HEIGHT		= 0.5;
	public static final double ROBOT_HEIGHT			= 2.00;
	public static final double DISTANCE_TO_INDEX	= 0.1;
	
	//Buttons
	public static final int DEPLOY_H_DRIVE			= 1;
}
