package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_1T extends Autonomous implements Runnable {

	public Auto_1T(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
	}

	@Override
	public void run() {
		switch(presentState){
		case INIT_CASE:
		{
			timer.start();
			nextState = State.START_DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			hDrive.goForward(driveDistance);
			Thread leftThread = hDrive.left.getCurrentThread();
			Thread rightThread = hDrive.right.getCurrentThread();
			synchronized (leftThread){
				try{
					leftThread.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			synchronized (rightThread){
				try{
					rightThread.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
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
