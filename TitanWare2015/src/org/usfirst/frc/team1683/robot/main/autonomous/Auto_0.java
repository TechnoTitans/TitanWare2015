package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;

public class Auto_0 extends Autonomous{

	public Auto_0(HDrive drive, PickerUpper pickerUpper) {
		super(drive, pickerUpper);
	}

	private static boolean error = true; 
	
	public void run (){
		
		switch(presentState){
		case INIT_CASE:
		{
			nextState = State.PRINT_ERROR;
			break;
		}
		case PRINT_ERROR:
		{
			if(error){
				System.out.println("Not an Autonomous");
				error = false;
			}
			nextState = State.RUN_OTHER_AUTO;
			break;
		}
		case RUN_OTHER_AUTO:
		{
			autonomous = new Auto_1(hDrive, pickerUpper);
			nextState = State.RUN_OTHER_AUTO;
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
