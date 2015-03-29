package org.usfirst.frc.team1683.robot.pickerupper;

import org.usfirst.frc.team1683.robot.HWR;
import org.usfirst.frc.team1683.robot.drivetrain.Encoder;
import org.usfirst.frc.team1683.robot.drivetrain.HDrive;
import org.usfirst.frc.team1683.robot.drivetrain.MotorGroup;
import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.Gyro;
import org.usfirst.frc.team1683.robot.sensors.Photogate;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotBase;


public class PickerUpper{
	MotorGroup beltMotors;
	MotorGroup leftLiftMotor;
	MotorGroup rightLiftMotor;
	public Encoder beltEncoder;
	int liftButton;
	final double AUTO_LIFT_SPEED = 0.5;
	boolean isForward;
	Photogate photogate;
	double beltTargetPosition;
	HDrive hDrive;
	boolean enableSensor;
	Thread currentThread;
	PIDController controller;
	TiltScrew tilter;
	TiltMover mover;

	/**
	 * Constructor - one motor lift without encoder
	 * @param pickerUpperChannels
	 * @param motorType
	 * @param inverseDirection
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PickerUpper(int[] pickerUpperChannels, Class motorType, boolean inverseDirection){
		this.beltMotors = new MotorGroup("Picker Upper", pickerUpperChannels, motorType, inverseDirection);
	}

	/**
	 * Constructor - one motor lift with encoder
	 * @param motorType
	 * @param inverseBeltDirection
	 * @param liftSolenoids
	 * @param pickerUpperChannels
	 * @param beltChannelA
	 * @param beltChannelB
	 * @param reverseDirection
	 * @param wdpp
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PickerUpper(Class beltMotorType, boolean inverseBeltDirection, int[] pickerUpperChannels,
			int beltChannelA, int beltChannelB, boolean reverseDirection, double wdpp, Photogate photogate,
			Class tiltMotorType, int tiltMotor, boolean inverseTiltDirection, HDrive hDrive, Gyro gyro, RobotBase base){
		beltEncoder = new Encoder(beltChannelA, beltChannelB, reverseDirection, wdpp);
		this.beltMotors = new MotorGroup("Picker Upper", pickerUpperChannels, beltMotorType, inverseBeltDirection, 
				beltEncoder);
		this.photogate = photogate;
		tilter = new TiltScrew(tiltMotor, tiltMotorType, inverseTiltDirection);
		mover = new TiltMover(tilter, base);
		mover.start();
		this.hDrive = hDrive;
		//		isForward = true;
		//		pistons = new DoubleActionSolenoid(liftSolenoids, pressure);
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
	public PickerUpper(Class beltMotorType, boolean inverseDirection, int[] pickerUpperChannels,
			int beltChannelA, int beltChannelB, boolean reverseDirection, double wdpp, Photogate photogate,
			Class tiltMotorType, int tiltMotor, boolean inverseTiltDirection, HDrive hDrive, int index, Gyro gyro, RobotBase base){		
		beltEncoder = new Encoder(beltChannelA, beltChannelB, reverseDirection, wdpp);
		this.beltMotors = new MotorGroup("Picker Upper", pickerUpperChannels, beltMotorType, inverseDirection, 
				beltEncoder);
		this.photogate = photogate;
		tilter = new TiltScrew(tiltMotor, tiltMotorType, inverseTiltDirection);
		this.hDrive = hDrive;
		//		isForward = true;
		//		pistons = new DoubleActionSolenoid(liftSolenoids, pressure);

		enableSensor = DriverStation.getBoolean("enableSensor");	
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
	public PickerUpper(Class beltMotorType, boolean leftInverseDirection, boolean rightInverseDirection,
			int leftMotor, int rightMotor,int beltChannelA, int beltChannelB, boolean reverseDirection,
			Class tiltMotorType, int tiltMotor, boolean inverseTiltDirection, double wdpp, Photogate photogate, Gyro gyro, RobotBase base){
		beltEncoder = new Encoder(beltChannelA, beltChannelB, reverseDirection, wdpp);
		tilter = new TiltScrew(tiltMotor, tiltMotorType, inverseTiltDirection);
		leftLiftMotor = new MotorGroup("Left Lift Motor", new int[]{leftMotor}, beltMotorType , leftInverseDirection, beltEncoder);
		rightLiftMotor = new MotorGroup("Right Lift Motor",new int[]{rightMotor}, beltMotorType, rightInverseDirection, beltEncoder);
		this.photogate = photogate;
		if (DriverStation.getBoolean("EnablePID")){
			enablePID();
		}
	}

	public void liftMode(int joystickNumber) {
		/*if (DriverStation.antiBounce(joystickNumber, HWR.TOGGLE_BELT_PISTON)) {
			if (isForward){
				angledPickerUpper();
	 		}else{
				uprightPickerUpper();
			}
		}*/
		beltMotors.set(DriverStation.auxStick.getRawAxis(DriverStation.YAxis));

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

		if (DriverStation.antiBounce(joystickNumber, 8)){
			beltMotors.moveDistanceInches(12);
		}

		if (beltEncoder.getDistance()>HWR.MOVE_MAX)
			beltMotors.moveDistance(HWR.MOVE_MAX-beltEncoder.getDistance());
		if (enableSensor&&photogate.get()){
			calibrateToZero();
		}
		//		if (DriverStation.antiBounce(joystickNumber, HWR.FREEZE_BELT)){
		//			freezePickerUpper();
		//		}
	}
	
	public MotorGroup getMotorGroup(){
		return beltMotors;
	}


	public void enablePID(){
		double p = DriverStation.getDouble("PIDValueP");
		double i = DriverStation.getDouble("PIDValueI");
		double d = DriverStation.getDouble("PIDValueD");
		double tolerance = DriverStation.getDouble("PIDTolerance");

		beltMotors.enablePIDController(p, i, d,tolerance, beltMotors.getEncoder());
	}

	/**
	 * Fully uprights the PickerUpper
	 */
	public void uprightPickerUpper() {
		tilter.tiltForward(2);
	}

	//	/**
	//	 * Lifts the pickerupper device into the straight position
	//	 */
	//	public void uprightPickerUpperToggle() {
	//		if (!isForward){
	//			pistons.changeState();
	//			isForward = true;
	//		}
	//	}

	/**
	 * fully angles the PickerUpper
	 */
	public void anglePickerUpper() {
		tilter.tiltBackward(2);
	}

	//	/**
	//	 * Brings back the pickerupper device into an angle
	//	 */
	//	public void angledPickerUpperToggle() {
	//		if (isForward){
	//			pistons.changeState();
	//			isForward = false;
	//		}
	//	}

	//	public void freezePickerUpper() {
	//		pistons.freeze();
	//	}

	public void calibrateToZero(){
		beltEncoder.reset();
	}

	public void goToZero(){
		//		currentThread = new Thread(this);
		//		currentThread.setPriority(Thread.MAX_PRIORITY);
		//		currentThread.start();
		liftToHeight(0);
	}

	public Thread getCurrentThread(){
		return currentThread;
	}

	public TiltScrew getTilter() {
		return tilter;
	}

	//	/**
	//	 * Goes To Position
	//	 */
	//	@Override
	//	public void run(){
	//		while(beltEncoder.getDistance() > 0) {
	//			motors.set(-AUTO_LIFT_SPEED);
	//		}
	//		while (beltEncoder.getDistance()<0){
	//			motors.set(AUTO_LIFT_SPEED);
	//		}
	////		while (!photogate.get()){
	////			motors.set(-AUTO_LIFT_SPEED);
	////		}
	//	}

	/**
	 * 
	 * @param changeInHeight - measured in inches
	 */
	public void liftHeight(double changeInHeight){
		double changeInBeltPosition = changeInHeight/HWR.SLOPE;
		beltMotors.moveDistanceInches(changeInBeltPosition);
		currentThread = beltMotors.getCurrentThread();
	}

	public void liftToHeight(double targetHeight){
		DriverStation.sendData("Target Height", targetHeight);
		double b = HWR.B1 +getHeightFromHDrive();
		beltTargetPosition = (targetHeight-b)/HWR.SLOPE;
		DriverStation.sendData("Belt Target Position", beltTargetPosition);
		double beltMove = beltTargetPosition - beltEncoder.getDistance();
		double relativeDistanceToMove = beltMove;// - (beltEncoder.getDistance() /*% HWR.PICKERUPPER_BELT_LENGTH*/);
		beltMotors.moveDistanceInches(relativeDistanceToMove);
		currentThread = beltMotors.getCurrentThread();
		DriverStation.sendData("Belt Move", relativeDistanceToMove);
	}

	/*
	public void liftToHeight(double targetHeight){
		DriverStation.sendData("targetHeight", targetHeight);
		double currentHeight = getCurrentHeight();
		DriverStation.sendData("currentHeight", currentHeight);
		double changeInHeight = targetHeight-currentHeight;
		double changeInBeltPosition = changeInHeight/HWR.SLOPE;
		DriverStation.sendData("changeInBeltPosition", changeInBeltPosition);
		motors.moveDistanceInches(changeInBeltPosition);
	}
	 */

	public void liftFirstTote(){
		liftHeight(HWR.SINGLE_TOTE_HEIGHT+getHeightFromHDrive());
	}

	public void liftSecondTote(){
		liftHeight(HWR.DOUBLE_TOTE_HEIGHT+getHeightFromHDrive());
	}

	public void liftThirdTote(){
		liftHeight(HWR.TRIPLE_TOTE_HEIGHT+getHeightFromHDrive());
	}

	public void liftToClearBarrel(){
		liftToHeight(HWR.BARREL_CLEAR_HEIGHT+getHeightFromHDrive());
	}

	public void liftBarrel(){
		liftToHeight(HWR.BARREL_HEIGHT+getHeightFromHDrive());
	}

	public void dropBarrel(){
		liftHeight(HWR.DROP_BARREL_HEIGHT);
	}
	
	public void dropTote(){
		liftHeight(HWR.DROP_TOTE_HEIGHT);
	}

	public void setToZero(){
		while (!photogate.get()){
			beltMotors.set(-AUTO_LIFT_SPEED);
		}
	}

	public double getHeightFromHDrive(){
		if (hDrive.isDeployed())
			return HWR.H_DRIVE_HEIGHT;
		else
			return 0;
	}

	public double getCurrentHeight(){
		double b = getHeightFromHDrive() + HWR.ROBOT_HEIGHT + HWR.DISTANCE_TO_INDEX*HWR.SLOPE;
		return HWR.SLOPE*beltEncoder.getDisplacement(beltEncoder.getDistancePerPulse())+b;
	}
}
