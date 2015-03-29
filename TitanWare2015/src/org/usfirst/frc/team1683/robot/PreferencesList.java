package org.usfirst.frc.team1683.robot;

import org.usfirst.frc.team1683.robot.main.DriverStation;

public class PreferencesList{
	
	public static void set(){
		DriverStation.prefDouble("delay", 0.075);
		DriverStation.prefDouble("distance", 1);
		DriverStation.prefDouble("bearing", 90);
		
		//Autonomous Preferences
		DriverStation.prefInt("autonomousMode", 1);
		DriverStation.prefDouble("driveDistance", 135); //inches
		DriverStation.prefDouble("sideDistance", -85); //inches
		DriverStation.prefDouble("liftDistance", 2);
		DriverStation.prefDouble("adjustDistance", 2);
		DriverStation.prefDouble("backDistance", 2);
		DriverStation.prefDouble("robotDistance", 2);
		DriverStation.prefDouble("toteSpaceDistance", 2);
		DriverStation.prefBoolean("enablePrinting", true);
		DriverStation.prefDouble("driveTime", 1.75); //sec
		DriverStation.prefDouble("sideTime", 2); //sec
		DriverStation.prefDouble("coopDistance",276);
		DriverStation.prefDouble("stepDistance", 12.3);
		DriverStation.prefDouble("secondDelay", 0); //sec
		DriverStation.prefDouble("tilterTime", 0); //sec
		DriverStation.prefDouble("sideSpeed", HWR.HDRIVE_SPEED);
		
		DriverStation.prefBoolean("enableLightSensor", true);
		
		//PID Preferences
		DriverStation.prefBoolean("EnablePID", false);
		DriverStation.prefDouble("PIDValueP", 0);
		DriverStation.prefDouble("PIDValueI", 0);
		DriverStation.prefDouble("PIDValueD", 0);
		DriverStation.prefDouble("PIDTolerance", 5);
		
		//Gyro Value
		DriverStation.prefDouble("kpside", -0.05);
		DriverStation.prefDouble("kpforward", 0.03);
	}
	
}
