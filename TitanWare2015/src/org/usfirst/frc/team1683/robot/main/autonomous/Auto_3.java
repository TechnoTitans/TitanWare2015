package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.HWR;
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
	 */
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
//			nextState = State.DRIVE_SIDEWAYS;
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
			hDrive.resetHEncoders();
			nextState = State.DRIVE_SIDEWAYS;
			break;
		}
		case DRIVE_SIDEWAYS:
		{
			double hSpeed;
			if (sideDistance>0){
				hSpeed = HWR.MEDIUM_SPEED;
//				hSpeed = sideSpeed;
			}
			else{
				hSpeed = -HWR.MEDIUM_SPEED;
//				hSpeed = -sideSpeed;
			}
			if (Math.abs(leftEncoder.getDisplacement(leftEncoder.getDistancePerPulse()))<sideDistance&&
					Math.abs(rightEncoder.getDisplacement(rightEncoder.getDistancePerPulse()))<sideDistance)
			{
				hDrive.set(hSpeed);
				nextState = State.DRIVE_SIDEWAYS;
			}
			else
			{
				hDrive.stopSide();
				visionTimer.reset();
				nextState = State.CENTER_TOTE;
			}
//			hDrive.moveSideways(sideDistance);
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
			hDrive.resetTankEncoders();
			nextState = State.DRIVE_FORWARD;
			break;
		}
		case DRIVE_FORWARD:
		{
			double speed;
			if (driveDistance>0){
				speed = HWR.MEDIUM_SPEED;
//				speed = driveSpeed;
			}
			else{
				speed = -HWR.MEDIUM_SPEED;
//				speed = -driveSpeed;
			}
			if (Math.abs(leftEncoder.getDisplacement(leftEncoder.getDistancePerPulse()))<Math.abs(driveDistance)&&
					Math.abs(rightEncoder.getDisplacement(rightEncoder.getDistancePerPulse()))<Math.abs(driveDistance))
			{
				hDrive.setTankDrive(speed);
				nextState = State.DRIVE_FORWARD;
			}
			else
			{
				hDrive.stop();
				nextState = State.END_CASE;
			}
//			hDrive.goForward(driveDistance);
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
