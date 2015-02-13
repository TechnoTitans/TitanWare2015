package org.usfirst.frc.team1683.robot.main.autonomous;

public class Auto_3 extends Autonomous{
	/**
	 * Lifts the barrel, and uses the barrel to push the tote into the Auto Zone to get the barrel, tote, and robot in the
	 * @author komals
	 */
	public static void run(){
		switch(presentState){
		case INIT_CASE:
		{
			timer.start();
			visionTimer.start();
			nextState = State.LIFT_BARREL;
			break;
		}
		case LIFT_BARREL:
		{
			pickerUpper.liftBarrel();
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			driveTrain.goSideways(sideDistance);
			nextState = State.CENTER_TOTE;
			visionTimer.reset();
			break;
		}
		case CENTER_TOTE:
		{
			nextState = centerTote(State.DRIVE_FORWARD);
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
		default:
		{
			defaultState();
			break;
		}
		}
		printState();
		presentState = nextState;

	}

}
