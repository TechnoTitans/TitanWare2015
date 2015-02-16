package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
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
			nextState = State.START_DRIVE_FORWARD;
			break;
		}
		case START_DRIVE_FORWARD:
		{
			hDrive.leftEncoder.reset();
			hDrive.rightEncoder.reset();
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			double speed;
			if (driveDistance>0){
				speed = HWR.MEDIUM_SPEED;
			}
			else{
				speed = -HWR.MEDIUM_SPEED;
			}
			if (Math.abs(hDrive.leftEncoder.getDisplacement(hDrive.leftEncoder.getDistancePerPulse()))<Math.abs(driveDistance)&&
					Math.abs(hDrive.rightEncoder.getDisplacement(hDrive.rightEncoder.getDistancePerPulse()))<Math.abs(driveDistance))
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
