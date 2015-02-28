package org.usfirst.frc.team1683.robot.pneumatics;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;

/**This class is to be used instead of the built in Compressor class whenever we want
 * to use a Spike relay to control the compressor over the PCM closed loop method.
 * @author Sreyas Mirthipati
 *
 */
public class OldCompressor {
	DigitalInput pressureSwitch;
	Relay compressor;

	/**Constructor
	 * @param switchChannel - the Digital IO channel that the pressure sensor is connected to
	 * @param compChannel - the Relay channel that the Spike is connected to 
	 */
	public OldCompressor(int switchChannel, int compChannel) {
		pressureSwitch = new DigitalInput(switchChannel);
		compressor = new Relay(compChannel, Direction.kForward);
	}

	/**To be called periodically to check for correct pressure
	 *  and control compressor as needed
	 */
	public void pressurize() {
		if(pressureSwitch.get() == false)
			compressor.set(Value.kOn);
		else
			compressor.set(Value.kOff);
	}
}
