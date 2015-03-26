package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.main.autonomous.Autonomous.State;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_2D extends Autonomous{
	public Auto_2D(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Seung-Seok Lee
	 * Lifts recycling bin and drives forward
	 */
	public void run(){
		switch(presentState){
		case INIT_CASE:
		{
			delay();
			timer.start();
			visionTimer.start();
			nextState = State.LIFT_BARREL;
			break;
		}
		case LIFT_BARREL:
		{
			pickerUpper.beltEncoder.reset();
			pickerUpper.liftBarrel();
			waitForThread(pickerUpper.getCurrentThread());
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			hDrive.resetTankEncoders();
			hDrive.goForward(driveDistance);
			waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());
			nextState = State.DROP_TOTE;
			break;
		}
		case DROP_TOTE:
		{
			pickerUpper.drop();
			waitForThread(pickerUpper.getCurrentThread());
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
