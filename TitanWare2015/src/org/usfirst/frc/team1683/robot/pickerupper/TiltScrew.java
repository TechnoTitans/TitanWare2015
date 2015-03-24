package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.MotorGroup;


public class TiltScrew implements Runnable{
	private MotorGroup tiltMotor;
	private PickupState state;
	private PickupState targetState;
	protected static double speed = HWR.TILTSCREW_SPEED;
	Thread currentThread; 

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TiltScrew(int motorPort, Class motorType, boolean inverseDirection) {
		tiltMotor = new MotorGroup("TiltMotor", new int[]{motorPort}, motorType, inverseDirection);
		tiltMotor.enableBrakeMode(false);
		tiltMotor.enableLimitSwitch(true, true);
		targetState = null;
	}

	/**
	 * Drives the motor forwards until front limit switch is closed
	 */
	public void tiltUpright() {
		targetState = PickupState.VERTICAL;
		currentThread = new Thread(this, "Upright Picker Upper");
		currentThread.start();
	}

	/**
	 * Drives the motor backwards until rear limit switch is closed
	 */
	public void tiltBackward() {
		targetState = PickupState.ANGLED;
		currentThread = new Thread(this, "Backward Picker Upper");
		currentThread.start();
	}
	
	

	/**
	 * @return the tilt MotorGroup
	 */
	public MotorGroup getTiltMotor() {
		return tiltMotor;
	}
	
	public Thread getCurrentThread(){
		return currentThread;
	}
	
	public void setState(PickupState state) {
		this.state = state;
	}
	
	public PickupState getState() {
		return state;
	}

	@Override
	public void run() {
		while (targetState != state){
			if (targetState == PickupState.ANGLED){
				tiltMotor.set(speed);
			}else if (targetState == PickupState.VERTICAL){
				tiltMotor.set(-speed);
			}
		}
		tiltMotor.stop();
		currentThread.destroy();
		currentThread.notifyAll();
	}
	
}
