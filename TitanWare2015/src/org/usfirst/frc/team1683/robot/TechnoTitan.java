package org.usfirst.frc.team1683.robot;

import org.usfirst.frc.team1683.robot.drivetrain.Talon;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.Autonomous;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.main.TeleOp;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.test.DriveTester;
import org.usfirst.frc.team1683.robot.test.GyroTest;
import org.usfirst.frc.team1683.robot.test.VisionTest;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class TechnoTitan extends IterativeRobot {
     
//	AirSystemTester soloTester;
//	AirStateMachine stateMachine;
//	VisionTest visionTest;
	DriveTester driveTester;
//	TalonSRXTest talonTest;
    Gyro gyro;
    GyroTest gyrotest;
	TankDrive tankDrive;
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	DriverStation.prefDouble("delay", 0.075);
    	DriverStation.prefDouble("distance", 1);
    	DriverStation.prefDouble("bearing", 90);
    	gyro =new Gyro(HWR.GYRO);
    	gyrotest = new GyroTest(gyro);
//    	tankDrive = new TankDrive(new int[]{HWR.LEFT_MOTOR}, true , new int[]{HWR.RIGHT_MOTOR},false , Talon.class, HWR.GYRO);
//    	tankDrive = new TankDrive(new int[]{HWR.LEFT_MOTOR}, true , new int[]{HWR.RIGHT_MOTOR},false , Talon.class, HWR.GYRO, 
//    			HWR.LEFT_CHANNEL_A, HWR.LEFT_CHANNEL_B, HWR.RIGHT_CHANNEL_A, HWR.RIGHT_CHANNEL_B);
    	
//    	stateMachine = new AirStateMachine(new int[]{PCM.SOLENOID_0,PCM.SOLENOID_1,PCM.SOLENOID_2,
//    			PCM.SOLENOID_3,PCM.SOLENOID_4,PCM.SOLENOID_5,PCM.SOLENOID_6}, HWR.AUX_JOYSTICK,1);
//    	soloTester = new AirSystemTester(new int[]{PCM.SOLENOID_0} ,1);
    //	visionTest = new VisionTest();
//    	talonTest = new TalonSRXTest(new TalonSRX(1));
    	driveTester = new DriveTester(tankDrive);

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
    public void testInit(){
    	gyro.reset();
    }
    public void testPeriodic() {
    	//soloTester.test();
//    	stateMachine.test();
    	//visionTest.test();
    	//talonTest.test();
//    	tankDrive.driveMode(DriverStation.rightStick, DriverStation.leftStick);
//    	driveTester.test();
    	gyrotest.test();
    }
    
}
