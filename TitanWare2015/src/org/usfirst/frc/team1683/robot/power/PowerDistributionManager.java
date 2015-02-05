package org.usfirst.frc.team1683.robot.power;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * 
 * @author Animesh Koratana
 *
 */
public class PowerDistributionManager implements Runnable{
	
	private PowerDistributionPanel powerDistPanel;
	List<RobotElementListener> channelListeners;
	RobotStatisticListener temperature;
	RobotStatisticListener voltage;
	RobotStatisticListener power;
	RobotStatisticListener energy;
	RobotStatisticListener current;
	

	public PowerDistributionManager(PrintWriter log, int ... channelsToWatch) {
		powerDistPanel = new PowerDistributionPanel();
		channelListeners = new ArrayList<RobotElementListener>();
		for (int i = 0; i < channelsToWatch.length; i++) {
			int channel = channelsToWatch[i];
			RobotElementListener listener = new RobotElementListener(powerDistPanel, channel, log);
			channelListeners.add(listener);
		}
		temperature = new RobotStatisticListener("Temperature", log, powerDistPanel.getTemperature());
		voltage = new RobotStatisticListener("Voltage", log,powerDistPanel.getVoltage());
		power = new RobotStatisticListener("Power", log, powerDistPanel.getTotalPower());
		energy = new RobotStatisticListener("Energy", log, powerDistPanel.getTotalEnergy());
		current = new RobotStatisticListener("Current", log, powerDistPanel.getTotalCurrent());
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

	@Override
	public void run() {
		while (true){
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

}
