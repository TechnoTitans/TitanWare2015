package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.main.autonomous.Autonomous.State;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_9 extends Autonomous {

	public Auto_9 (HDrive drive, PickerUpper pickerUpper, Vision vision){
		super(drive, pickerUpper, vision);
	}

	public void run(){
		boolean forward = false;
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
			if (forward == false){			
				hDrive.goForward(driveDistance);
				waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());			
				nextState = State.DRIVE_SIDEWAYS;
			}
			else{
				hDrive.goForward(coopDistance - driveDistance);
				waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());
				nextState = State.END_CASE;
			}
			break;

		}
		case DRIVE_SIDEWAYS:
		{
			forward = true;
			hDrive.resetHEncoders();
			hDrive.moveSideways(sideDistance);
			waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());
			nextState = State.DRIVE_FORWARD;
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
