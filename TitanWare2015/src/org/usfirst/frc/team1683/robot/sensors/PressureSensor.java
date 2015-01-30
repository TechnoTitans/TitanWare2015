package org.usfirst.frc.team1683.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**Class to handle any pressure sensors used.
 * @author Sreyas Mirthipati
 *
 */
public class PressureSensor implements Sensor {
	//Instance data
	private final double MIN_PRESSURE_VOLTS = 0.5;
	private final double MAX_PRESSURE_VOLTS = 4.5;
	private final double VOLTAGE_RANGE = MAX_PRESSURE_VOLTS - MIN_PRESSURE_VOLTS;
	private final double MAX_PRESSURE = 200;
	private final double PRESSURE_SLOPE = MAX_PRESSURE/VOLTAGE_RANGE;
	private double averageVoltage;
	private double pressure;
	private int portNumber;
	AnalogInput sensor;
	
	/**Constructor
	 * @param portNumber
	 */
	public PressureSensor(int portNumber)
	{
		this.portNumber = portNumber;
		sensor = new AnalogInput(portNumber);
	}
	
	/**
     * Gets raw data from pressure sensor
     * @return raw data as double
     */
	public double getRaw() {
		return sensor.getAverageVoltage();
	}
	
	public int getPort() {
		return portNumber;
	}
	
	public double getPressure() {
		averageVoltage = sensor.getAverageVoltage();
        if (averageVoltage < MIN_PRESSURE_VOLTS)
        {
            SmartDashboard.putBoolean("LowPressureTransducerError", true);
            pressure = 0;
            
        }
        else if (averageVoltage > MAX_PRESSURE_VOLTS)
        {
            SmartDashboard.putBoolean("LowPressureTransducerError", true);
            pressure = MAX_PRESSURE;
        }
        else
        {
            SmartDashboard.putBoolean("LowPressureTransducerError", false);
            pressure = PRESSURE_SLOPE*(averageVoltage-MIN_PRESSURE_VOLTS);
        }
        return pressure;
	}
	
	
}
