package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.Talon;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.vision.Vision;

import edu.wpi.first.wpilibj.Timer;

public abstract class Autonomous {

	protected static TankDrive tankDrive;
	protected static PickerUpper pickerUpper;
	protected static HDrive hDrive;
	protected static Gyro gyro;
	protected static DriveTrain driveTrain;
	protected static Timer timer;
	protected static Vision vision;
	
	public static enum State {
		INIT_CASE, 					
		DRIVE_FORWARD, 				
		DRIVE_BACKWARD,				
		DRIVE_SIDEWAYS,			
		LIFT_BARREL,           
		LIFT_TOTE,	
		ADJUST_TOTE,		
		DROP_TOTE,		
		LIFT_POSITION,		
		IS_TOTE_LIFTED,	
		CENTER_TOTE,
		END_CASE,		
	}
	
	
	public static State presentState = State.INIT_CASE;
	public static State nextState;
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
		vision = new Vision();
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
	
	/**
	 * @author David Luo
	 * Attempts to center the robot in front of the closest tote.
	 */
	public static void centerTote() {
		double centerDistance = vision.centerOffset()/240;
		if (vision.isCentered() == -1) {
			driveTrain.goSideways(centerDistance);
//			nextState = CENTER_TOTE;
		}
		else if (vision.isCentered() == 1) {
			driveTrain.goSideways(centerDistance);
//			nextState = CENTER_TOTE;
		}
		else {
			driveTrain.stop();
//			nextState = DRIVE_FORWARD;	
		}
	}
}
