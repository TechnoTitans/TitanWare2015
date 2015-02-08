package org.usfirst.frc.team1683.robot.vision;

public class Vector {
	
	double magnitude;
	double direction;
	
	/**
	 * Constructor
	 * @param magnitude
	 * @param angle
	 */
	public Vector(double angle, double magnitude) {
		this.setVector(direction, magnitude);
	}
	
	public Vector(double originX, double originY, double targetX, double targetY) {
		this.setVector(originX, originY, targetX, targetY);
	}
	/**
	 * Sets direction of vector. If angle is > 360, will make angle < 360.
	 * @param angle Direction of angle from the positive X axis.
	 */
	public void setDirection(double angle) {
		if (angle > 360) {
			angle = angle % 360;
		}
		
		this.direction = angle;
	}
	/**
	 * Sets magnitude of vector.
	 * @param magnitude
	 */
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	
	/**
	 * 
	 * @param direction
	 * @param magnitude
	 */
	public void setVector(double direction, double magnitude) {
		
	}
	
	/**
	 * 
	 * @param originX
	 * @param originY
	 * @param targetX
	 * @param targetY
	 */
	public void setVector(double originX, double originY, double targetX, double targetY) {
		double deltaX = targetX - originX;
		double deltaY = targetY - targetY;
		
		this.magnitude = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		this.direction = Math.toDegrees(Math.atan(deltaY/deltaX));
	}
}
