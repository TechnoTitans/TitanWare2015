package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.main.autonomous.Autonomous.State;

public class Auto_0 extends Autonomous{

	
	
	public static void run (){
		boolean error = true; 
		switch(presentState){
		case INIT_CASE:
		{
			nextState = State.PRINT_ERROR;
			break;
		}
		case PRINT_ERROR:
		{
			if(error = true){
				System.out.println("Not an Autonomous");
				error = false;
			}
			nextState = State.RUN_OTHER_AUTO;
			break;
		}
		case RUN_OTHER_AUTO:
		{
			Auto_1.run();
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
