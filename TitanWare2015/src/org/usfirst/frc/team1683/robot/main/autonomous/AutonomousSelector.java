package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.main.DriverStation;

public class AutonomousSelector {
	int autonomousMode;
	public AutonomousSelector(){
		autonomousMode = DriverStation.getInt("autonomousMode");
	}
	
	public void run(){
		if (autonomousMode == 1)
			Auto_1.run();
		else if (autonomousMode == 2)
			Auto_2.run();
	}
}
