package org.usfirst.frc.team1683.robot.drivetrain;

public interface Motor{
	public void moveDistance(double distanceInMeters); //in meters
	public void set(double speed);
	public void setSpeeds(double low, double medium, double high);
	public void moveDegrees(double degrees);
}
