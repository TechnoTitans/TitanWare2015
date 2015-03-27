package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

import edu.wpi.first.wpilibj.Timer;

public class Auto_3 extends Autonomous{
	public Auto_3(HDrive drive, PickerUpper pickerUpper, Vision vision) {
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
			nextState = State.LIFT_BARREL;
			break;
		}
		case LIFT_BARREL:
		{
			pickerUpper.beltEncoder.reset();
			pickerUpper.liftToClearBarrel();
			waitForThread(pickerUpper.getCurrentThread());
			pickerUpper.liftBarrel();
			nextState = State.TILT_BACK;
			break;
		}
		case TILT_BACK:
		{
			pickerUpper.getTilter().tiltBackward(tilterTime);
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			hDrive.resetHEncoders();
			hDrive.moveSideways(sideDistance, sideSpeed);
			waitForThread(hDrive.getBackHMotor().getCurrentThread(),
					hDrive.getFrontHMotor().getCurrentThread());
			hDrive.liftWheels();
			nextState = State.DRIVE_FORWARD;
			Timer.delay(0.5);
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
		case DROP:
		{
			pickerUpper.beltEncoder.reset();
			pickerUpper.getMotorGroup().moveDistanceInches(HWR.DROP_BARREL_HEIGHT);
			waitForThread(pickerUpper.getMotorGroup().getCurrentThread());
			nextState = State.DRIVE_BACKWARD;
			break;
		}
		case DRIVE_BACKWARD:
		{
			hDrive.goForward(-backDistance);
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
