package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.main.DriverStation;

public class AutonomousSelector {
	int autonomousMode;
	Autonomous autonomous;
	public AutonomousSelector(){
		autonomousMode = DriverStation.getInt("autonomousMode");
	}
	

}
