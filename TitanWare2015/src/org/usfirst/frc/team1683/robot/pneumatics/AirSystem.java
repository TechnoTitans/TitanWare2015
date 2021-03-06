package org.usfirst.frc.team1683.robot.pneumatics;

import java.util.ArrayList;

import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.Solenoid;

/**Class to represent any particular system of solenoids acting in unison.
 * The compressor is shared amongst these systems.
 * @author Animesh Koratana
 *
 */
public class AirSystem {
	//Constants to change depending on plumbing of robot
	final static boolean EXTENDED = true;
	final static boolean RETRACTED = false;

	int PcmNum;
	ArrayList<Solenoid> solenoids = new ArrayList<Solenoid>();
	PressureSensor pressure;
	
	/**
	 * @param compressor - the compressor for the air system, usually one per robot.
	 * @param solenoid - an int array of all the solenoid channels of an AirSystem.
	 */
	public AirSystem(int[] solenoid , PressureSensor pressure) {
		this.pressure = pressure;
		for (int i = 0; i < solenoid.length; i++) {
			int channel = solenoid[i];
			solenoids.add(new Solenoid(channel));
		}
	}
	
	/**
	 * @param compressor - the compressor for the air system, usually one per robot.
	 * @param PcmNum - the PCM ID that the AirSystem is connected to.
	 * @param solenoid - an int array of all the solenoid channels of an AirSystem.
	 */
	public AirSystem(int PcmNum, int[] solenoid ){
		this.PcmNum = PcmNum;
		for (int i = 0; i < solenoid.length; i++) {
			int channel = solenoid[i];
			solenoids.add(new Solenoid(PcmNum,channel));
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
	
	public boolean isExtended(){
		boolean isExtended = true;
		for(Solenoid solenoid : solenoids){
			if (!solenoid.get())
				isExtended = false;
		}
		return isExtended;
	}
	
	/**Get the current being used by the compressor.
	 * @return current consumed in amps for the compressor
	 */
//	public float getCurrent(){
//		return compressor.getCompressorCurrent();
//	}
	
	/**Prints various messages to NetConsole regarding the status of the AirSystem
	 * 
	 */
	public void printDiags(){
		//Problems-System.out.println("Compressor disconnected: " + compressor.getCompressorNotConnectedFault());
//		System.out.println("Compressor current is too high: " +
//							compressor.getCompressorCurrentTooHighFault());
		System.out.println("Solenoids blacklisted: " + countBlacklistedSolenoids());
//		System.out.println("Compressor current: " + getCurrent() + "Amps");
		System.out.println("Pressure: " + pressure.getPressure());
	}
	
	
	/**
	 * @return the count of blacklisted solenoids
	 */
	public int countBlacklistedSolenoids(){
		int count = 0;
		for(Solenoid solenoid : solenoids){
			if (solenoid.isBlackListed() == true){
				count++;
			}
		}
		return count;
	}

	/**
	 * @return the ArrayList of all the solenoids in an air system.
	 */
	public ArrayList<Solenoid> getSolenoids() {
		return solenoids;
	}

}
