package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

//Do no harm autonomous
public class Auto_5 extends Autonomous{
	 public Auto_5 (HDrive drive, PickerUpper pickerUpper, Vision vision){
		 super (drive, pickerUpper, vision);
	 }
	
	public void run() {
		switch(presentState){
		case INIT_CASE:
		{
			delay();
			nextState = State.END_CASE;
		    break;
		}
		case END_CASE:
		{
		   nextState = State.END_CASE;
			break;
		}
		default:
		{
			defaultState();
			nextState = State.END_CASE;
			break;
		}
		}
		printState();
		presentState = nextState;
		
		
	}

}
