package org.usfirst.frc.team1683.robot.main;

import org.usfirst.frc.team1683.robot.main.autonomous.Auto_1;

public class Autonomous {

	public static int autonomousMode;
	Auto_1 auto1;
	
	public Autonomous(int autonomousMode){
		Autonomous.autonomousMode = autonomousMode;
		
	}
	public void run(){
		if (autonomousMode == 1)
			auto1.run();
	}
}
