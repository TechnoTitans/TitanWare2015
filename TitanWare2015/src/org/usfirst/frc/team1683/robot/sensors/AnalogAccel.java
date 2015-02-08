package org.usfirst.frc.team1683.robot.sensors;

import edu.wpi.first.wpilibj.AnalogAccelerometer;

/**Class to handle separate analog accelerometer
 * @author Sreyas Mirthipati
 */
public class AnalogAccel extends AnalogAccelerometer implements Sensor {

	/**
	 * Constructor
	 * @param channel - port that analog accelerometer is plugged into
	 */
	public AnalogAccel(int channel) {
		super(channel);
	}

	@Override
	/**
	 * @return Acceleration detected in Gs.
	 */
	public double getRaw() {
		return super.getAcceleration();
	}

}
