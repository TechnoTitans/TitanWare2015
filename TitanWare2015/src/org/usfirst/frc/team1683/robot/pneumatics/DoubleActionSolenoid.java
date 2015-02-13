package org.usfirst.frc.team1683.robot.pneumatics;

import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.Compressor;

public class DoubleActionSolenoid {
	AirSystem frontAirSystem;
	AirSystem backAirSystem;
	
	/**
	 * @param pistons
	 * @param pressure - PressureSensor
	 * @param initState - initial state of controlling boolean value
	 */
	public DoubleActionSolenoid(int[] pistons, PressureSensor pressure) { //front piston, back Piston
		Compressor compressor = new Compressor();
		frontAirSystem = new AirSystem(compressor, new int[]{pistons[0]}, pressure);
		backAirSystem = new AirSystem(compressor, new int[]{pistons[1]}, pressure);
	}
	
	/**
	 * @author Animesh Koratana
	 * returns the front air system of dual action pistons
	 * @return frontAirSystem
	 */
	public AirSystem getFrontAirSystem(){
		return frontAirSystem;
	}
	
	/**
	 * @author Animesh Koratana
	 * returns the back air system of dual action pistons
	 * @return backAirSystem
	 */
	public AirSystem getBackAirSystem(){
		return backAirSystem;
	}
	
	/**
	 * @author Animesh Koratana
	 * switches between the two useful states of the dual action solenoids
	 */
	public void changeState(){
		if (frontAirSystem.isExtended()){
			frontAirSystem.retract();
			backAirSystem.extend();
		}else{
			frontAirSystem.extend();
			backAirSystem.retract();
		}
	}
}
