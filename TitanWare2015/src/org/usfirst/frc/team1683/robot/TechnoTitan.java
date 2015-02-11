package org.usfirst.frc.team1683.robot;

import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.Talon;
import org.usfirst.frc.team1683.robot.drivetrain.TalonSRX;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.main.autonomous.AutonomousSelector;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.statistics.PowerDistributionManager;
import org.usfirst.frc.team1683.robot.test.DriveTester;

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
    
	DriveTester driveTester;
    Gyro gyro;
	TankDrive tankDrive;
	PickerUpper pickerUpper;
	AutonomousSelector autonomous;
	PowerDistributionManager powerDistributionManager;
	Encoder liftEncoder;
	HDrive drive;
	
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	DriverStation.prefDouble("delay", 0.075);
    	DriverStation.prefDouble("distance", 1);
    	DriverStation.prefDouble("bearing", 90);
        pickerUpper = new PickerUpper(new int[]{HWR.BELT_MOTOR},Talon.class , HWR.beltEncoderReverse);
        powerDistributionManager = new PowerDistributionManager(HWR.BACK_LEFT_MOTOR,HWR.FRONT_LEFT_MOTOR,HWR.BACK_RIGHT_MOTOR,HWR.FRONT_RIGHT_MOTOR, HWR.BELT_MOTOR );
        powerDistributionManager.start();
        drive = new HDrive(new int[]{HWR.BACK_LEFT_MOTOR,HWR.FRONT_LEFT_MOTOR}, false, 
        		new int[]{HWR.BACK_RIGHT_MOTOR,HWR.FRONT_RIGHT_MOTOR}, true, 
        		TalonSRX.class, HWR.GYRO, 
        		HWR.LEFT_CHANNEL_A, HWR.LEFT_CHANNEL_B, 
        		HWR.RIGHT_CHANNEL_A, HWR.RIGHT_CHANNEL_B, 
        		HWR.LEFT_H_PISTON, HWR.RIGHT_H_PISTON, 
        		HWR.FRONT_H_MOTOR, HWR.BACK_H_MOTOR, Talon.class, 1, 4);
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
   //     tankDrive.driveMode(DriverStation.leftStick, DriverStation.rightStick);
        pickerUpper.run();
        drive.driveMode(DriverStation.leftStick, DriverStation.rightStick);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testInit(){
    	
    }
    public void testPeriodic() {

    }

    
    
}
