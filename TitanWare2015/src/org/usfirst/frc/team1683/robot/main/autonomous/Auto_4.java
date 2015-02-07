package org.usfirst.frc.team1683.robot.main.autonomous;

public class Auto_4 extends Autonomous{
	/**
	 * @author Sneha
	 */
	
	public static void run(){
		switch(presentState){
		case INIT_CASE:
		{
			nextState = LIFT_TOTE;
			break;
		}
		case LIFT_TOTE:
		{	
			pickerUpper.runAuto(liftDistance);
			nextState = DRIVE_SIDEWAYS;
			break;
		}
		case IS_TOTE_LIFTED:
		{	
			if (isToteLifted) {
				nextState = DRIVE_SIDEWAYS;
			}
			else if (isToteLiftedCount >= 3) {
				nextState = DRIVE_FORWARD;
			}
			else {
				nextState = LIFT_TOTE;
				isToteLiftedCount++;
			}
			
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			driveTrain.goSideways(sideDistance);
			nextState = ADJUST_TOTE;
			break;
		}
		case ADJUST_TOTE:
		{
			//how to adjust tote not quite clear, but it does need adjusting
			driveTrain.goStraight(backDistance);
			nextState = DROP_TOTE;
			break;
		}
		case DROP_TOTE:
		{
			driveTrain.goStraight(adjustDistance);
			pickerUpper.runAuto(dropDistance);
			liftCount++;
			if(liftCount <= 1) {
				nextState = LIFT_POSITION;
			}
			else {
				nextState = DRIVE_FORWARD;
			}
			break;
		}
		case LIFT_POSITION:
		{	
			//variables need to be added here. 
			pickerUpper.runAuto(-6.0);
			driveTrain.goStraight(-3.0);
			pickerUpper.runAuto(-6.0);
			driveTrain.goStraight(3.0);
			nextState = LIFT_TOTE; 
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
		presentState = nextState; 
	}
}
