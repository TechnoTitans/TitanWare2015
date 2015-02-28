package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.HWR;

import edu.wpi.first.wpilibj.Preferences;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;
import org.usfirst.frc.team1683.robot.pneumatics.OldCompressor;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class AirStateMachine {
	AirSystem air;
	Joystick stick;
	int buttonAssignment;
	int stickAssignment;
	double valveDelay;
	Timer timer;
	int stickNum;
	Preferences preferences;
	
	//State Machine Variables
	static final int INIT_CASE				 = 0;
	static final int WAIT_FOR_BUTTON		 = 1;
	static final int OPEN_VALVE				 = 2;
	static final int DELAY					 = 3;
	static final int CLOSE_VALVE			 = 4;
	int presentState = INIT_CASE;
	int nextState;
	
	public AirStateMachine(int[] solenoids, int button, PressureSensor pressure) {
		air = new AirSystem(solenoids, pressure);
		stick = new Joystick(HWR.AUX_JOYSTICK);
		buttonAssignment = button;
		air.retract();
		timer = new Timer();
		//valveDelay = DriverStation.getDouble("delay");
		valveDelay = 0.075;
		this.stickNum = 0;
		
		//preferences.putDouble("delay", 0.075);
	}
	
	public AirStateMachine(int[] solenoids, int stickNum, int button, PressureSensor pressure) {
		air = new AirSystem(solenoids, pressure);
		stick = new Joystick(stickNum);
		buttonAssignment = button;
		air.retract();
		timer = new Timer();
		//valveDelay = DriverStation.getDouble("delay");
		valveDelay = 0.075;
		this.stickNum = stickNum;
		
		//DriverStation.prefDouble("delay", 0.075);
	}
	
	public void test(){
		switch(presentState){
		case INIT_CASE:
		{
			timer.start();
			nextState = WAIT_FOR_BUTTON;
			break;
    	}
		case WAIT_FOR_BUTTON:
		{
			if (DriverStation.antiBounce(stickNum, buttonAssignment))
			//if (stick.getRawButton(buttonAssignment) == true)
				nextState = OPEN_VALVE;
			else
				nextState = WAIT_FOR_BUTTON;
			break;
		}
		case OPEN_VALVE:
		{
			System.out.println(valveDelay);
			air.extend();
			timer.reset();
			nextState = DELAY;
			break;
		}
		case DELAY:
		{
			if (timer.get()>=valveDelay)
				nextState = CLOSE_VALVE;
			else
				nextState = DELAY;
			break;
		}
		case CLOSE_VALVE:
		{
			air.retract();
			nextState = WAIT_FOR_BUTTON;
			break;
		}
		default:
		{
			nextState = INIT_CASE;
			break;
		}
		}
		presentState = nextState;
	}
}
