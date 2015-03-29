package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_10 extends Autonomous{
	public Auto_10(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
	}

	/**
	 * Lifts the barrel, and uses the barrel to push the tote into the Auto Zone to get the barrel, tote, and robot in the
	 */
	public void run(){
		switch(presentState){
		case INIT_CASE:
		{
			delay();
			timer.start();
			visionTimer.start();
			nextState = State.DRIVE_BACKWARD;
			break;
		}
		case DRIVE_BACKWARD:
		{
			hDrive.resetTankEncoders();
			hDrive.goForward(-coopDistance);
			waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());
			nextState = State.TILT_BACK;
			break;
		}
		case TILT_BACK:
		{
			pickerUpper.getTilter().tiltBackward(fullTiltTime);
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			hDrive.resetTankEncoders();
			hDrive.goForward(coopDistance-driveDistance);
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
