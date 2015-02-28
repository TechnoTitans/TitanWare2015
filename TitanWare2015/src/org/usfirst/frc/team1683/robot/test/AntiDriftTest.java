package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;

public class AntiDriftTest implements Tester{

	HDrive drive;

	public AntiDriftTest(HDrive drive){
		this.drive = drive;
	}

	@Override
	public void test() {
		if (DriverStation.antiBounce(HWR.LEFT_JOYSTICK, 10)){
			drive.goForward(200);
		}else if(DriverStation.antiBounce(HWR.LEFT_JOYSTICK, 6)){
			drive.moveSideways(50);
		}		
	}



}
