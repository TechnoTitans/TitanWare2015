package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.HWP;
import org.usfirst.frc.team1683.robot.PCM;
import org.usfirst.frc.team1683.robot.main.Autonomous;
import org.usfirst.frc.team1683.robot.main.TeleOp;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;
import org.usfirst.frc.team1683.robot.vision.Vision;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class AirSystemTest extends IterativeRobot {
    
	Vision vision;
	
	//for testing
	AirSystem air;
	int[] solenoids = {PCM.SOLENOID_0};
	Joystick stick;
    //for testing
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	vision = new Vision();
    	
    	//for testing
    	air = new AirSystem(new Compressor(),solenoids);
    	stick = new Joystick(HWP.JOY_0);
    	air.retract();
    	//for testing
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
    	vision.test();
    	
    	//for testing
    	if(stick.getRawButton(1) == true)
    	{
    		air.extend();
    		System.out.println("Extend");
    	}
    	else
    	{
    		air.retract();
    		System.out.println("Retract");
    	}
    	//for testing
    	
    }
    
}
