package org.usfirst.frc.team1683.robot.power;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.usfirst.frc.team1683.robot.TechnoTitan;
/**
 * 
 * @author Animesh Koratana
 *
 */
public class RobotStatisticListener {
	
	private String statistic;
	
	private ArrayList<Double> data;
	
	private PrintWriter log;
	private static int maxNumberOfStDev = 2;
	
	private double idleValue = 0;
	
	public RobotStatisticListener(String statistic, PrintWriter log, double normalValue) {
		this.statistic = statistic;
		this.log = log;
		data = new ArrayList<Double>();
		this.idleValue = normalValue;
	}
	
	public void listen(double value){
		if (updateValues(value) && data.size()>=30){
			Double [] currentArray = data.toArray(new Double[data.size()]);
			double mean = StatisticsCalculator.mean(currentArray);
			double stDev = StatisticsCalculator.standardDeviation(currentArray, mean);
			double upperEndZInterval = mean + (maxNumberOfStDev) * (stDev);
			if (value > upperEndZInterval){
				String problem = "\nAbnormal " + statistic + " Detected On PowerDistBoard- "
						+ statistic +  " Was: " + value
						+ "Mean  Was: "+ mean 
						+ "StDev Was: "+ stDev;
				if (log == null){
					log.write(problem);
				}else{
					System.out.println(problem);
				}
				
			}
		}
	}

	public boolean updateValues(double value){
		if (value > (idleValue+(idleValue * 0.07)) || value< (idleValue-(idleValue * 0.07))){
			data.add(value);
			if (TechnoTitan.debug){
				System.out.println(statistic +": " + value);
			}
			return true;
		}else{
			return false;
		}
	}

}
