package org.usfirst.frc.team1683.robot.drivetrain;

public class Talon extends edu.wpi.first.wpilibj.Talon implements Motor{

	boolean hasEncoder;
	boolean reverseDirection;
	Encoder encoder;

	double lowSpeed;
	double mediumSpeed;
	double highSpeed;	

	public Talon(int channel) {
		super(channel);
		this.hasEncoder = false;
	}
	public Talon (int channel, int aChannel, int bChannel, boolean reverseDirection){
		super(channel);
		this.encoder = new Encoder(aChannel, bChannel, reverseDirection);
		this.hasEncoder = true;
	}

	public void moveDistance(double distanceInMeters){
		if (hasEncoder){
			Thread thread = new Thread(new MotorMover(this, distanceInMeters));
			thread.run();
		}
	}

	@Override
	public void set(double speed){
		if (reverseDirection){
			super.set(speed);
		}else{
			super.set(-speed);
		}
	}

	public void setSpeeds(double low, double medium, double high){
		this.lowSpeed = low;
		this.mediumSpeed = medium;
		this.highSpeed = high;
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
			while (encoder.getDistanceMeters() < distanceInMeters){
				talon.set(mediumSpeed);
			}
			encoder.reset();
		}

	}

}
