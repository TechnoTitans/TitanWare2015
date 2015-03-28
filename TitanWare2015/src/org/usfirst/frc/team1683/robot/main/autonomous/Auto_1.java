package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_1 extends Autonomous implements Runnable {

	public Auto_1(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
	}

	@Override
	public void run() {
		switch(presentState){
		case INIT_CASE:
		{
			delay();
			timer.start();
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			hDrive.goForward(driveDistance);
			waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());
			nextState = State.END_CASE;
			break;
		}
		case END_CASE:
		{
			hDrive.stop();
			//destroyThreads(new Thread[] {hDrive.left.getCurrentThread(),hDrive.right.getCurrentThread()});
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
