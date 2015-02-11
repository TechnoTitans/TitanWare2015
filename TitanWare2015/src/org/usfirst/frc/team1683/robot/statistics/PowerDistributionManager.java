package org.usfirst.frc.team1683.robot.statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

/**
 * 
 * @author Animesh Koratana
 *
 */
public class PowerDistributionManager implements Runnable{
	
	Logger log;
	public final static String KEY = "PowerDistributionManager";
	
	private PowerDistributionPanel powerDistPanel;
	List<RobotElementListener> channelListeners;
	RobotStatisticListener temperature;
	RobotStatisticListener voltage;
	RobotStatisticListener power;
	RobotStatisticListener energy;
	RobotStatisticListener current;
	

	public PowerDistributionManager(int ... channelsToWatch) {
		initLogger();
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
	
	public void start(){
		Thread thread = new Thread(this, "Power Distribution Manager");
		thread.start();
	}
	
	private void initLogger(){
		log = Logger.getGlobal();  
        FileHandler fh;  
        try {  
            // This block configure the logger with handler and formatter
            fh = new FileHandler("/Logs/PowerDistManager.log");  
            log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  

            // the following statement is used to log any messages  
            log.info("Power Distribution Manager:");  

        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
	
	private void periodicCheck(){
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
			periodicCheck();
		}
	}

}
