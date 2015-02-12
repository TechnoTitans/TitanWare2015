package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.MotorGroup;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.pneumatics.AirSystem;
import org.usfirst.frc.team1683.robot.sensors.Photogate;
import org.usfirst.frc.team1683.robot.sensors.PressureSensor;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;


public class PickerUpper {
	MotorGroup motors;
	DualActionPistons pistons;
	MotorGroup leftLiftMotor;
	MotorGroup rightLiftMotor;
	public Encoder beltEncoder;
	int liftButton;
	final double AUTO_LIFT_SPEED = 0.5;
	PressureSensor pressure;
	boolean isForward;
	Photogate photogate;

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
	 * @param inverseDirection
	 * @param liftSolenoids
	 * @param pickerUpperChannels
	 * @param beltChannelA
	 * @param beltChannelB
	 * @param reverseDirection
	 * @param wdpp
	 */
	public PickerUpper(Class motorType, boolean inverseDirection, int[] liftSolenoids, int[] pickerUpperChannels,
			 int beltChannelA, int beltChannelB, boolean reverseDirection, double wdpp, PressureSensor pressure, Photogate photogate){
		this.motors = new MotorGroup(pickerUpperChannels, motorType, inverseDirection, 
				beltEncoder);
		beltEncoder = new Encoder(beltChannelA, beltChannelB, reverseDirection, wdpp);
		this.pressure = pressure;
		this.photogate = photogate;
		pistons = new DualActionPistons(liftSolenoids, pressure);
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
			 int beltChannelA, int beltChannelB, boolean reverseDirection, double wdpp, Photogate photogate, PressureSensor pressure){
//		this.motors = new MotorGroup(pickerUpperChannels, talonSRX, inverseDirection, 
//				encoder);
		this.pressure = pressure;
		beltEncoder = new Encoder(beltChannelA, beltChannelB, reverseDirection, wdpp);
		pistons = new DualActionPistons(liftSolenoids, pressure);
		leftLiftMotor = new MotorGroup(new int[]{leftMotor}, motorType , leftInverseDirection, beltEncoder);
		rightLiftMotor = new MotorGroup(new int[]{rightMotor}, motorType, rightInverseDirection, beltEncoder);
		this.photogate = photogate;
	}

	public void liftMode(int joystickNubmer) {
		int button = HWR.PICKER_UPPER;
		if (DriverStation.antiBounce(joystickNubmer, button)) {
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
		if (!isForward){
			pistons.changeState();
			isForward = true;
		}
	}

	/**
	 * Brings back the pickerupper device into an angle
	 */
	public void angledPickerUpper() {
		if (isForward){
			pistons.changeState();
			isForward = false;
		}
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
	
	public void liftHeight(double changeInHeight)
	{
		motors.moveDistance(changeInHeight);
	}
	
	//targetHeight and height should be in meters
	//assumes lift movement corresponds directly to height change
	public void liftToHeight(double targetHeight, HDrive hDrive)
	{
		setToZero();
		double height = HWR.ROBOT_HEIGHT + HWR.DISTANCE_TO_INDEX;
		if (hDrive.isDeployed())
			height+=HWR.H_DRIVE_HEIGHT;
		motors.moveDistance(targetHeight-height);
	}
	
	//accomodates for the error between lift movement and height change
	public void liftToHeight2(double targetHeight, HDrive hDrive)
	{
		setToZero();
		double b;
		if (hDrive.isDeployed())
			b = HWR.B2;
		else
			b = HWR.B1;
		motors.moveDistance((targetHeight-b)/HWR.SLOPE);
	}
	
	public void setToZero(){
		while (!photogate.get()){
			motors.set(-AUTO_LIFT_SPEED);
		}
	}
	
	private class DualActionPistons{
		AirSystem frontAirSystem;
		AirSystem backAirSystem;
		public DualActionPistons(int[] pistons, PressureSensor pressure) { //front piston, back Piston
			Compressor compressor = new Compressor();
			frontAirSystem = new AirSystem(compressor, new int[]{pistons[0]}, pressure);
			backAirSystem = new AirSystem(compressor, new int[]{pistons[1]}, pressure);
			isForward = false;
		}
		
		public AirSystem getFrontAirSystem(){
			return frontAirSystem;
		}
		public AirSystem getBackAirSystem(){
			return backAirSystem;
		}
		
		public void changeState(){
			if (frontAirSystem.isExtended()){
				frontAirSystem.retract();
				backAirSystem.extend();
			}else{
				frontAirSystem.extend();
				backAirSystem.retract();
			}
		}
	}
	
	
}
