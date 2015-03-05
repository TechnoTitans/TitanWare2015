package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.RobotBase;

public class PickerUpperPneumatics implements Runnable{
	private static int numberOfSwitchesPerSecond = 20; //50ms per Switch
	private static double freezeDegrees = 22; //50ms per Switch

	boolean isForward;
	AirSystem frontAirSystem;
	AirSystem backAirSystem;
	Gyro gyro;
	RobotBase base;
	Thread freezeThread;

	public PickerUpperPneumatics(RobotBase base, Gyro gyro ,int[] pistons, PressureSensor pressure) { //front piston, back Piston
		frontAirSystem = new AirSystem(new int[]{pistons[0]}, pressure);
		backAirSystem = new AirSystem(new int[]{pistons[1]}, pressure);
		isForward = false;
		this.gyro = gyro;
		this.base = base;
		freezeThread = null;
		if (gyro != null){
			gyro.reset();
		}
	}

	public void changeState(){
		if (frontAirSystem.isExtended()){
			frontAirSystem.retract();
			backAirSystem.extend();
		}else{
			frontAirSystem.extend();
			backAirSystem.retract();
		}
	}

	public void upright(){
		nullifyCurrentThread();
		frontAirSystem.retract();
		backAirSystem.retract();
	}

	public void angle(){
		nullifyCurrentThread();
		frontAirSystem.extend();
		backAirSystem.extend();
	}

	public void freeze(){
		if (gyro!= null){
			freezeThread = new Thread(this, "Freeze");
			freezeThread.setPriority(Thread.NORM_PRIORITY);
			freezeThread.start();
		}else{
			frontAirSystem.extend();
			backAirSystem.retract();
		}
	}

	private void nullifyCurrentThread(){
		if (freezeThread!= null){
			//			freezeThread.stop();
			//			freezeThread.destroy();
			freezeThread = null;
		}
	}

	@Override
	public void run() {
		if (gyro!= null){
			double upperBand = freezeDegrees + (freezeDegrees*.03);
			double lowerBand= freezeDegrees - (freezeDegrees*.03);
			while (true){
				while (base.isEnabled()){
					try {
						Thread.sleep(1000/numberOfSwitchesPerSecond);
					} catch (InterruptedException e) {}
					double angle = gyro.getAngle();
					if (angle<=lowerBand){
						angle();
					}else if (angle>= upperBand){
						upright();
					}else{
						frontAirSystem.extend();
						backAirSystem.retract();
					}
				}
			}
		}
	}
}
