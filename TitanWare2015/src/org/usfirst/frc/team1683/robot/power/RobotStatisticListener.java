package org.usfirst.frc.team1683.robot.power;

import java.io.PrintWriter;
import java.util.List;
/**
 * 
 * @author Animesh Koratana
 *
 */
public class RobotStatisticListener {
	
	private String statistic;
	
	private List<Double> data;
	
	private PrintWriter log;
	private static int maxNumberOfStDev = 2;
	
	public RobotStatisticListener(String statistic, PrintWriter log) {
		this.log = log;
	}
	
	public void listen(double value){
		if (updateValues(value) && data.size()>=30){
			Double [] currentArray = (Double[]) data.toArray();
			double mean = StatisticsCalculator.mean(currentArray);
			double stDev = StatisticsCalculator.standardDeviation(currentArray, mean);
			double upperEndZInterval = mean + (maxNumberOfStDev) * (stDev);
			if (value > upperEndZInterval){
				log.write("\nAbnormal " + statistic + " Detected On PowerDistBoard- "
						+ statistic +  " Was: " + value
						+ "Mean  Was: "+ mean 
						+ "StDev Was: "+ stDev);
			}
		}
	}

	public boolean updateValues(double value){
		if (value > 0){
			data.add(value);
			return true;
		}else{
			return false;
		}
	}

}
