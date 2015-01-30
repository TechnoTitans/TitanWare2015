package org.usfirst.frc.team1683.robot.drivetrain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Animesh Koratana
 *
 */
public class MotorGroup {
	List<Motor> motors;
	Encoder encoder;
	
	public MotorGroup(int[] channelNumbers, Class<Motor> motorType, boolean inverseDirection)  {
		motors = new ArrayList<Motor>();
		for (int i = 0; i < channelNumbers.length; i++) {
			int j = channelNumbers[i];
			if (motorType.equals(Talon.class)){
				motors.add(new Talon(j, inverseDirection));
			}else if (motorType.equals(TalonSRX.class)){
				motors.add(new TalonSRX(j, inverseDirection));
			}
		}
	}
	
	public MotorGroup(int[] channelNumbers, Class<Motor> motorType, boolean inverseDirection, Encoder encoder)
	{
		motors = new ArrayList<Motor>();
		for (int i = 0; i < channelNumbers.length; i++) {
			int j = channelNumbers[i];
			if (motorType.equals(Talon.class)){
				motors.add(new Talon(j, inverseDirection));
			}else if (motorType.equals(TalonSRX.class)){
				motors.add(new TalonSRX(j, inverseDirection));
			}
		}
		this.encoder = encoder;
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
