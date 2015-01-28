package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.drivetrain.Motor;
import org.usfirst.frc.team1683.robot.drivetrain.TalonSRX;

public class TalonSRXTest implements Tester{
	TalonSRX talon;
	double speed;
public TalonSRXTest(TalonSRX t){
	talon=t;
	speed = 0.75;
}
@Override
public void test() {
	// TODO Auto-generated method stub
	talon.set(speed);
}
}
