package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.main.autonomous.Autonomous.State;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_6 extends Autonomous{
	public Auto_6(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
	}
	public void run(){
		switch(presentState){
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
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			hDrive.resetHEncoders();
			hDrive.moveSideways(sideDistance);
			waitForThread(hDrive.getBackHMotor().getCurrentThread(),
					hDrive.getFrontHMotor().getCurrentThread());
			hDrive.liftWheels();
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			hDrive.resetTankEncoders();
			hDrive.goForward(driveDistance);
			waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());
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
