package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.drivetrain.TalonSRX;
import org.usfirst.frc.team1683.robot.main.DriverStation;

public class TalonSRXTest implements Tester{
	TalonSRX talon;
	double speed;
public TalonSRXTest(TalonSRX t){
	talon=t;
}
@Override
public void test() {
	speed = DriverStation.auxStick.getRawAxis(DriverStation.YAxis);
	talon.set(speed);
}
}
