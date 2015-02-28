package org.usfirst.frc.team1683.robot.pneumatics;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.RobotBase;

/**This class is to be used instead of the built in Compressor class whenever we want
 * to use a Spike relay to control the compressor over the PCM closed loop method.
 * @author Sreyas Mirthipati
 *
 */
public class OldCompressor implements Runnable {
	DigitalInput pressureSwitch;
	Relay compressor;
	RobotBase base;

	/**Constructor
	 * @param switchChannel - the Digital IO channel that the pressure sensor is connected to
	 * @param compChannel - the Relay channel that the Spike is connected to 
	 */
	public OldCompressor(int switchChannel, int compChannel, RobotBase base) {
		pressureSwitch = new DigitalInput(switchChannel);
		compressor = new Relay(compChannel, Direction.kForward);
		this.base = base;
	}
	
	public void start(){
		Thread thread = new Thread(this, "Compressor Manager");
		thread.setPriority(Thread.NORM_PRIORITY);
		thread.start();
	}

	/**Constantly checks the pressure and runs compressor if the robot is enabled
	 *Requires a boolean in the main class to change state when the robot is enabled
	 *and disabled.
	 */
	public void run() {
		while(true) {
			while(base.isEnabled()){
				if(pressureSwitch.get() == false){
					compressor.set(Value.kOn);
				}else{
					compressor.set(Value.kOff);
				}
			}
		}
	}
}
