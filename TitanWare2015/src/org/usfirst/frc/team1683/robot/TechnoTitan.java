package org.usfirst.frc.team1683.robot;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.Talon;
import org.usfirst.frc.team1683.robot.drivetrain.TalonSRX;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.main.autonomous.Autonomous;
import org.usfirst.frc.team1683.robot.main.autonomous.AutonomousSwitcher;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.sensors.Photogate;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;
import org.usfirst.frc.team1683.robot.statistics.CurrentTierIdentifier;
import org.usfirst.frc.team1683.robot.statistics.PowerDistributionManager;
import org.usfirst.frc.team1683.robot.test.DriveTester;
import org.usfirst.frc.team1683.robot.vision.Vision;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class TechnoTitan extends IterativeRobot {
    public static final boolean debugPowerDistribution = false;
    public static final boolean postEncoder = true;
    public static final boolean debugTierIdentifier = false;
    public static final boolean debugPressure = true;
    public static final boolean debugStates = true;
    
	DriveTester driveTester;
    Gyro gyro;
	PickerUpper pickerUpper;
	AutonomousSwitcher autonomous;
	PowerDistributionManager powerDistributionManager;
	HDrive drive;
	PressureSensor pressure;
	Photogate photogate;
	Vision vision;
	CurrentTierIdentifier toteNumberIdentifier;
	
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
//    	PreferencesList.set();
    	vision = new Vision();
        powerDistributionManager = new PowerDistributionManager(HWR.BACK_LEFT_MOTOR,HWR.FRONT_LEFT_MOTOR,HWR.BACK_RIGHT_MOTOR,HWR.FRONT_RIGHT_MOTOR, HWR.BELT_MOTOR );
        powerDistributionManager.start();
        pressure = new PressureSensor(HWR.PRESSURE_SENSOR);
        photogate = new Photogate(HWR.PHOTOGATE);
        drive = new HDrive(new int[]{HWR.BACK_LEFT_MOTOR,HWR.FRONT_LEFT_MOTOR}, HWR.LEFT_INVERSE, 
        		new int[]{HWR.BACK_RIGHT_MOTOR,HWR.FRONT_RIGHT_MOTOR}, HWR.RIGHT_INVERSE, 
        		TalonSRX.class, HWR.GYRO, 
        		HWR.LEFT_CHANNEL_A, HWR.LEFT_CHANNEL_B, HWR.RIGHT_CHANNEL_A, HWR.RIGHT_CHANNEL_B,
        		HWR.BACK_CHANNEL_A, HWR.BACK_CHANNEL_B, HWR.FRONT_CHANNEL_A, HWR.FRONT_CHANNEL_B,
        		HWR.LEFT_H_PISTON, HWR.RIGHT_H_PISTON, pressure, 
        		HWR.FRONT_H_MOTOR, HWR.BACK_H_MOTOR, Talon.class, HWR.DEPLOY_H_DRIVE, HWR.driveEncoderWDPP, HWR.hDriveEncoderWDPP,
        		HWR.leftDriveEncoderReverse, HWR.rightDriveEncoderReverse, HWR.backHEncoderReverse, HWR.frontHEncoderReverse);
        pickerUpper = new PickerUpper(Talon.class, HWR.BELT_INVERSE, new int[]{HWR.FRONT_LIFT_PISTON, HWR.BACK_LIFT_PISTON}, new int[]{HWR.BELT_MOTOR}, 
        		HWR.BELT_CHANNEL_A, HWR.BELT_CHANNEL_B, HWR.beltEncoderReverse, HWR.liftEncoderWDPP, 
        		pressure, photogate, drive);
        driveTester = new DriveTester(pickerUpper, drive);
    	toteNumberIdentifier = new CurrentTierIdentifier(powerDistributionManager.getPowerDistributionPanel(), 4, HWR.BELT_MOTOR);
		new Thread(toteNumberIdentifier, "Tier Manager").start();

    }

    public void autonomousInit(){
    	autonomous = new AutonomousSwitcher(drive, pickerUpper, vision);
    	Autonomous.errorWarning = true;
    	Autonomous.updatePreferences();
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
        drive.driveMode(DriverStation.leftStick, DriverStation.rightStick);
        pickerUpper.liftMode(HWR.AUX_JOYSTICK);
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testInit(){
    	
    }
    public void testPeriodic() {
    	driveTester.test();
    }

    
    
}
