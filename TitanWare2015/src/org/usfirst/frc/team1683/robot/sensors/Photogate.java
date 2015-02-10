package org.usfirst.frc.team1683.robot.sensors;

/**This class handles photogate sensors.
 * @author Sreyas Mirthipati
 *
 */
public class Photogate extends edu.wpi.first.wpilibj.DigitalInput implements Sensor {

	boolean isBlocked;
	
	/**Constructor
	 * @param portNumber - the DIO port that the sensor is connected to
	 */
	public Photogate(int portNumber){
		super(portNumber);
	}

	/**
	 * @return a boolean that is true if the photogate is blocked
	 */
	public boolean getIsBlocked() {
		return super.get();
	}

	/**DO NOT USE THIS METHOD
	 * 
	 * @see org.usfirst.frc.team1683.robot.sensors.Sensor#getRaw()
	 */
	@Override
	public double getRaw() {
		return 0;
	}

}
