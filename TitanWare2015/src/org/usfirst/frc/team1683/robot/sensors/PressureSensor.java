package org.usfirst.frc.team1683.robot.sensors;

/**Class to handle any pressure sensors used.
 * @author Sreyas Mirthipati
 *
 */
public class PressureSensor implements Sensor {
	//Instance data
	private double pressure;
	private int portNumber;
	
	/**Constructor
	 * @param portNumber
	 */
	public PressureSensor(int portNumber)
	{
		this.portNumber = portNumber;
	}

	
	/**
     * Gets raw data from pressure sensor
     * @return raw data as double
     */
	public double getRaw() {
		return 0;
	}
	
	
}
