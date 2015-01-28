package org.usfirst.frc.team1683.robot.power;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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


	public RobotElementListener(int channel, PrintWriter log) {
		this.channel = channel;
		currents = new ArrayList<Double>();
		this.log = log;
	}

	public void listenAndReact(){
		if (updatedValues() && currents.size()>=30){
			double current = currents.get(currents.size()-1);
			Double [] currentArray = (Double[]) currents.toArray();
			double mean = StatisticsCalculator.mean(currentArray);
			double stDev = StatisticsCalculator.standardDeviation(currentArray, mean);
			double upperEndZInterval = mean + (maxNumberOfStDev) * (stDev);
			if (current > upperEndZInterval){
				log.write("\nAbnormal Current Detected On Channel: " + channel + "- Current Was: " + current
						+ "Mean Current Was: "+ mean + "StDev Current Was: "+ stDev);
			}
		}
	}

	public boolean updatedValues(){
		double current = powerDistBoard.getCurrent(this.channel);
		if (current > 0){
			currents.add(current);
			return true;
		}else{
			return false;
		}
	}



}