package org.usfirst.frc.team1683.robot.power;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1683.robot.TechnoTitan;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
/**
 * 
 * @author Animesh Koratana
 *
 */
public class RobotElementListener{

	private static int maxNumberOfStDev = 2;

	PowerDistributionPanel powerDistBoard;
	int channel;
	
	List<Double> currents;
	PrintWriter log;
	
	private double idleValue = 0;


	public RobotElementListener(PowerDistributionPanel powerDistPanel, int channel, PrintWriter log) {
		this.powerDistBoard = powerDistPanel;
		this.channel = channel;
		currents = new ArrayList<Double>();
		this.log = log;
		idleValue = powerDistBoard.getCurrent(this.channel);
	}

	public void listenAndReact(){
		if (updatedValues() && currents.size()>=30){
			double current = currents.get(currents.size()-1);
			Double [] currentArray = currents.toArray(new Double[currents.size()]);
			double mean = StatisticsCalculator.mean(currentArray);
			double stDev = StatisticsCalculator.standardDeviation(currentArray, mean);
			double upperEndZInterval = mean + (maxNumberOfStDev) * (stDev);
			if (current > upperEndZInterval){
				String problem = "\nAbnormal Current Detected On Channel: " + channel + "- Current Was: " + current
						+ "Mean Current Was: "+ mean + "StDev Current Was: "+ stDev;
				if (log == null){
					System.out.println(problem);
				}else{
					log.write(problem);
				}
				
			}
		}
	}

	public boolean updatedValues(){
		double current = powerDistBoard.getCurrent(this.channel);
		if (current > (idleValue+(idleValue * 0.05)) || current< (idleValue-(idleValue * 0.05))){
			currents.add(current);
			if (TechnoTitan.debug){
				System.out.println("Channel " + channel + "Current: "+ current );
			}
			return true;
		}else{
			return false;
		}
	}



}