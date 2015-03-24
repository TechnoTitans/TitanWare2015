package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.main.DriverStation;

import edu.wpi.first.wpilibj.RobotBase;

public class TiltMover implements Runnable{
	RobotBase base;
	TiltScrew tilter;

	public TiltMover(TiltScrew tilter, RobotBase base) {
		this.tilter = tilter;
		this.base = base;
	}

	private void stateChecker() {
		
		if(tilter.getTiltMotor().getMotor(0).isFwdLimitSwitchClosed())
			tilter.setState(PickupState.VERTICAL);
		else if(tilter.getTiltMotor().getMotor(0).isRevLimitSwitchClosed())
			tilter.setState(PickupState.ANGLED);
		else if((!tilter.getTiltMotor().getMotor(0).isRevLimitSwitchClosed()&&
				!tilter.getTiltMotor().getMotor(0).isFwdLimitSwitchClosed())&&
				(tilter.getTiltMotor().getMotor(0).getSetPoint() < 0))
			tilter.setState(PickupState.MOVING_FWD);
		else if((!tilter.getTiltMotor().getMotor(0).isRevLimitSwitchClosed()&&
				!tilter.getTiltMotor().getMotor(0).isFwdLimitSwitchClosed())&&
				(tilter.getTiltMotor().getMotor(0).getSetPoint() > 0))
			tilter.setState(PickupState.MOVING_REV);
		else if (tilter.getTiltMotor().getMotor(0).getSetPoint() == 0)
			tilter.setState(PickupState.FROZEN);
		
		DriverStation.sendData("PickerUpper Status", tilter.getState().toString());
		DriverStation.sendData("Forward LimitSwitch: ", tilter.getTiltMotor().getMotor(0).isFwdLimitSwitchClosed());
		DriverStation.sendData("Back LimitSwitch: ", tilter.getTiltMotor().getMotor(0).isRevLimitSwitchClosed());
	}
	
	public void start() {
		Thread thread = new Thread(this, "Pickerupper Tilt Manager");
		thread.setPriority(Thread.NORM_PRIORITY);
		thread.start();
	}

	@Override
	public void run() {
		while(true) {
			stateChecker();
			while(base.isOperatorControl()) {
				if(DriverStation.auxStick.getRawButton(HWR.FORWARD_TILT)) {
					tilter.getTiltMotor().set(-TiltScrew.speed);
				}
				else if(DriverStation.auxStick.getRawButton(HWR.BACKWARD_TILT)) {
					tilter.getTiltMotor().set(TiltScrew.speed);
				}
				else {
					tilter.getTiltMotor().stop();
				}
				stateChecker();
			}
			while(base.isAutonomous()) {
				System.out.println("AUTO MODE YAYAYAYYAYAY!!");
				stateChecker();
			}

		}

	}
}
