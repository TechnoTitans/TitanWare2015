package org.usfirst.frc.team1683.robot.drivetrain;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.Joystick;

public abstract class DriveTrain{
	public final static double lowSpeed = .2;
	public final static double mediumSpeed = .5;
	public final static double highSpeed = 1.0;
	
	ArrayList<Motor> motors = new ArrayList<>();
	
	public abstract void goStraight(double distance);
	public abstract void turnAngle(double bearing);
	public abstract void setBackToOriginalPos();
	public abstract void driveMode(Joystick leftStick, Joystick rightStick);
	public abstract void antiDrift();
	
}
