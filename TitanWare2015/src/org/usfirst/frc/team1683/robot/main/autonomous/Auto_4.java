package org.usfirst.frc.team1683.robot.main.autonomous;

public class Auto_4 extends Autonomous{
	/**
	 * @author Sneha
	 */
	public static final int LIFT_TOTE				= 5;
	public static final int ALIGN_TOTE				= 6;
	public static final int DROP_TOTE				= 7;
	public static void run(){
		switch(presentState){
		case INIT_CASE:
		{
			nextState = LIFT_TOTE;
			break;
		}
		case LIFT_TOTE:
		{	
			//need to add method which returns hook to bottom
			pickerUpper.runAuto(liftDistance);
			nextState = DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			driveTrain.goSideways(sideDistance);
			liftCount++;
			if(liftCount == 1) {
				nextState = DROP_TOTE;
			}
			else {
				nextState = DRIVE_FORWARD;
			}
			break;
		}
		case ALIGN_TOTE:
		{
			driveTrain.goStraight(backDistance);
			nextState = LIFT_TOTE;
			break;
		}
		case DROP_TOTE:
		{
			driveTrain.goStraight(adjustDistance);
			pickerUpper.runAuto(dropDistance);
			nextState = ALIGN_TOTE;
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
