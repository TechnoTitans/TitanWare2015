package org.usfirst.frc.team1683.robot.drivetrain;
/**
 * 
 * @author Animesh Koratana & Seung-Seok
 *
 */
public class Talon extends edu.wpi.first.wpilibj.Talon implements Motor{

	boolean hasEncoder;
	boolean reverseDirection;
	Encoder encoder;

	double lowSpeed;
	double mediumSpeed;
	double highSpeed;	
	/**
	 * Constructor
	 * @param channel
	 * @param reverseDirection
	 */
	public Talon(int channel, boolean reverseDirection) {
		super(channel);
		this.hasEncoder = false;
		this.reverseDirection = reverseDirection;
	}
	/**
	 * Constructor
	 * @param channel
	 * @param aChannel
	 * @param bChannel
	 * @param reverseDirection
	 */
	public Talon (int channel, int aChannel, int bChannel, boolean reverseDirection, double wheelDistancePerPulse){
		super(channel);
		this.encoder = new Encoder(aChannel, bChannel, reverseDirection, wheelDistancePerPulse);
		this.reverseDirection = reverseDirection;
		this.hasEncoder = true;
	}
	/**
	 * Moves the robot a certain distance
	 */
	public void moveDistance(double distanceInMeters){
		if (hasEncoder){
			Thread thread = new Thread(new MotorMover(this, distanceInMeters));
			thread.start();
		}
	}
	/**
	 * sets the speed
	 */
	@Override
	public void set(double speed){
		if (reverseDirection){
			super.set(-speed);
		}else{
			super.set(speed);
		}
	}
	/**
	 * moves the robot a certain degrees
	 */
	public void moveDegrees(double degrees){
		double radians = degrees * (Math.PI/180);		//converts degrees to radians
		double distance = radians*Encoder.WHEEL_RADIUS;	//converts radians to distance
		moveDistance(distance);							//travels distance
	}

	public class MotorMover implements Runnable{

		double distanceInMeters;
		Talon talon;

		public MotorMover(Talon talon, double distanceInMeters) {
			this.distanceInMeters = distanceInMeters;
			this.talon = talon;
		}
		@Override
		public void run() {
			encoder.reset();
			double speed;
			if (distanceInMeters>0)
				speed = mediumSpeed;
			else
				speed = -mediumSpeed;
			while (Math.abs(encoder.getDistanceMeters()) < distanceInMeters){
					talon.set(speed);
				}
			talon.stopMotor();
			encoder.reset();
		}

	}

	@Override
	public void stop() {
		this.stop();
		
	}

}
