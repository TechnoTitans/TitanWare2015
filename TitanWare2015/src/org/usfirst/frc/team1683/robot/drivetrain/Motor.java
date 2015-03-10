package org.usfirst.frc.team1683.robot.drivetrain;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * 
 * @author Animesh Koratana
 *
 */
public interface Motor extends PIDOutput{
	public void moveDistance(double distanceInMeters); //in meters
	public void moveDistanceInches(double distanceInInches);
	public void set(double speed);
	public void moveDegrees(double degrees);
	public void stop();
	public boolean hasEncoder();
	public Encoder getEncoder();
	public int getChannel();
	public void enableBrakeMode(boolean enable);
	public void enableLimitSwitch(boolean enableFwd, boolean enableBack);
	public boolean isFwdLimitSwitchClosed();
	public boolean isRevLimitSwitchClosed();
}
