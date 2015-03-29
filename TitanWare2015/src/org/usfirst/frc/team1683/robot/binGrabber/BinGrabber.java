package org.usfirst.frc.team1683.robot.binGrabber;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.DoubleActionSolenoid;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

/**
 * The flappy wing thing that grabs the green bins in auto mode
 * @author Sreyas Mirthipati
 *
 */
public class BinGrabber {
	DoubleActionSolenoid binGrabber;

	/**
	 * Default Constructor
	 * @param ports the ports of the solenoids
	 * @param inverse set to false by default, set to true to invert function
	 * @param pressure needed for DoubleActionSolenoid, not used here
	 */
	public BinGrabber(int[] ports, boolean inverse, PressureSensor pressure) {
		binGrabber = new DoubleActionSolenoid(ports, pressure, inverse);
	}
	
	/**
	 * Telop control method- call in teleopPeriodic
	 */
	public void grabMode() {
		if(DriverStation.auxStick.getRawButton(HWR.CONTRACT_BINGRABBER)) {
			this.contract();
		}
		else if (DriverStation.auxStick.getRawButton(HWR.EXPAND_BINGRABBER)) {
			this.expand();
		}
	}
	
	/**
	 * Moves out the grabber
	 */
	public void expand() {
		binGrabber.extend();
	}
	
	/**
	 * moves in the grabber
	 */
	public void contract() {
		binGrabber.retract();
	}
}
