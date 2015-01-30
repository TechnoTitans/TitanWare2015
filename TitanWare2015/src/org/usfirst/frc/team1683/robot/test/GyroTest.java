package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.Gyro;

public class GyroTest implements Tester{
    Gyro gyro;
    public GyroTest(Gyro gyro){
    	this.gyro = gyro;
    }
	@Override
	public void test() {
		// TODO Auto-generated method stub
		DriverStation.sendData("Current Angle from Gyro", gyro.getAngle());
	}
	

}
