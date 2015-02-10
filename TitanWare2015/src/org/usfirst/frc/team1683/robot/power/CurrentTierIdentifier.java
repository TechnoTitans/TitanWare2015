package org.usfirst.frc.team1683.robot.power;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class CurrentTierIdentifier implements Runnable{
	
	final private double stDevTierDifference = 2.9;
	ArrayList<List<Double>> tiers; //each list represents a distribution of currents for each tier
	
	private List<Double> currentTier;
	private int currentTierIndex;

	
	PowerDistributionPanel panel;
	double idleValue;
	
	int motorChannel;
	
	public CurrentTierIdentifier( PowerDistributionPanel panel, int numberOfTiers, int motorChannel) {
		this.motorChannel = motorChannel;
		tiers = new ArrayList<List<Double>>();
		this.panel = panel;
		for (int i = 0; i<numberOfTiers; i++){
			tiers.add(new ArrayList<Double>());
		}
		idleValue = panel.getCurrent(this.motorChannel);
		new Thread(this, "Tier Manager").start();
	}
	
	
	@Override
	public void run() {
		while (true){
			if (updatedValues()){
				double value = currentTier.get(currentTier.size()-1);
				Double [] tierArray = currentTier.toArray(new Double[currentTier.size()]);
				double mean = StatisticsCalculator.mean(tierArray);
				double stDev = StatisticsCalculator.standardDeviation(tierArray, mean);
				double zScore = (value - mean)/ stDev;
				checkToMoveToNewTier(zScore);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void checkToMoveToNewTier(double zScore){
		if (zScore > stDevTierDifference){
			currentTierIndex++;
			currentTier = tiers.get(currentTierIndex);
		}
	}
	
	public int getCurrentTier(){
		return findCurrentTier(panel.getCurrent(motorChannel));
	}
	

	public boolean updatedValues(){
		double current = panel.getCurrent(this.motorChannel);
		if (current > (idleValue+(idleValue * 0.09)) || current< (idleValue-(idleValue * 0.09))){
			currentTier.add(current);
			return true;
		}else{
			return false;
		}
	}


	
	private int findCurrentTier(double currentValue){
	int index = 0;
	int currentMinumumTier = 0;
	double currentMinumumZ = 0;
	double [] means = new double[tiers.size()];
	double [] sigma = new double[tiers.size()];
	double [] stDevOfValue = new double[tiers.size()];
	for (Iterator<List<Double>> iterator = tiers.iterator(); iterator.hasNext();) {
		List<Double> tier = (List<Double>) iterator.next();
		Double [] tierArray = tier.toArray(new Double[tier.size()]);
		means[index]=StatisticsCalculator.mean(tierArray);
		sigma[index]=StatisticsCalculator.standardDeviation(tierArray, means[index]);
		stDevOfValue[index] = (currentValue - means[index]) / sigma [index];
		if (index == 0){
			currentMinumumTier = index;
			currentMinumumZ = stDevOfValue[index];
		}else if (stDevOfValue[index] < currentMinumumZ){
			currentMinumumZ = stDevOfValue[index];
			currentMinumumTier = index;
		}
		index++;
	}		
	
	return currentMinumumTier;
}
	

}
