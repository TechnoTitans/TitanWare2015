package org.usfirst.frc.team1683.robot.drivetrain;

import java.util.ArrayList;
import edu.wpi.first.wpilibj.Joystick;

public abstract class DriveTrain{
	public final static double LOW_SPEED = .2;
	public final static double MEDIUM_SPEED = .5;
	public final static double HIGH_SPEED = 1.0;
	
	ArrayList<Motor> motors = new ArrayList<>();
	
	public abstract void goStraight(double distance);
	public abstract void turnAngle(double bearing,MotorGroup left, MotorGroup right);
	public abstract void setBackToOriginalPos();
	public abstract void driveMode(Joystick leftStick, Joystick rightStick);
	public abstract void antiDrift();
	public abstract void stop();
	public abstract void goSideways(double distance);
}
