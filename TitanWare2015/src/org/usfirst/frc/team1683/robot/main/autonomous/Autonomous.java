package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.sensors.Photogate;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;
import org.usfirst.frc.team1683.robot.vision.Vision;

import edu.wpi.first.wpilibj.Timer;

public abstract class Autonomous {

	protected static TankDrive tankDrive;
	protected static PickerUpper pickerUpper;
	protected static HDrive hDrive;
	protected static Gyro gyro;
	protected static DriveTrain driveTrain;
	protected static Timer timer;
	protected static Timer driveTimer;
	protected static Vision vision;
	protected static Timer visionTimer;
	protected static Photogate photogate;
	protected static PressureSensor pressure;

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
		ADJUST_FORWARD,
		ADJUST_BACKWARD,
		TURN,
		GO_FORWARD,
		MOVE_FORWARD,
		END_CASE,
		PRINT_ERROR,
		RUN_OTHER_AUTO,
		START_DRIVE_SIDEWAYS,
		START_DRIVE_FORWARD
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
	protected static double robotDistance;
	protected static double toteSpaceDistance;
	protected static boolean isToteLifted;
	protected static boolean enablePrinting;
	protected static double driveTime;
	public static boolean errorWarning;

	protected static double visionDistance = 0;
	protected static final double VISION_TIMEOUT = 3;

	protected static Autonomous autonomous;


	@SuppressWarnings("static-access")
	public Autonomous(HDrive drive, PickerUpper pickerUpper, Vision vision){
		driveTrain = tankDrive;
		this.hDrive = drive;
		this.pickerUpper = pickerUpper;
		this.vision = vision;
		updatePreferences();
		timer = new Timer();
		visionTimer = new Timer();
		driveTimer = new Timer();
	}

	/**
	 * @author Seung-Seok Lee
	 * prints out current and next state and the time during switches between states
	 * sends the above information to the SmartDashboard
	 */
	public static void printState(){
		if (enablePrinting){
			if (!nextState.equals(presentState)){
				System.out.println("Present State is: "+presentState.toString());
				System.out.println("Next State is: "+nextState.toString());
				System.out.println("Current Time: "+timer.get());
			}
		}
		DriverStation.sendData("presentState", presentState.toString());
		DriverStation.sendData("nextState", nextState.toString());
		DriverStation.sendData("time", timer.get());
	}

	public static void updatePreferences(){
		//Preferences from the SmartDashboard
		driveDistance = DriverStation.getDouble("driveDistance");
		sideDistance = DriverStation.getDouble("sideDistance");
		liftDistance = DriverStation.getDouble("liftDistance");
		adjustDistance = DriverStation.getDouble("adjustDistance");
		backDistance = DriverStation.getDouble("backDistance");
		robotDistance = DriverStation.getDouble("robotDistance");
		toteSpaceDistance = DriverStation.getDouble("toteSpaceDistance");
		enablePrinting = DriverStation.getBoolean("enablePrinting");
		driveTime = DriverStation.getDouble("driveTime");
	}

	/**
	 * @author Seung-Seok Lee
	 * default case for all autonomous sequences - prints out error in state machine once
	 */
	public static void defaultState(){
		if (errorWarning)
		{
			System.out.println("Error in State Machine");
			errorWarning = false;
		}
	}

	/**
	 * @author David Luo
	 * Attempts to center the robot in front of the closest tote.
	 * @param next The state to be executed after CENTER_TOTE.
	 * @return The next state to be executed (keep trying to center or skip).
	 */
	public static State centerTote(State next) {
		double centerDistance = vision.centerOffset()/240;
		visionDistance += centerDistance;
		if (visionTimer.get() <= VISION_TIMEOUT) {
			if (vision.isCentered() == -1) {
				driveTrain.goSideways(centerDistance);
				return State.CENTER_TOTE;
			}
			else if (vision.isCentered() == 1) {
				driveTrain.goSideways(centerDistance);
				return State.CENTER_TOTE;
			}
			else {
				driveTrain.stop();
				return next;
			}
		}
		else {
			//			driveTrain.goSideways(visionDistance);
			return next;
		}
	}

	public abstract void run();
}
