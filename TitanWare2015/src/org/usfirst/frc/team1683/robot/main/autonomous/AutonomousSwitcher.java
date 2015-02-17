package org.usfirst.frc.team1683.robot.main.autonomous;

import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pickerupper.PickerUpper;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class AutonomousSwitcher {
	
	Autonomous autonomous;
	Auto_0 auto_0;

	public AutonomousSwitcher(HDrive hDrive, PickerUpper pickerUpper, Vision vision) {
		int autonomousMode = DriverStation.getInt("autonomousMode");
		if (autonomousMode == 1)
			autonomous = new Auto_1(hDrive, pickerUpper, vision);
		else if (autonomousMode == 2)
			autonomous = new Auto_2(hDrive, pickerUpper, vision);
		else if (autonomousMode == 3)
			autonomous = new Auto_3(hDrive, pickerUpper, vision);
		else if (autonomousMode == 4)
			autonomous = new Auto_4(hDrive, pickerUpper, vision);
		else if (autonomousMode == 11)
			autonomous = new Auto_1A(hDrive, pickerUpper, vision);
		else if (autonomousMode == 12)
			autonomous = new Auto_1T(hDrive, pickerUpper, vision);
		else if (autonomousMode == 13)
			autonomous = new Auto_3A(hDrive, pickerUpper, vision);
		else
			autonomous = new Auto_0(hDrive, pickerUpper, vision);
	}
	
	public void setAutonomous(Autonomous autonomous){
		this.autonomous = autonomous;
	}
	
	@SuppressWarnings("static-access")
	public void run(){
		autonomous.updatePreferences();
		autonomous.run();
	}
	
}
