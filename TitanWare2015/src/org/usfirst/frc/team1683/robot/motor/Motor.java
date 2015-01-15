package org.usfirst.frc.team1683.robot.motor;

import edu.wpi.first.wpilibj.Talon;

public class Motor extends Talon {
	
	/*
	 * Constructor
	 * @param channel port the Talon is plugged into
	*/
	public Motor(int channel){
		super(channel);
	}
}
