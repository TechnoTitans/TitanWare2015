package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_7 extends Autonomous {
	public Auto_7 (HDrive drive, PickerUpper pickerUpper, Vision vision){
		super(drive, pickerUpper, vision);
	}

	/**
	 * one tote coop
	 */
	public void run(){
		switch(presentState)
		{
		case INIT_CASE:
		{
			delay();
			timer.start();
			nextState = State.LIFT_TOTE;
			break;
		}
		case LIFT_TOTE:
		{
			pickerUpper.liftFirstTote();
			waitForThread(pickerUpper.getCurrentThread());
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			hDrive.resetTankEncoders();
			hDrive.goForward(coopDistance);
			waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());
			nextState = State.DROP_TOTE;
			break;
		}
		case DROP_TOTE:
		{
			pickerUpper.beltEncoder.reset();
			pickerUpper.getMotorGroup().moveDistanceInches(HWR.DROP_BARREL_HEIGHT);
			waitForThread(pickerUpper.getMotorGroup().getCurrentThread());
			nextState = State.DRIVE_BACKWARD;
			break;
		}
		case DRIVE_BACKWARD:
		{
			hDrive.goForward(-backToAutoDistance);
			waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());
			nextState = State.END_CASE;
			break;
		}
		case END_CASE:
		{
			hDrive.stop();
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
