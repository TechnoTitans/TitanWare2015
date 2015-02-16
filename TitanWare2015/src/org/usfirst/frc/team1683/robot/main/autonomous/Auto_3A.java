package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_3A extends Autonomous{
	public Auto_3A(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
		// TODO Auto-generated constructor stub
	}
	
	public void run(){
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
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextState = State.START_DRIVE_SIDEWAYS;
			break;
		}
		case START_DRIVE_SIDEWAYS:
		{
			driveTimer.start();
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			double sideSpeed = sideDistance/HWR.MEDIUM_SPEED;
			if (driveTimer.get()<sideSpeed)
			{
				hDrive.set(sideSpeed);
				nextState = State.DRIVE_SIDEWAYS;
			}
			else
			{
				hDrive.stop();
				driveTimer.stop();
				nextState = State.CENTER_TOTE;
			}
		}
		case CENTER_TOTE:
		{
//			nextState = centerTote(State.DRIVE_FORWARD);
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			hDrive.goForward(driveDistance);
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
