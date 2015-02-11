package org.usfirst.frc.team1683.robot.main.autonomous;

public class Auto_4 extends Autonomous{
	/**
	 * Picks up all the totes and drives all of them into the Auto Zone and the robot into the Auto Zone
	 * @author Sneha
	 */
	public static void run(){
		switch(presentState){
		case INIT_CASE:
		{
			nextState = State.LIFT_TOTE;
			break;
		}
		case LIFT_TOTE:
		{	
			pickerUpper.runAuto(liftDistance);
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case IS_TOTE_LIFTED:
		{	
			if (isToteLifted) {
				nextState = State.DRIVE_SIDEWAYS;
			}
			else if (isToteLiftedCount >= 3) {
				nextState = State.DRIVE_FORWARD;
			}
			else {
				nextState = State.LIFT_TOTE;
				isToteLiftedCount++;
			}
			
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			driveTrain.goSideways(sideDistance);
			nextState = State.ADJUST_TOTE;
			break;
		}
		case ADJUST_TOTE:
		{
			//how to adjust tote not quite clear, but it does need adjusting
			driveTrain.goStraight(backDistance);
			nextState = State.DROP_TOTE;
			break;
		}
		case DROP_TOTE:
		{
			driveTrain.goStraight(adjustDistance);
			pickerUpper.runAuto(dropDistance);
			liftCount++;
			if(liftCount <= 1) {
				nextState = State.LIFT_POSITION;
			}
			else {
				nextState = State.DRIVE_FORWARD;
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
			nextState = State.LIFT_TOTE; 
			break;
		}
		case DRIVE_FORWARD:
		{
			driveTrain.goStraight(driveDistance);
			nextState = State.END_CASE;
			break;
		}
		case END_CASE:
		{
			driveTrain.stop();
			nextState = State.END_CASE;
			break;
		}
		}
		printState();
		presentState = nextState; 
	}
}
