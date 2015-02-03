package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.drivetrain.Talon;
import org.usfirst.frc.team1683.robot.main.DriverStation;

public class TalonTest implements Tester{
	
	Talon talon;
	double speed;
	public TalonTest(Talon t){
		talon=t;
	}
	
	@Override
	public void test() {
		speed = DriverStation.auxStick.getRawAxis(DriverStation.YAxis);
		talon.set(speed);
	}

}
