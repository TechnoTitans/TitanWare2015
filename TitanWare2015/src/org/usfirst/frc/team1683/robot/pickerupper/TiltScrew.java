package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.MotorGroup;


public class TiltScrew {
	private MotorGroup tiltMotor;
	private PickupState state;
	protected static double speed = HWR.TILTSCREW_SPEED;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TiltScrew(int motorPort, Class motorType, boolean inverseDirection) {
		tiltMotor = new MotorGroup("TiltMotor", new int[]{motorPort}, motorType, inverseDirection);
		tiltMotor.enableBrakeMode(true);
		tiltMotor.enableLimitSwitch(true, true);
	}

	/**
	 * Drives the motor forwards until front limit switch is closed
	 */
	public void tiltUpright() {
		if(!tiltMotor.getMotor(0).isFwdLimitSwitchClosed()) {
			tiltMotor.set(speed);
		}
		else {
			tiltMotor.stop();
		}
	}

	/**
	 * Drives the motor backwards until rear limit switch is closed
	 */
	public void tiltBackward() {
		if(!tiltMotor.getMotor(0).isRevLimitSwitchClosed()) {
			tiltMotor.set(-speed);
		}
		else {
			tiltMotor.stop();
		}
	}

	/**
	 * @return the tilt MotorGroup
	 */
	public MotorGroup getTiltMotor() {
		return tiltMotor;
	}
	
	public void setState(PickupState state) {
		this.state = state;
	}
	
	public PickupState getState() {
		return state;
	}
	
}
