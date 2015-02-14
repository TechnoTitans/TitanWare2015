package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.main.DriverStation;

public class PreferencesList{
	
	public static void set(){
		DriverStation.prefDouble("delay", 0.075);
		DriverStation.prefDouble("distance", 1);
		DriverStation.prefDouble("bearing", 90);
		
		//Autonomous Preferences
		DriverStation.prefInt("autonomousMode", 1);
		DriverStation.prefDouble("driveDistance", 2);
		DriverStation.prefDouble("sideDistance", 2);
		DriverStation.prefDouble("liftDistance", 2);
		DriverStation.prefDouble("adjustDistance", 2);
		DriverStation.prefDouble("backDistance", 2);
		DriverStation.prefDouble("robotDistance", 2);
		DriverStation.prefDouble("toteSpaceDistance", 2);
		DriverStation.prefBoolean("enablePrinting", true);
		
		DriverStation.prefBoolean("enableLightSensor", true);
	}
	
}
