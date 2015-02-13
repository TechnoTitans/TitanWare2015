package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.main.autonomous.Autonomous.State;

public class Auto_4 extends Autonomous{
	/**
	 * Picks up all the totes and drives all of them into the Auto Zone and the robot into the Auto Zone
	 * @author Sneha
	 */
	public static void run(){
		switch(presentState)
		{
		case INIT_CASE:
		{
			pickerUpper.goToZero();
			nextState = State.ADJUST_FORWARD;
			break;
		}
		case ADJUST_FORWARD:
		{
			driveTrain.goStraight(adjustDistance);
			nextState = State.CENTER_TOTE;
			break;
		}
		case CENTER_TOTE:
		{
			nextState = centerTote(State.DROP_TOTE);
			break;
		}
		case DROP_TOTE:
		{
			pickerUpper.drop();
			if (liftCount<2)
				nextState = State.LIFT_TOTE;
			else
				nextState = State.DRIVE_FORWARD;
			break;
		}
		case LIFT_TOTE:
		{
			if (liftCount<1)
				pickerUpper.liftFirstTote();
			else
				pickerUpper.liftSecondTote();
			liftCount++;
			nextState = State.ADJUST_BACKWARD;
			break;
		}
		case ADJUST_BACKWARD:
		{
			driveTrain.goStraight(-adjustDistance);
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			driveTrain.goSideways(sideDistance);
			nextState = State.ADJUST_FORWARD;
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
