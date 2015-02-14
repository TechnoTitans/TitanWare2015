package org.usfirst.frc.team1683.robot.drivetrain;
/**
 * 
 * @author Animesh Koratana
 *
 */
public interface Motor {
	public void moveDistance(double distanceInMeters); //in meters
	public void moveDistanceInches(double distanceInInches);
	public void set(double speed);
	public void moveDegrees(double degrees);
	public void stop();
	public boolean hasEncoder();
	public Encoder getEncoder();
	public int getChannel();
}
