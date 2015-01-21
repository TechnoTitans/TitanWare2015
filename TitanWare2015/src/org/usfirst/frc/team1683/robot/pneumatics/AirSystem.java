package org.usfirst.frc.team1683.robot.pneumatics;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

public class AirSystem {
	final static boolean EXTENDED = true;
	final static boolean RETRACTED = false;
	Compressor compressor;
	
	ArrayList<Solenoid> solenoids = new ArrayList<Solenoid>();
	
	public AirSystem(Compressor compressor, int[] solenoid ) {
		this.compressor = compressor;
		for (int i = 0; i < solenoid.length; i++) {
			int channel = solenoid[i];
			solenoids.add(new Solenoid(channel));
		}
	}
	
	public void extend(){
		for(Solenoid solenoid : solenoids){
			solenoid.set(EXTENDED);
		}
	}
	
	public void retract(){
		for(Solenoid solenoid : solenoids){
			solenoid.set(RETRACTED);
		}
	}

	public Compressor getCompressor() {
		return compressor;
	}

	public ArrayList<Solenoid> getSolenoids() {
		return solenoids;
	}

}
