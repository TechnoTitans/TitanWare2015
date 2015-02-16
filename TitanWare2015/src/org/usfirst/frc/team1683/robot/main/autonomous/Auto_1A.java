package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_1A extends Autonomous{
	public Auto_1A(HDrive drive, PickerUpper pickerUpper, Vision vision) {
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
			nextState = State.START_DRIVE_FORWARD;
			break;
		}
		case START_DRIVE_FORWARD:
		{
			driveTimer.start();
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			double speed;
			driveDistance = 143;
			if (driveDistance > 0){
				speed = HWR.MEDIUM_SPEED;
			}
			else{
				speed = -HWR.MEDIUM_SPEED;
			}
//			System.out.println("driveDistance = " + driveDistance);
			if (driveTimer.get()<driveTime)
			{
				hDrive.setTankDrive(speed);
				nextState = State.DRIVE_FORWARD;
			}
			else
			{
				hDrive.stop();
				nextState = State.END_CASE;
			}
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
