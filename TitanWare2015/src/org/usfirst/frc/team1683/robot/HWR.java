package org.usfirst.frc.team1683.robot;

public class HWR {

	// Joysticks
	public static final int AUX_JOYSTICK 			= HWP.JOY_0;
	public static final int LEFT_JOYSTICK 			= HWP.JOY_1;
	public static final int RIGHT_JOYSTICK 			= HWP.JOY_2;

	//Motors For Drive
	public static final int FRONT_LEFT_MOTOR		= HWP.CAN_1;
	public static final int BACK_LEFT_MOTOR			= HWP.CAN_2;

	public static final int FRONT_RIGHT_MOTOR		= HWP.CAN_3;
	public static final int BACK_RIGHT_MOTOR		= HWP.CAN_4;

	//Motors For HDrive
	public static final int BACK_H_MOTOR			= HWP.PWM_3;
	public static final int FRONT_H_MOTOR			= HWP.PWM_2;

	//Motors for Belt
	public static final int BELT_MOTOR				= HWP.PWM_1;

	//Motor Inverse Directions
	public static final boolean LEFT_INVERSE		= false;
	public static final boolean RIGHT_INVERSE		= true;
	public static final boolean BELT_INVERSE		= false;
	public static final boolean FRONT_H_INVERSE		= false;
	public static final boolean BACK_H_INVERSE		= true;

	public static final int PICKERUPPER_ACCEL		= HWP.ANALOG_3;
	
	// Gyro MUST BE 0 OR 1
	public static final int GYRO					= HWP.ANALOG_1;
	
	//Encoders
	public static final int BELT_CHANNEL_A          = HWP.DIO_0; //actual port is 0
	public static final int BELT_CHANNEL_B          = HWP.DIO_1; //actual port is 1
	public static final int LEFT_CHANNEL_A			= HWP.DIO_4;
	public static final int LEFT_CHANNEL_B			= HWP.DIO_5;
	public static final int RIGHT_CHANNEL_A			= HWP.DIO_2;
	public static final int RIGHT_CHANNEL_B			= HWP.DIO_3;
	public static final int FRONT_CHANNEL_A			= HWP.DIO_6;
	public static final int FRONT_CHANNEL_B			= HWP.DIO_7;
	public static final int BACK_CHANNEL_A			= 11;
	public static final int BACK_CHANNEL_B			= 10;
	public static final int PHOTOGATE 				= HWP.DIO_9;


	//Encoder Wheel Distance Per Pulse
	public static final double driveEncoderWDPP 			= 18.85/2040;
	public static final double liftEncoderWDPP 				= (14.25/757.75)*(28.0/30.0);
	public static final double hDriveEncoderWDPP			= (85.0*0.0254)/25.99;
	
	//Encoder Reverse Directions
	public static final boolean leftDriveEncoderReverse 	= true;
	public static final boolean rightDriveEncoderReverse 	= false;
	public static final boolean backHEncoderReverse 		= false;
	public static final boolean frontHEncoderReverse 		= false;
	public static final boolean beltEncoderReverse			= false;

	//Pneumatics
	public static final int PRESSURE_SENSOR 		= HWP.ANALOG_0;
	public static final int PRESSURE_SWITCH			= HWP.DIO_8;
	public static final int COMPRESSOR_RELAY		= HWP.RELAY_0;

	//Pistons
	public static final int BACK_H_PISTON			= PCM.SOLENOID_0;
	public static final int FRONT_H_PISTON			= PCM.SOLENOID_1;
	public static final int FRONT_LIFT_PISTON		= PCM.SOLENOID_2;
	public static final int BACK_LIFT_PISTON		= PCM.SOLENOID_3;

	//PickerUpper constants
	public static final double MOVE_MIN             = 0.0;
	public static final double MOVE_MAX             = 52.0;
	public static final double MOVE_ZERO_OFFSET     = 2.5;
	public static final double LIFT_MIN             = 12.0;
	public static final double LIFT_MAX             = 63.0;
	public static final double LIFT_H_INC           = 0.5;
	public static final double SLOPE                = (LIFT_MAX - LIFT_MIN)/(MOVE_MAX - MOVE_ZERO_OFFSET);
	public static final double B1                   = LIFT_MIN - (SLOPE * MOVE_ZERO_OFFSET);
	public static final double B2                   = B1 + LIFT_H_INC;

	//Dimensions
	public static final double TOTE_HEIGHT			= 12.1;
	public static final double H_DRIVE_HEIGHT		= 0.5;
	public static final double ROBOT_HEIGHT			= 2.00;
	public static final double DISTANCE_TO_INDEX	= 0.1;
	public static final double HOOK_INCREMENTS		= 13.0;
	
	//heights for PickerUpper
	public static final double SINGLE_TOTE_HEIGHT	= 60.0;
	public static final double DOUBLE_TOTE_HEIGHT	= SINGLE_TOTE_HEIGHT - HOOK_INCREMENTS;
	public static final double TRIPLE_TOTE_HEIGHT	= DOUBLE_TOTE_HEIGHT - HOOK_INCREMENTS;
	public static final double FOURTH_TOTE_HEIGHT   = TRIPLE_TOTE_HEIGHT - HOOK_INCREMENTS;
	public static final double BARREL_HEIGHT		= 60.0;
	public static final double BARREL_CLEAR_HEIGHT	= 37.0;
	public static final double DROP_HEIGHT			= 12.0;

	//Buttons
	public static final int DEPLOY_H_DRIVE			= 1;
	public static final int TOGGLE_BELT_PISTON		= 1;
	public static final int CALIBRATE_BELT			= 7;
	public static final int GO_TO_HOME				= 9;
	public static final int LIFT_FIRST_TOTE			= 10;
	public static final int LIFT_SECOND_TOTE		= 11;
	public static final int UPRIGHT_BELT			= 3;
	public static final int ANGLE_BELT				= 4;
	public static final int FREEZE_BELT				= 1;
	public static final int CALIBRATE_DRIVE			= 7;

	//Light Ring
	public static final int LIGHT_RING				= HWP.PWM_5;
	
	//Motor Constants
	public static final double LOW_SPEED			= 0.25;
	public static final double MEDIUM_SPEED			= 0.40;
	public static final double HIGH_SPEED			= 1.0;
	
	public static final double HDRIVE_SPEED			= 0.8;

	
}
