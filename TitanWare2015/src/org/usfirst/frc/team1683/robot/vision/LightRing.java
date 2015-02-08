package org.usfirst.frc.team1683.robot.vision;

import edu.wpi.first.wpilibj.Talon;

/**Class to handle the light ring for the camera
 * @author Sreyas Mirthipati
 */
public class LightRing {
	Talon ring;
	
	/**Constructor - defaults to maximum brightness when instantiated
	 * @param talonChannel - channel of the Talon that controls the light ring
	 */
	public LightRing(int talonChannel) {
		ring = new Talon(talonChannel);
		this.maxOn();
	}
	
	/**
	 * turns the light ring off
	 */
	public void off() {
		ring.set(0);
	}
	
	/**
	 * sets the light ring to maximum brightness
	 */
	public void maxOn() {
		ring.set(1);
	}
	
	/**Sets the brightness of the ring
	 * @param brightnessPercent - a value from 0 to 100 that sets the brightness
	 * of the ring
	 */
	public void setBrightness(double brightnessPercent) {
		if(brightnessPercent > 100) {
			brightnessPercent = 100;
		}
		if(brightnessPercent < 0) {
			brightnessPercent = 0;
		}
		ring.set(brightnessPercent/100);
	}
	
}
