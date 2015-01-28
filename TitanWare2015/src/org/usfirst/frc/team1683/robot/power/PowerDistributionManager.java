package org.usfirst.frc.team1683.robot.power;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * 
 * @author Animesh Koratana
 *
 */
public class PowerDistributionManager {
	
	private PowerDistributionPanel powerDistPanel;
	List<RobotElementListener> channelListeners;
	RobotStatisticListener temperature;
	RobotStatisticListener voltage;
	RobotStatisticListener power;
	RobotStatisticListener energy;
	RobotStatisticListener current;

	public PowerDistributionManager(PrintWriter log, int ... channelsToWatch) {
		powerDistPanel = new PowerDistributionPanel();
		for (int i = 0; i < channelsToWatch.length; i++) {
			int channel = channelsToWatch[i];
			RobotElementListener listener = new RobotElementListener(channel, log);
			channelListeners.add(listener);
		}
		temperature = new RobotStatisticListener("Temperature", log);
		voltage = new RobotStatisticListener("Voltage", log);
		power = new RobotStatisticListener("Power", log);
		energy = new RobotStatisticListener("Energy", log);
		current = new RobotStatisticListener("Current", log);
	}
	
	public PowerDistributionPanel getPowerDistributionPanel(){
		return powerDistPanel;
	}
	
	public void periodicCheck(){
		temperature.listen(powerDistPanel.getTemperature());
		voltage.listen(powerDistPanel.getVoltage());
		power.listen(powerDistPanel.getTotalPower());
		energy.listen(powerDistPanel.getTotalEnergy());
		current.listen(powerDistPanel.getTotalCurrent());
		for (Iterator<RobotElementListener> iterator = channelListeners.iterator(); iterator.hasNext();) {
			RobotElementListener robotElementListener = (RobotElementListener) iterator.next();
			robotElementListener.listenAndReact();
		}
	}

}
