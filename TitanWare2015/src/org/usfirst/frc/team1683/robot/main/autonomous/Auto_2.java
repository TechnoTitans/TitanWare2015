package org.usfirst.frc.team1683.robot.main.autonomous;

public class Auto_2 extends Autonomous{
	//Pushes all three totes into the Auto Zone
	public static void run(){
		switch(presentState){
		case INIT_CASE:
		{
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			driveCount++;
			driveTrain.goStraight(driveDistance);
			if (driveCount<3){
				nextState = State.DRIVE_BACKWARD;
				break;
			}
			else{
				nextState = State.END_CASE;
				break;
			}
		}
		case DRIVE_BACKWARD:
		{
			driveTrain.goStraight(-driveDistance);
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			driveTrain.goSideways(sideDistance);			
			nextState = State.CENTER_TOTE;
			break;
		}
		case CENTER_TOTE:
		{
			centerTote();
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case END_CASE:
		{
			nextState = State.END_CASE;
			break;
		}
		}
		printState();
		presentState = nextState;
	}
}
