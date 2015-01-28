package org.usfirst.frc.team1683.robot.power;

/**
 * 
 * @author Animesh Koratana
 *
 */
public class StatisticsCalculator {
	
	public static double mean (Double[] data) { 
		// Calculate the mean 
		double mean = 0; 
		final int n = data.length; 
		if ( n < 2 ) { 
			return Double.NaN; 
		} 
		for ( int i=0; i<n; i++ ) { 
			mean += data[i]; 
		} 
		return mean /= n; 
	} 


	public static double standardDeviation (Double[] data, double mean) { 
		// sd is sqrt of sum of (values-mean) squared divided by n - 1 
		final int n = data.length; 
		// calculate the sum of squares 
		double sum = 0; 
		for ( int i=0; i<n; i++ ) 
		{ 
			final double v = data[i] - mean; 
			sum += v * v; 
		} 
		// Change to ( n - 1 ) to n if you have complete data instead of a sample. 
		return Math.sqrt( sum / (n) ); 
	} 

}
