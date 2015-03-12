package org.usfirst.frc.team1683.robot.main.autonomous;


import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class Auto_8 extends Autonomous{

		public Auto_8(HDrive drive, PickerUpper pickerUpper, Vision vision) {
			super(drive, pickerUpper, vision);
		}
	// Two Tote Coop autonomous

		@Override
		public void run() {
			switch(presentState){
			case INIT_CASE:
			{
				timer.start();
				nextState = State.LIFT_TOTE;
				break;
			}case LIFT_TOTE:
			{
				if (liftCount<1){
					pickerUpper.liftFirstTote();
					nextState = State.DRIVE_SIDEWAYS;
				}
				else{
					pickerUpper.liftSecondTote();
					nextState = State.DRIVE_FORWARD;
				}
				waitForThread(pickerUpper.getCurrentThread());
				liftCount++;
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
				if (liftCount<1) {
				hDrive.goForward(coopDistance - stepDistance);
				} 
				else {
					hDrive.goForward(stepDistance);
				}
				waitForThread(hDrive.left.getCurrentThread(), hDrive.right.getCurrentThread());
				nextState = State.DROP_TOTE;
				break;
			}
			case DROP_TOTE:
			{
				pickerUpper.drop();
				waitForThread(pickerUpper.getCurrentThread());
			    adjustTote();
				nextState = State.LIFT_TOTE;
				break;
			}
			case END_CASE: 
			{
				
			}
		}
			printState();
			presentState = nextState;
		}
	}


