package org.usfirst.frc.team1683.robot;

import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.Talon;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.main.autonomous.AutonomousSelector;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.power.PowerDistributionManager;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.test.DriveTester;
import org.usfirst.frc.team1683.robot.test.GyroTest;
import org.usfirst.frc.team1683.robot.test.TalonTest;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class TechnoTitan extends IterativeRobot {
    public static boolean debug = true;
//	AirSystemTester soloTester;
//	AirStateMachine stateMachine;
//	VisionTest visionTest;
	DriveTester driveTester;
//	TalonSRXTest talonTest;
    Gyro gyro;
    Gyro analogGyro;
    AnalogInput analog;
    TalonTest talonTest;
    GyroTest gyrotest;
	TankDrive tankDrive;
	PickerUpper pickerUpper;
	AutonomousSelector autonomous;
	PowerDistributionManager powerDistributionManager;
	Encoder encoder;
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	DriverStation.prefDouble("delay", 0.075);
    	DriverStation.prefDouble("distance", 1);
    	DriverStation.prefDouble("bearing", 90);
     	//gyro =new Gyro(HWR.GYRO);
//   	analogGyro=new Gyro(new AnalogInput(HWR.GYRO));
//   	gyrotest = new GyroTest(analogGyro);
//    	encoder = new Encoder(HWR.BELT_CHANNEL_A, HWR.BELT_CHANNEL_B, false);
    	//tankDrive = new TankDrive(new int[]{HWR.LEFT_MOTOR}, false , new int[]{HWR.RIGHT_MOTOR},true , Talon.class, HWR.GYRO);
    	//pickerUpper = new PickerUpper(new int[]{HWR.BELT_MOTOR}, Talon.class, false);
//    	powerDistributionManager = new PowerDistributionManager(15);
//    	new Thread(powerDistributionManager, "Power Distribution Manager").start();    	
//   	tankDrive = new TankDrive(new int[]{HWR.LEFT_MOTOR}, true , new int[]{HWR.RIGHT_MOTOR},false , Talon.class, HWR.GYRO, 
//    			HWR.LEFT_CHANNEL_A, HWR.LEFT_CHANNEL_B, HWR.RIGHT_CHANNEL_A, HWR.RIGHT_CHANNEL_B);
    	
//    	stateMachine = new AirStateMachine(new int[]{PCM.SOLENOID_0,PCM.SOLENOID_1,PCM.SOLENOID_2,
//    			PCM.SOLENOID_3,PCM.SOLENOID_4,PCM.SOLENOID_5,PCM.SOLENOID_6}, HWR.AUX_JOYSTICK,1);
//    	soloTester = new AirSystemTester(new int[]{PCM.SOLENOID_0} ,1);
//		visionTest = new VisionTest();
//    	talonTest = new TalonTest(new Talon(3,true));
    	driveTester = new DriveTester(tankDrive, tankDrive, encoder);
    	
    }

    public void autonomousInit(){
    	autonomous = new AutonomousSelector();
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	autonomous.run();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        tankDrive.driveMode(DriverStation.leftStick, DriverStation.rightStick);
        pickerUpper.run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testInit(){
    	analogGyro.reset();
    	//gyrotest.getBestSensitivity();
    }
    public void testPeriodic() {
    	//soloTester.test();
//    	stateMachine.test();
    	//visionTest.test();
    	//talonTest.test();
//    	tankDrive.driveMode(DriverStation.rightStick, DriverStation.leftStick);
    	driveTester.test();
//   	gyrotest.test();
//    	pickerUpper.run();
//    	talonTest.test();
    }
    
//    @Override 
//    public void disabledInit(){
//    	
//    }
    
    
}
