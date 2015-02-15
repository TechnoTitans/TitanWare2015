package org.usfirst.frc.team1683.robot.statistics;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.usfirst.frc.team1683.robot.TechnoTitan;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * 
 * @author Animesh Koratana
 *
 */
public class RobotStatisticListener {
	
	private String statistic;
	
	private ArrayList<Double> data;
	
	private Logger log;
	private static int maxNumberOfStDev = 3;
	
	private double idleValue = 0;
	
	public RobotStatisticListener(String statistic, Logger log, double normalValue) {
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
					SmartDashboard.putString(PowerDistributionManager.KEY, problem);
				}else{
					log.log(Level.WARNING, problem);
					SmartDashboard.putString(PowerDistributionManager.KEY, problem);
				}
				if (TechnoTitan.debugPowerDistribution){
					System.out.println(problem);
				}
				
			}
		}
	}

	public boolean updateValues(double value){
		if (value > (idleValue+(idleValue * 0.07)) || value< (idleValue-(idleValue * 0.07))){
			data.add(value);
			if (TechnoTitan.debugPowerDistribution){
				System.out.println(statistic +": " + value);
			}
			return true;
		}else{
			return false;
		}
	}

}
