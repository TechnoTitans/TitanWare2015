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


public class PickerUpper implements Runnable{
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
	double beltTargetPosition;
	HDrive hDrive;

	/**
	 * Constructor - one motor lift without encoder
	 * @param pickerUpperChannels
	 * @param motorType
	 * @param inverseDirection
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PickerUpper(int[] pickerUpperChannels, Class motorType, boolean inverseDirection){
		this.motors = new MotorGroup("Picker Upper", pickerUpperChannels, motorType, inverseDirection);
	}
	
	/**
	 * Constructor - one motor lift with encoder
	 * @param motorType
	 * @param inverseDirection
	 * @param liftSolenoids
	 * @param pickerUpperChannels
	 * @param beltChannelA
	 * @param beltChannelB
	 * @param reverseDirection
	 * @param wdpp
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PickerUpper(Class motorType, boolean inverseDirection, int[] liftSolenoids, int[] pickerUpperChannels,
			 int beltChannelA, int beltChannelB, boolean reverseDirection, double wdpp, 
			 PressureSensor pressure, Photogate photogate, HDrive hDrive){
		beltEncoder = new Encoder(beltChannelA, beltChannelB, reverseDirection, wdpp);
		this.motors = new MotorGroup("Picker Upper", pickerUpperChannels, motorType, inverseDirection, 
				beltEncoder);
		this.pressure = pressure;
		this.photogate = photogate;
		pistons = new DualActionPistons(liftSolenoids, pressure);
//		isForward = true;
//		pistons = new DoubleActionSolenoid(liftSolenoids, pressure);
	}
	/**
	 * Constructor - two motor lift with encoder
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PickerUpper(Class motorType, boolean leftInverseDirection, boolean rightInverseDirection,
			 int[] liftSolenoids, int leftMotor, int rightMotor,
			 int beltChannelA, int beltChannelB, boolean reverseDirection, double wdpp, Photogate photogate, PressureSensor pressure){
		this.pressure = pressure;
		beltEncoder = new Encoder(beltChannelA, beltChannelB, reverseDirection, wdpp);
		pistons = new DualActionPistons(liftSolenoids, pressure);
		leftLiftMotor = new MotorGroup("Left Lift Motor", new int[]{leftMotor}, motorType , leftInverseDirection, beltEncoder);
		rightLiftMotor = new MotorGroup("Right Lift Motor",new int[]{rightMotor}, motorType, rightInverseDirection, beltEncoder);
		this.photogate = photogate;
		uprightPickerUpper();
	}

	public void liftMode(int joystickNumber) {
		motors.set(DriverStation.auxStick.getRawAxis(DriverStation.YAxis));
		if (DriverStation.antiBounce(joystickNumber, HWR.PICKER_UPPER)) {
			if (isForward){
				angledPickerUpper();
			}else{
				uprightPickerUpper();
			}
		}
		if (DriverStation.antiBounce(joystickNumber, HWR.CALIBRATE_BELT)){
			calibrateToZero();
		}
		if (DriverStation.antiBounce(joystickNumber, HWR.GO_TO_HOME)){
			goToZero();
		}
		if (DriverStation.antiBounce(joystickNumber, HWR.LIFT_FIRST_TOTE)){
			liftFirstTote();
		}
		if (DriverStation.antiBounce(joystickNumber, HWR.LIFT_SECOND_TOTE)){
			liftSecondTote();
		}
		if (beltEncoder.getDistance()>HWR.MOVE_MAX)
			motors.moveDistance(HWR.MOVE_MAX-beltEncoder.getDistance());
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
	
	public void calibrateToZero(){
		beltEncoder.reset();
	}
	
	public void goToZero(){
		new Thread(this).start();
	}


	/**
	 * Goes To Position
	 */
	@Override
	public void run(){
		while(beltEncoder.getDistance() > 0) {
			motors.set(-AUTO_LIFT_SPEED);
		}
		while (beltEncoder.getDistance()<0){
			motors.set(AUTO_LIFT_SPEED);
		}
//		while (!photogate.get()){
//			motors.set(-AUTO_LIFT_SPEED);
//		}
	}
	
	public void liftHeight(double changeInHeight)
	{
		motors.moveDistance(changeInHeight);
	}
	
	public void liftToHeight(double targetHeight){
		DriverStation.sendData("Target Height", targetHeight);
		double b = HWR.B1 +getHeightFromHDrive();
		beltTargetPosition = (targetHeight-b)/HWR.SLOPE;
		motors.moveDistance(beltTargetPosition - beltEncoder.getDisplacement(HWR.liftEncoderWDPP));
		DriverStation.sendData("Belt Position", beltTargetPosition);
	}
	
	public void liftFirstTote(){
		liftToHeight(HWR.SINGLE_TOTE_HEIGHT+getHeightFromHDrive());
	}
	
	public void liftSecondTote(){
		liftToHeight(HWR.DOUBLE_TOTE_HEIGHT+getHeightFromHDrive());
	}
	
	public void liftThirdTote(){
		liftToHeight(HWR.TRIPLE_TOTE_HEIGHT+getHeightFromHDrive());
	}
	
	public void liftFourthTote(){
		liftToHeight(HWR.FOURTH_TOTE_HEIGHT+getHeightFromHDrive());
	}
	
	public void liftBarrel(){
		liftToHeight(HWR.BARREL_HEIGHT+getHeightFromHDrive());
	}
	
	public void drop(){
		goToZero();
	}
	
	public void setToZero(){
		while (!photogate.get()){
			motors.set(-AUTO_LIFT_SPEED);
		}
	}
	
	public double getHeightFromHDrive(){
		if (hDrive.isDeployed())
			return HWR.H_DRIVE_HEIGHT;
		else
			return 0;
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
		
		@SuppressWarnings("unused")
		public AirSystem getFrontAirSystem(){
			return frontAirSystem;
		}
		@SuppressWarnings("unused")
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
