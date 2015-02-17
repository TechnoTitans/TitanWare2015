package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.main.autonomous.Autonomous.State;
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
			nextState = State.START_LIFT_BARREL;
			break;
		}
		case START_LIFT_BARREL:
		{
			pickerUpper.liftBarrel();
			liftTimer.start();
			nextState = State.LIFT_BARREL;
			break;
		}
//		case LIFT_BARREL:
//		{
//			pickerUpper.liftBarrel();
//			try {
//				this.wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			nextState = State.START_DRIVE_SIDEWAYS;
//			break;
//		}
		case LIFT_BARREL:
		{
//			if (liftTimer.get()<liftTime)
			if (liftTimer.get()<1.5){
				nextState = State.LIFT_BARREL;
			}
			else{
				nextState = State.START_DRIVE_SIDEWAYS;
				liftTimer.stop();
				liftTimer.reset();
			}
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
			double hSpeed;
			sideDistance = 24;
			if (sideDistance>0){
				hSpeed = HWR.MEDIUM_SPEED;
//				hSpeed = sideSpeed;
			}
			else{
				hSpeed = -HWR.MEDIUM_SPEED;
//				hSpeed = -sideSpeed;
			}
			if (driveTimer.get()<sideTime)
			{
				hDrive.set(hSpeed);
				nextState = State.DRIVE_SIDEWAYS;
			}
			else
			{
				hDrive.stopSide();
				driveTimer.stop();
				driveTimer.reset();
				visionTimer.reset();
				nextState = State.CENTER_TOTE;
			}
			break;
		}
		case CENTER_TOTE:
		{
//			nextState = centerTote(State.DRIVE_FORWARD);
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
			if (driveDistance>0){
				speed = HWR.MEDIUM_SPEED;
//				speed = driveSpeed;
			}
			else{
				speed = -HWR.MEDIUM_SPEED;
//				speed = -driveSpeed;
			}
			if (driveTimer.get()<driveTime)
			{
				hDrive.setTankDrive(speed);
				nextState = State.DRIVE_FORWARD;
			}
			else
			{
				hDrive.stop();
				driveTimer.stop();
				driveTimer.reset();
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
