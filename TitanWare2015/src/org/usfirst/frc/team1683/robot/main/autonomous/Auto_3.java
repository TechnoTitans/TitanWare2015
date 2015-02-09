package org.usfirst.frc.team1683.robot.main.autonomous;

public class Auto_3 extends Autonomous{
	/**
	 * @author komals
	 */
	public static void run(){
		switch(presentState){
		case INIT_CASE:
		{
			nextState = LIFT_BARREL;
			break;
		}
		case LIFT_BARREL:
		{
			pickerUpper.runAuto(liftDistance);
			nextState = DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			driveTrain.goSideways(sideDistance);
			nextState = DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			driveTrain.goStraight(driveDistance);
			nextState = END_CASE;
			break;
		}
		case END_CASE:
		{
			driveTrain.stop();
			nextState = END_CASE;
			break;
		}
		}
		printState();
		presentState = nextState;

	}

}
