package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_3 extends Autonomous{
	public Auto_3(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Lifts the barrel, and uses the barrel to push the tote into the Auto Zone to get the barrel, tote, and robot in the
	 * @author komals
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
			nextState = State.DRIVE_SIDEWAYS;
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			hDrive.moveSideways(sideDistance);
			nextState = State.CENTER_TOTE;
			visionTimer.reset();
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
