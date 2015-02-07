package org.usfirst.frc.team1683.robot.sensors;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class BuiltInAccel extends BuiltInAccelerometer implements Sensor {

	public BuiltInAccel() {
		super();
	}
	
	/**
	 * @see org.usfirst.frc.team1683.robot.sensors.Sensor#getRaw()
	 * @return the magnitude of the acceleration vector
	 */
	@Override
	public double getRaw() {
		return Math.sqrt(Math.pow(super.getX(), 2) + Math.pow(super.getY(), 2) +
				Math.pow(super.getZ(), 2));
	}
	
	/**
	 * @return  the X,Y,Z accelerations in an array of doubles
	 */
	public double[] getArray() {
		return new double[]{super.getX(),super.getY(),super.getZ()};
	}
	
	/**
	 * @see edu.wpi.first.wpilibj.BuiltInAccelerometer#getX()
	 */
	public double getX() {
		return super.getX();
	}
	
	/**
	 * @see edu.wpi.first.wpilibj.BuiltInAccelerometer#getY()
	 */
	public double getY() {
		return super.getY();
	}
	
	/**
	 * @see edu.wpi.first.wpilibj.BuiltInAccelerometer#getZ()
	 */
	public double getZ() {
		return super.getZ();
	}
	
	/**
	 * @return the angle of the robot from front to back
	 */
	public double getYZAngle() {
		double angle = Math.atan(super.getY()/super.getZ());
		return Math.toDegrees(angle);
	}
	
	/**
	 * @return the angle of the robot from side to side
	 */
	public double getXZAngle() {
		double angle = Math.atan(super.getX()/super.getZ());
		return Math.toDegrees(angle);
	}

}
