package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

import edu.wpi.first.wpilibj.Timer;

public class Auto_3A extends Autonomous{
	public Auto_3A(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
	}
	
	public void run(){
		switch(presentState){
		case INIT_CASE:
		{
			delay();
			timer.start();
			visionTimer.start();
			nextState = State.START_LIFT_BARREL;
			break;
		}
		case START_LIFT_BARREL:
		{
			pickerUpper.beltEncoder.reset();
			pickerUpper.liftBarrel();
			liftTimer.start();
			nextState = State.LIFT_BARREL;
			break;
		}
		case LIFT_BARREL:
		{
			pickerUpper.liftBarrel();
			waitForThread(pickerUpper.getCurrentThread());
			nextState = State.START_DRIVE_SIDEWAYS;
			break;
		}
		case START_DRIVE_SIDEWAYS:
		{
			driveTimer.start();
			hDrive.deployWheels();
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			double hSpeed = setSpeed(sideSpeed, sideDistance);
			if (driveTimer.get()<sideTime)
			{
//				hDrive.antiDrift(hSpeed, hDrive.getFrontHMotor(), hDrive.getBackHMotor());
				hDrive.set(hSpeed);
				nextState = State.DRIVE_SIDEWAYS;
			}
			else
			{
				hDrive.stopSide();
				hDrive.liftWheels();
				Timer.delay(0.5);
				driveTimer.stop();
				driveTimer.reset();
				visionTimer.reset();
				nextState = State.START_DRIVE_FORWARD;
			}
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
			double speed = setSpeed(driveSpeed, driveDistance);
			if (driveTimer.get()<driveTime)
			{
				hDrive.setTankDrive(speed);
//				hDrive.antiDrift(speed);
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
