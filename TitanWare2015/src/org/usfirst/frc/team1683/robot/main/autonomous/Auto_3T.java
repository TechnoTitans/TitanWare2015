package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

import edu.wpi.first.wpilibj.Timer;

public class Auto_3T extends Autonomous{
	public Auto_3T(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Lifts the barrel, and uses the barrel to push the tote into the Auto Zone to get the barrel, tote, and robot in the
	 */
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
				pickerUpper.getCurrentThread().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			hDrive.resetHEncoders();
			hDrive.moveSideways(sideDistance);
			try {
				hDrive.getBackHMotor().getCurrentThread().join();
				hDrive.getFrontHMotor().getCurrentThread().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			hDrive.liftWheels();
			nextState = State.CENTER_TOTE;
			Timer.delay(0.5);
			break;
		}
		case CENTER_TOTE:
		{
//			nextState = centerTote(State.DRIVE_FORWARD);
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			hDrive.resetTankEncoders();
			hDrive.goForward(driveDistance);
			try {
				hDrive.left.getCurrentThread().join();
				hDrive.right.getCurrentThread().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextState = State.END_CASE;
			
			break;
		}
		case DROP_TOTE:
		{
			pickerUpper.drop();
			try {
				pickerUpper.getCurrentThread().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextState = State.DRIVE_BACKWARD;
			break;
		}
		case DRIVE_BACKWARD:
		{
			hDrive.goForward(-backDistance);
			try {
				hDrive.left.getCurrentThread().join();
				hDrive.right.getCurrentThread().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextState = State.END_CASE;
			break;
		}
		case END_CASE:
		{
			hDrive.stop(false);
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
