package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_1 extends Autonomous{
	public Auto_1(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
	}

	/**
	 * Pushes tote forward
	 */
	public void run(){
		switch(presentState){
		case INIT_CASE:
		{
			timer.start();
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			System.out.println("driveDistance = " + driveDistance);
//			hDrive.goForward(driveDistance);
			hDrive.goForward(143);
			nextState = State.END_CASE;
			break;
		}
		case END_CASE:
		{
//			hDrive.stop();			
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
