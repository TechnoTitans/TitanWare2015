package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.BuiltInAccel;

public class BuiltInAccelTester implements Tester {

	BuiltInAccel accel;
	double angle;
	
	public BuiltInAccelTester() {
		accel = new BuiltInAccel();
	}
	@Override
	public void test() {
		DriverStation.sendData("Raw", accel.getRaw());
		DriverStation.sendData("X", accel.getX());
		DriverStation.sendData("Y", accel.getY());
		DriverStation.sendData("Z", accel.getZ());
		DriverStation.sendData("YZ Angle", accel.getYZAngle());
		DriverStation.sendData("XZ Angle", accel.getXZAngle());
	}

}
