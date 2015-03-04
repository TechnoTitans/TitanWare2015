package org.usfirst.frc.team1683.robot.drivetrain;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.Joystick;

public abstract class DriveTrain{
	public final static double LOW_SPEED = .2;
	public final static double MEDIUM_SPEED = .5;
	public final static double HIGH_SPEED = 1.0;
	
	ArrayList<Motor> motors = new ArrayList<>();
	
	public abstract void goStraight(double distance);
	public abstract void goForward(double distance);
	public abstract void driveMode(Joystick leftStick, Joystick rightStick);
//	public abstract double[] antiDrift(double speed, double targetAngle);
	public abstract void stop();
	public abstract void goSideways(double distance);
	public abstract void moveSideways(double distanceInInches);
}
