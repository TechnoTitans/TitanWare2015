package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.TalonSRX;


public class TiltScrew {
	TalonSRX tiltMotor;
	private static final double speed = HWR.MEDIUM_SPEED;

	public TiltScrew(int motorPort, boolean inverseDirection) {
		tiltMotor = new TalonSRX(motorPort, inverseDirection);
		tiltMotor.enableBrakeMode(true);
		tiltMotor.enableLimitSwitch(true, true);
	}

	/**
	 * Drives the motor forwards until front limit switch is closed
	 */
	public void tiltUpright() {
		while(!tiltMotor.isFwdLimitSwitchClosed()) {
			tiltMotor.set(speed);
		}
		tiltMotor.stop();
	}

	/**
	 * Drives the motor backwards until rear limit switch is closed
	 */
	public void tiltBackward() {
		while(!tiltMotor.isRevLimitSwitchClosed()) {
			tiltMotor.set(-speed);
		}
		tiltMotor.stop();
	}
	
	/**
	 * @return the tilt motor
	 */
	public TalonSRX getTiltMotor() {
		return tiltMotor;
	}
}
