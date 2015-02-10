package org.usfirst.frc.team1683.robot.main.autonomous;

public class Auto_2 extends Autonomous{

	public static void run(){
		switch(presentState){
		case INIT_CASE:
		{
			nextState = DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			driveCount++;
			driveTrain.goStraight(driveDistance);
			if (driveCount<3){
				nextState = DRIVE_BACKWARD;
				break;
			}
			else{
				nextState = END_CASE;
				break;
			}
		}
		case DRIVE_BACKWARD:
		{
			driveTrain.goStraight(-driveDistance);
			nextState = DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			driveTrain.goSideways(sideDistance);			
			nextState = CENTER_TOTE;
			break;
		}
		case CENTER_TOTE:
		{
			centerTote();
			nextState = DRIVE_FORWARD;
			break;
		}
		case END_CASE:
		{
			nextState = END_CASE;
			break;
		}
		}
		printState();
		presentState = nextState;
	}
}
