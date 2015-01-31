package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;
import org.usfirst.frc.team1683.robot.main.Autonomous;

public class Auto_1 {
	TankDrive tankDrive;
	HDrive hDrive;
	double distance;
	public Auto_1(){
		
	}
	
	public void run(){
		tankDrive.goStraight(distance);
	}
}
