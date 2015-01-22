package org.usfirst.frc.team1683.robot.drivetrain;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MotorGroup {
	List<Motor> motors;
	
	public MotorGroup(double[] channelNumbers, Class<Motor> motorType)  {
		motors = new ArrayList<Motor>();
		for (int i = 0; i < channelNumbers.length; i++) {
			double j = channelNumbers[i];
			try {
				motorType.getConstructor(Double.class).newInstance(j);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void moveMotors(double distanceInMeters){
		for (Motor motor: motors){
			motor.moveDistance(distanceInMeters);
		}
	}
	
	public void moveDegrees(double degrees){
		
	}
}
