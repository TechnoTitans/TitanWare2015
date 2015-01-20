package org.usfirst.frc.team1683.robot.sensors;

public class PressureSensor implements Sensor {
	//Instance data
	private double pressure;
	private int portNumber;
	
	/**Creates a PressureSensor by taking in a portNumber.
	 * @param portNumber-an int value that defines the analog port
	 * number the sensor is connected to.
	 */
	public PressureSensor(int portNumber)
	{
		this.portNumber = portNumber;
	}

	
	/** 
	 * Returns raw sensor data as a double.
	 */
	public double getRaw() {
		return 0;
	}
	
	
}
