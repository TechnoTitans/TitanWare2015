package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;

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
	
	//State Machine Variables
	static final int INIT_CASE				 = 0;
	static final int WAIT_FOR_BUTTON		 = 1;
	static final int OPEN_VALVE				 = 2;
	static final int DELAY					 = 3;
	static final int CLOSE_VALVE			 = 4;
	int presentState = INIT_CASE;
	int nextState;
	
	public AirStateMachine(int[] solenoids, int button) {
		air = new AirSystem(new Compressor(),solenoids);
		stick = new Joystick(HWR.AUX_JOYSTICK);
		buttonAssignment = button;
		air.retract();
		timer = new Timer();
		valveDelay = DriverStation.getDouble("delay");
		this.stickNum = 0;
		
		DriverStation.prefDouble("delay", .020);
	}
	
	public AirStateMachine(int[] solenoids, int stickNum, int button) {
		air = new AirSystem(new Compressor(),solenoids);
		stick = new Joystick(stickNum);
		buttonAssignment = button;
		air.retract();
		timer = new Timer();
		valveDelay = DriverStation.getDouble("delay");
		this.stickNum = stickNum;
		
		DriverStation.prefDouble("delay", .020);
	}
	
	public void test(){
		switch(presentState){
		case INIT_CASE:
		{
			nextState = WAIT_FOR_BUTTON;
			break;
    	}
		case WAIT_FOR_BUTTON:
		{
			timer.reset();
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
			timer.start();
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
			timer.stop();
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
