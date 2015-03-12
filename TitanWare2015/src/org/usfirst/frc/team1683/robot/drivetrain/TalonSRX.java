package org.usfirst.frc.team1683.robot.drivetrain;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * 
 * @author Animesh Koratana & Seung-Seok
 *
 */
public class TalonSRX extends CANTalon implements Motor{

	boolean hasEncoder;
	Encoder encoder;

	double lowSpeed;
	double mediumSpeed;
	double highSpeed;	
	/**
	 * Constructor
	 * @param channel
	 * @param reverseDirection
	 */
	public TalonSRX(int channel, boolean reverseDirection) {
		super(channel);
		super.reverseOutput(reverseDirection);
	}
	/**
	 * Constructor
	 * @param channel
	 * @param aChannel
	 * @param bChannel
	 * @param reverseDirection
	 */
	public TalonSRX (int channel, int aChannel, int bChannel, boolean reverseDirection, double wheelDistancePerPulse){
		super(channel);
		super.changeControlMode(ControlMode.Current);
		this.encoder = new Encoder(aChannel, bChannel, reverseDirection, wheelDistancePerPulse);
		this.hasEncoder = true;
		super.reverseOutput(reverseDirection);
	}
	/**
	 * moves the robot a certain distance
	 */
	public void moveDistance(double distanceInMeters){
		if (hasEncoder){
			Thread thread = new Thread(new MotorMover(this, distanceInMeters));
			thread.start();
		}
	}
	
	public void moveDistanceInches(double distanceInInches){
		double distanceInMeters = distanceInInches*0.0254;
		moveDistance(distanceInMeters);
	}
	/**
	 * sets the speed
	 */
	@Override
	public void set(double speed){
		super.set(speed);
	}
	/**
	 * moves the robot a certain amount of degrees
	 */
	public void moveDegrees(double degrees){
		double radians = degrees * (Math.PI/180);		//converts degrees to radians
		double distance = radians*Encoder.WHEEL_RADIUS;	//converts radians to distance
		moveDistance(distance);							//travels distance
	}

	private class MotorMover implements Runnable{

		double distanceInMeters;
		TalonSRX talon;

		public MotorMover(TalonSRX talon, double distanceInMeters) {
			this.distanceInMeters = distanceInMeters;
			this.talon = talon;
		}
		
		@Override
		public void run() {
			encoder.reset();
			while (encoder.getDistanceMeters() < distanceInMeters){
				talon.set(mediumSpeed);
			}
			talon.set(0);
			encoder.reset();
		}

	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void stop() {
		super.stopMotor();
	}
	@Override
	public boolean hasEncoder() {
		return hasEncoder;
	}
	@Override
	public Encoder getEncoder() {
		return encoder;
	}
	@Override
	public int getChannel() {
		return super.getDeviceID();
	}
	@Override
	public void enableBrakeMode(boolean enable) {
		super.enableBrakeMode(enable);
	}
	@Override
	public double getSetPoint() {
		return super.getSetpoint();
	}
	


}
