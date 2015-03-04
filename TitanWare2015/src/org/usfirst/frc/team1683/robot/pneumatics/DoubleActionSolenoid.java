package org.usfirst.frc.team1683.robot.pneumatics;

import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

public class DoubleActionSolenoid {
	AirSystem controlAirSystem;
	AirSystem followingAirSystem;
	
	/**
	 * @param pistons
	 * @param pressure - PressureSensor
	 * @param initState - initial state of controlling boolean value
	 */
	public DoubleActionSolenoid(int[] pistons, PressureSensor pressure) { //front piston, back Piston
		controlAirSystem = new AirSystem(new int[]{pistons[0]}, pressure);
		followingAirSystem = new AirSystem(new int[]{pistons[1]}, pressure);
	}
	
	/**
	 * @author Animesh Koratana
	 * returns the front air system of dual action pistons
	 * @return frontAirSystem
	 */
	public AirSystem getControlAirSystem(){
		return controlAirSystem;
	}
	
	/**
	 * @author Animesh Koratana
	 * returns the back air system of dual action pistons
	 * @return backAirSystem
	 */
	public AirSystem getFollowingAirSystem(){
		return followingAirSystem;
	}
	
	/**
	 * @author Animesh Koratana
	 * switches between the two useful states of the dual action solenoids
	 */
	public void changeState(){
		if (controlAirSystem.isExtended()){
			controlAirSystem.retract();
			followingAirSystem.extend();
		}else{
			controlAirSystem.extend();
			followingAirSystem.retract();
		}
	}
	
	public void extend(){
		controlAirSystem.extend();
		followingAirSystem.retract();
	}
	
	public void retract(){
		controlAirSystem.retract();
		followingAirSystem.extend();
	}
	
	public boolean isExtended(){
		return controlAirSystem.isExtended();
	}
}
