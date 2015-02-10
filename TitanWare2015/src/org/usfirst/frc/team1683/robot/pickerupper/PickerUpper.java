package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.Motor;
import org.usfirst.frc.team1683.robot.drivetrain.MotorGroup;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;


public class PickerUpper {
	MotorGroup motors;
	AirSystem liftPistons;
	MotorGroup leftLiftMotor;
	MotorGroup rightLiftMotor;
	public Encoder beltEncoder;
	int liftButton;
	final double AUTO_LIFT_SPEED = 0.5;

	/**
	 * Constructor
	 * @param pickerUpperChannels
	 * @param motorType
	 * @param inverseDirection
	 */
	public PickerUpper(int[] pickerUpperChannels, Class motorType, boolean inverseDirection){
		this.motors = new MotorGroup(pickerUpperChannels, motorType, inverseDirection);
	}
	/**
	 * Constructor
	 * @param motorType
	 * @param leftInverseDirection - reverseDirection for left motor
	 * @param rightInverseDirection - reverseDirection for right motor
	 * @param liftSolenoids - ports for lift pistons
	 * @param leftMotor
	 * @param rightMotor
	 * @param beltChannelA
	 * @param beltChannelB
	 * @param reverseDirection - reverseDirection for encoder
	 * @param wdpp - wheel distance per pulse for lift encoder
	 */
	public PickerUpper(Class motorType, boolean leftInverseDirection, boolean rightInverseDirection,
			 int[] liftSolenoids, int leftMotor, int rightMotor,
			 int beltChannelA, int beltChannelB, boolean reverseDirection, double wdpp){
//		this.motors = new MotorGroup(pickerUpperChannels, talonSRX, inverseDirection, 
//				encoder);
		beltEncoder = new Encoder(beltChannelA, beltChannelB, reverseDirection, wdpp);
		liftPistons = new AirSystem(new Compressor(), liftSolenoids);
		leftLiftMotor = new MotorGroup(new int[]{leftMotor}, motorType , leftInverseDirection, beltEncoder);
		rightLiftMotor = new MotorGroup(new int[]{rightMotor}, motorType, rightInverseDirection, beltEncoder);
	}

	public void liftMode(Joystick auxStick) {
		int joystickNum = 0;
		int button = 3;
		if (DriverStation.antiBounce(joystickNum, button)) {
			angledPickerUpper();
		}
		else {
			uprightPickerUpper();	
		}
	}


	/**
	 * Lifts the pickerupper device into the straight position
	 */
	public void uprightPickerUpper() {
		liftPistons.extend();
	}

	/**
	 * Brings back the pickerupper device into an angle
	 */
	public void angledPickerUpper() {
		liftPistons.retract();
	}


	/**
	 * runs the motors for the pickerupper
	 */
	public void run(){
		motors.set(DriverStation.auxStick.getRawAxis(DriverStation.YAxis));
	}
	
	public void runAuto (double liftDistance){
		// Need to find getDisplacement parameter value (DISTANCE_PER_PULSE) for belt motor.
	   double speed;
	   if (liftDistance >= 0.0)
		   speed = AUTO_LIFT_SPEED;
	   else
		   speed = -AUTO_LIFT_SPEED;
	   
		if (Math.abs(beltEncoder.getDisplacement(47.0/700.0)) <= Math.abs(liftDistance))
			motors.set(speed);
		else
			motors.set(0);
		
	}


}
