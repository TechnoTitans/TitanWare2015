package org.usfirst.frc.team1683.robot;

import org.usfirst.frc.team1683.robot.drivetrain.TalonSRX;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.Autonomous;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.main.TeleOp;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.test.AirStateMachine;
import org.usfirst.frc.team1683.robot.test.TalonSRXTest;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class TechnoTitan extends IterativeRobot {
    
	//AirSystemTester soloTester;
	AirStateMachine stateMachine;
	//VisionTest visionTest;
	TalonSRXTest talonTest;
	Gyro gyro;
	TankDrive tankDrive;
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	//soloTester = new AirSystemTester(new int[]{PCM.SOLENOID_0} ,1);
    	stateMachine = new AirStateMachine(new int[]{PCM.SOLENOID_0}, HWR.AUX_JOYSTICK,1);
    	//visionTest = new VisionTest();
    	talonTest = new TalonSRXTest(new TalonSRX(1));
    	DriverStation.prefDouble("delay", 0.075);
    	tankDrive = new TankDrive(new int[]{HWR.LEFT_MOTOR}, new int[]{HWR.RIGHT_MOTOR}, Talon.class, HWR.GYRO);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	Autonomous.run();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        TeleOp.run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	//soloTester.test();
    	stateMachine.test();
    	//visionTest.test();
    	talonTest.test();
    	tankDrive.driveMode(DriverStation.rightStick, DriverStation.leftStick);
    }
    
}
