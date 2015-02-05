package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.Talon;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.sensors.Gyro;

public abstract class Autonomous {

	protected static TankDrive tankDrive;
	protected static PickerUpper pickerUpper;
	protected static HDrive hDrive;
	protected static Gyro gyro;
	protected static DriveTrain driveTrain;
	
	public static final int INIT_CASE 					= 0;
	public static final int DRIVE_FORWARD 				= 1;
	public static final int DRIVE_BACKWARD				= 2;
	public static final int DRIVE_SIDEWAYS				= 3;
	public static final int LIFT_BARREL                 = 4;
	public static final int END_CASE 					= 10;
	
	
	public static int presentState = INIT_CASE;
	public static int nextState;
	public static int driveCount = 0;
	
	protected static double driveDistance;
	protected static double sideDistance;
	protected static double liftDistance; 
	
	public Autonomous(){
		tankDrive = new TankDrive(new int[]{HWR.LEFT_MOTOR}, true , new int[]{HWR.RIGHT_MOTOR},false , Talon.class, HWR.GYRO, 
    			HWR.LEFT_CHANNEL_A, HWR.LEFT_CHANNEL_B, HWR.RIGHT_CHANNEL_A, HWR.RIGHT_CHANNEL_B);
		hDrive = new HDrive(tankDrive, HWR.RIGHT_H_PISTON, HWR.LEFT_H_PISTON, HWR.LEFT_H_MOTOR, HWR.RIGHT_H_MOTOR,
				Talon.class, 0); //last parameter is irrelevant to autonomous
		driveTrain = tankDrive;
//		driveTrain = hDrive;
		pickerUpper = new PickerUpper(new int[]{HWR.BELT_MOTOR}, Talon.class, false);
		//Preferences from the SmartDashboard
		driveDistance = DriverStation.getDouble("driveDistance");
		sideDistance = DriverStation.getDouble("sideDistance");
		liftDistance = DriverStation.getDouble("liftDistance");
	}
}
