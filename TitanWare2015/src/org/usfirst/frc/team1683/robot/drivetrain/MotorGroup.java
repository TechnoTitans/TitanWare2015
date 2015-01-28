package org.usfirst.frc.team1683.robot.drivetrain;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Animesh Koratana
 *
 */
public class MotorGroup {
	List<Motor> motors;
	
	public MotorGroup(int[] channelNumbers, Class<Motor> motorType)  {
		motors = new ArrayList<Motor>();
		for (int i = 0; i < channelNumbers.length; i++) {
			int j = channelNumbers[i];
			try {
				motorType.getConstructor(Double.class).newInstance(j);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void moveDistance(double distanceInMeters){
		for (Motor motor: motors){
			motor.moveDistance(distanceInMeters);
		}
	}
	
	public void moveDegrees(double degrees){
		for (Motor motor: motors){
			motor.moveDegrees(degrees);
		}
	}
	
	public void set(double speed){
		for (Motor motor: motors){
			motor.set(speed);
		} 
	}
	
	public void stop() {
		for (Motor motor: motors){
			motor.stop();
		} 
	}
}
