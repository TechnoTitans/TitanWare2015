package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.BuiltInAccel;
import org.usfirst.frc.team1683.robot.sensors.BuiltInAccel.TiltStatus;

/**This class contains tests for the BuiltInAccel class
 * @author Sreyas Mirthipati
 */
public class BuiltInAccelTester implements Tester {

	BuiltInAccel accel;
	double angle;
	
	public BuiltInAccelTester() {
		accel = new BuiltInAccel();
	}
	@Override
	public void test() {
		String status;
		DriverStation.sendData("Raw", accel.getRaw());
		DriverStation.sendData("X", accel.getX());
		DriverStation.sendData("Y", accel.getY());
		DriverStation.sendData("Z", accel.getZ());
		DriverStation.sendData("YZ Angle", accel.getYZAngle());
		DriverStation.sendData("XZ Angle", accel.getXZAngle());
		if (accel.getStatus().equals(TiltStatus.FLAT))
			status = "FLAT";
		else if(accel.getStatus().equals(TiltStatus.CLIMBING))
			status = "CLIMBING";
		else if(accel.getStatus().equals(TiltStatus.DESCENDING))
			status = "DESCENDING";
		else if(accel.getStatus().equals(TiltStatus.TIPPING))
			status = "TIPPING";
		else if(accel.getStatus().equals(TiltStatus.UNDETERMINED))
			status = "UNDETERMINED";
		else
			status = "ERROR";
		DriverStation.sendData("Tilt Status", status);
			
	}

}
