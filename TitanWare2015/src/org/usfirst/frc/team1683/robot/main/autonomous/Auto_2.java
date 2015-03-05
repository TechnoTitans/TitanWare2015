package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_2 extends Autonomous{
	public Auto_2(HDrive drive, PickerUpper pickerUpper, Vision vision) {
		super(drive, pickerUpper, vision);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @author Seung-Seok Lee
	 * Pushes all three totes into the Auto Zone
	 */
	public void run(){
		switch(presentState){
		case INIT_CASE:
		{
			timer.start();
			visionTimer.start();
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			driveCount++;
			hDrive.goForward(driveDistance);
			if (driveCount<3){
				nextState = State.DRIVE_BACKWARD;
				break;
			}
			else{
				nextState = State.END_CASE;
				break;
			}
		}
		case DRIVE_BACKWARD:
		{
			hDrive.goForward(-driveDistance);
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			hDrive.moveSideways(sideDistance);			
			nextState = State.DRIVE_FORWARD;
			visionTimer.reset();
			break;
		}
		case END_CASE:
		{
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
