package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.Motor;
import org.usfirst.frc.team1683.robot.drivetrain.Talon;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.sensors.Gyro;

import edu.wpi.first.wpilibj.Timer;

public abstract class Autonomous {

	protected static TankDrive tankDrive;
	protected static PickerUpper pickerUpper;
	protected static HDrive hDrive;
	protected static Gyro gyro;
	protected static DriveTrain driveTrain;
	protected static Timer timer;
	
	public static final int INIT_CASE 					= 0;
	public static final int DRIVE_FORWARD 				= 1;
	public static final int DRIVE_BACKWARD				= 2;
	public static final int DRIVE_SIDEWAYS				= 3;
	public static final int LIFT_BARREL                 = 4;
	public static final int LIFT_TOTE					= 5;
	public static final int ADJUST_TOTE					= 6;
	public static final int DROP_TOTE					= 7;
	public static final int LIFT_POSITION				= 8;
	public static final int IS_TOTE_LIFTED				= 9;
	public static final int END_CASE 					= 10;
	
	
	public static int presentState = INIT_CASE;
	public static int nextState;
	public static int driveCount = 0;
	public static int liftCount = 0;
	public static int isToteLiftedCount = 0;
	protected static double driveDistance;
	protected static double sideDistance;
	protected static double liftDistance; 
	protected static double adjustDistance;
	protected static double dropDistance;
	protected static double backDistance;
	protected static boolean isToteLifted;
	protected static boolean enablePrinting;

	
	public Autonomous(){
		tankDrive = new TankDrive(new int[]{HWR.LEFT_MOTOR}, true, new int[]{HWR.RIGHT_MOTOR}, false, Talon.class, HWR.GYRO, 
    			HWR.LEFT_CHANNEL_A, HWR.LEFT_CHANNEL_B, HWR.RIGHT_CHANNEL_A, HWR.RIGHT_CHANNEL_B, 1);
//		hDrive = new HDrive(tankDrive, HWR.RIGHT_H_PISTON, HWR.LEFT_H_PISTON, HWR.LEFT_H_MOTOR, HWR.RIGHT_H_MOTOR,
//				Talon.class, 0); //last parameter is irrelevant to autonomous
		hDrive = new HDrive(new int[]{HWR.LEFT_MOTOR}, true , new int[]{HWR.RIGHT_MOTOR},false , Talon.class, HWR.GYRO, 
    			HWR.LEFT_CHANNEL_A, HWR.LEFT_CHANNEL_B, HWR.RIGHT_CHANNEL_A, HWR.RIGHT_CHANNEL_B, 
    			HWR.RIGHT_H_PISTON, HWR.LEFT_H_PISTON, HWR.RIGHT_H_MOTOR, HWR.LEFT_H_MOTOR, 
    			Talon.class, 1, HWR.driveEncoderWDPP);
		driveTrain = tankDrive;
//		driveTrain = hDrive;
		pickerUpper = new PickerUpper(new int[]{HWR.BELT_MOTOR}, Talon.class, false);
		//Preferences from the SmartDashboard
		driveDistance = DriverStation.getDouble("driveDistance");
		sideDistance = DriverStation.getDouble("sideDistance");
		liftDistance = DriverStation.getDouble("liftDistance");
		timer = new Timer();
	}
	
	public static void printState(){
		if (enablePrinting){
			if (nextState != presentState){
				System.out.println("Present State is: "+presentState);
				System.out.println("Next State is: "+nextState);
				System.out.println("Current Time: "+timer.get());
			}
		}
	}
}