package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.DriveTrain;
import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
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
	protected static Timer liftTimer;
	protected static Photogate photogate;
	protected static PressureSensor pressure;
	protected static Encoder leftEncoder;
	protected static Encoder rightEncoder;

	public static enum State {
		INIT_CASE, 					
		DRIVE_FORWARD, 				
		DRIVE_BACKWARD,				
		DRIVE_SIDEWAYS,			
		LIFT_BARREL,           
		LIFT_TOTE,	
		ADJUST_TOTE,		
		DROP,		
		LIFT_POSITION,		
		IS_TOTE_LIFTED,	
		ADJUST_FORWARD,
		ADJUST_BACKWARD,
		TURN,
		GO_FORWARD,
		MOVE_FORWARD,
		END_CASE,
		PRINT_ERROR,
		RUN_OTHER_AUTO,
		START_DRIVE_SIDEWAYS,
		START_DRIVE_FORWARD,
		START_LIFT_BARREL,
		TILT_BACK
	}


	public static State presentState = State.INIT_CASE;
	public static State nextState;
	public static int driveCount = 0;
	public static int liftCount = 0;
	public static int isToteLiftedCount = 0;
	protected static double driveDistance;
	protected static double sideDistance;
	protected static double coopDistance;
	protected static double stepDistance;
	protected static double liftDistance; 
	protected static double backDistance;
	protected static double backToAutoDistance;
	protected static boolean isToteLifted;
	protected static boolean enablePrinting;
	protected static double driveTime = 5;
	protected static boolean errorWarning;
	protected static double liftTime;
	protected static double driveSpeed;
	protected static double sideSpeed;
	protected static double sideTime = 5;
	protected static double secondDelay = 0;
	protected static double tilterTime;
	protected static double fullTiltTime;

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
		leftEncoder = hDrive.leftEncoder;
		rightEncoder = hDrive.rightEncoder;
		liftTimer = new Timer();
	}
	
	/**
	 * 
	 */
	public static void setErrorWarning(boolean desiredCondition){
		errorWarning = desiredCondition;
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
		coopDistance = DriverStation.getDouble("co-opDistance");
		stepDistance = DriverStation.getDouble("stepDistance");
		sideDistance = DriverStation.getDouble("sideDistance");
		liftDistance = DriverStation.getDouble("liftDistance");
		backDistance = DriverStation.getDouble("backDistance");
		enablePrinting = DriverStation.getBoolean("enablePrinting");
		driveTime = DriverStation.getDouble("driveTime");
		liftTime = DriverStation.getDouble("liftTime");
		sideTime = DriverStation.getDouble("sideTime");
		secondDelay = DriverStation.getDouble("secondDelay");
		tilterTime = DriverStation.getDouble("tilterTime");
		sideSpeed = DriverStation.getDouble("sideSpeed");
		fullTiltTime = DriverStation.getDouble("fullTiltTime");

		backToAutoDistance = DriverStation.getDouble("backToAutoDistance");
		coopDistance = DriverStation.getDouble("coopDistance");
		stepDistance = DriverStation.getDouble("stepDistance");
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
	
	public static void waitForThread(Thread thread){
		try{
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void waitForThread(Thread thread1, Thread thread2){
		try{
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * DO NOT USE
	 * Should be run in END_CASE of all auto modes to ensure leftover
	 * threads are not running in Teleop mode
	 * @author Sreyas Mirthipati
	 * @param threads an array of all the threads to be destroyed
	 */
	@Deprecated
	public static void destroyThreads(Thread[] threads) {
		for(int i = 0;i < threads.length; i++) {
			try {
				threads[i].destroy();
			}
			catch(ThreadDeath e) {
				e.printStackTrace();
			}
		}
	}
	
	public static double setSpeed(double baseSpeed, double distance){
		if (distance>0){
			return baseSpeed;
		}
		else{
			return -baseSpeed;
		}
	}
	
	public static void delay() {
		if (secondDelay>15.0)
		{
			secondDelay = 15.0;
		}
		Timer.delay(secondDelay);
	}
	
	public static void adjustTote() {
		pickerUpper.dropTote();
		waitForThread(pickerUpper.getCurrentThread());
		hDrive.goForward(-backDistance);
		waitForThread(hDrive.left.getCurrentThread(),hDrive.right.getCurrentThread());
		pickerUpper.goToZero();
		waitForThread(pickerUpper.getCurrentThread());
		hDrive.goForward(backDistance);
		waitForThread(hDrive.left.getCurrentThread(),hDrive.right.getCurrentThread());
	}

	public abstract void run();
}
