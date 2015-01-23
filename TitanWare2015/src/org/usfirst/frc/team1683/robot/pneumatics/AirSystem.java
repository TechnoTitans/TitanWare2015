package org.usfirst.frc.team1683.robot.pneumatics;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

/**Class to represent any particular system of solenoids acting in unison.
 * The compressor is shared amongst these systems.
 * @author Sreyas Mirthipati
 *
 */
public class AirSystem {
	//Constants to change depending on plumbing of robot
	final static boolean EXTENDED = true;
	final static boolean RETRACTED = false;
	
	Compressor compressor;
	
	ArrayList<Solenoid> solenoids = new ArrayList<Solenoid>();
	
	/**
	 * @param compressor - the compressor for the air system, usually one per robot.
	 * @param solenoid - an int array of all the solenoid channels of an AirSystem.
	 */
	public AirSystem(Compressor compressor, int[] solenoid ) {
		this.compressor = compressor;
		for (int i = 0; i < solenoid.length; i++) {
			int channel = solenoid[i];
			solenoids.add(new Solenoid(channel));
		}
	}
	
	/**
	 * sets all the solenoids of the AirSystem instance to "extended position"
	 */
	public void extend(){
		for(Solenoid solenoid : solenoids){
			solenoid.set(EXTENDED);
		}
	}
	
	/**
	 * sets all the solenoids of the AirSystem instance to "retracted position"
	 */
	public void retract(){
		for(Solenoid solenoid : solenoids){
			solenoid.set(RETRACTED);
		}
	}

	/**
	 * @return the compressor
	 */
	public Compressor getCompressor() {
		return compressor;
	}

	/**
	 * @return the ArrayList of all the solenoids in an air system.
	 */
	public ArrayList<Solenoid> getSolenoids() {
		return solenoids;
	}

}
