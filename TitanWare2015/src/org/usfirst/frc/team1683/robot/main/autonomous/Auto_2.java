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
			}
			else{
				nextState = END_CASE;
			}
		}
		case DRIVE_BACKWARD:
		{
			driveTrain.goStraight(-driveDistance);
			nextState = DRIVE_SIDEWAYS;
		}
		case DRIVE_SIDEWAYS:
		{
			driveTrain.goSideways(sideDistance);
			nextState = DRIVE_FORWARD;
		}
		case END_CASE:
		{
			nextState = END_CASE;
			break;
		}
		}
		presentState = nextState;
	}
}
