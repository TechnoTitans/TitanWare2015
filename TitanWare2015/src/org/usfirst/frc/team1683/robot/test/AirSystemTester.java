package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;
import org.usfirst.frc.team1683.robot.pneumatics.OldCompressor;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;

/**
 * This class contains functions to test the AirSystems of a robot.
 * @author Sreyas Mirthipati
 */
public class AirSystemTester implements Tester{

	AirSystem air;
	Joystick stick;
	int buttonAssignment;
	int stickAssignment;

	/**
	 * @param solenoids - an int array of all solenoids to be tested
	 * @param button - the button assignment for test firing (default on aux joystick)
	 */
	public AirSystemTester(int[] solenoids, int button , PressureSensor pressure) {
		air = new AirSystem(solenoids, pressure);
		stick = new Joystick(HWR.AUX_JOYSTICK);
		buttonAssignment = button;
		air.retract();
	}
	
	/**
	 * @param PcmNum - the PCM ID the solenoids are connected to
	 * @param solenoids - an int array of all solenoids to be tested
	 * @param button - the button assignment for test firing (default on aux joystick)
	 */
	public AirSystemTester(int PcmNum, int[] solenoids, int button) {
		air = new AirSystem(PcmNum, solenoids);
		stick = new Joystick(HWR.AUX_JOYSTICK);
		buttonAssignment = button;
		air.retract();
	}

	/**
	 * @param solenoids - an int array of all solenoids to be tested
	 * @param stickNum - the joystick that the button to fire with is located
	 * @param button - the button assignment for test firing
	 */
	public AirSystemTester(int[] solenoids, int stickNum, int button , PressureSensor pressure) {
		air = new AirSystem(solenoids, pressure);
		stick = new Joystick(stickNum);
		buttonAssignment = button;
		air.retract();
	}


	/**
	 * This function is called to listen for button press event to trigger
	 * the solenoid
	 */
	public void test() {
		if(stick.getRawButton(buttonAssignment) == true) {
			air.extend();
			System.out.println("Extend");
		}

		else {
			air.retract();
			System.out.println("Retract");
		}
		air.printDiags();
	}
	

}
