package org.usfirst.frc.team1683.robot.sensors;

import org.usfirst.frc.team1683.robot.main.DriverStation;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Gyroscope and its methods.
 * @author Kirin
 * @author David Luo
 *
 */
public class Gyro extends edu.wpi.first.wpilibj.Gyro implements Sensor {

	public final static double SENSITIVITY = 0.00656693; //0.0072464; 
	public final static double driftRate = 0.3333;
	public final static double HDRIVE_THRESHOLD=5;
    AnalogInput channel;
    //decides whether to put onto DriverStation or not
    private boolean post = false;
	
    /**
     * Constructor
     * Cannot get voltage of Gyro.
     * @param channel Integer channel
     */
	public Gyro(int channel) {
		super(channel);
	    super.initGyro();
	    super.reset();
	    super.setSensitivity(SENSITIVITY);
	}
	
	/**
	 * Constructor
	 * Can get voltage of Gyro
	 * @param channel AnalogInput object channel
	 */
	public Gyro(AnalogInput channel){
		super(channel);
		this.channel=channel;
		super.initGyro();
		super.reset();
		super.setSensitivity(SENSITIVITY);
	}
	
	public void setPost(boolean post){
		this.post= post;
	}
	
	/**
	 * Gets the voltage of the Gyro.
	 * @return The voltage of the Gyro.
	 */
	public double getVoltage(){
		try {
			return channel.getAverageVoltage();
		}
		catch (NullPointerException e) {
			return 0;
		}
	}
	
	/**
	 * Gets the direction from original bearing.
	 * @return The direction the robot is facing in degrees (0-360)
	 */
	public double getDirection(){
		double angle;
		if (super.getAngle() > 0){
			angle = super.getAngle() % 360;
		}
		else if (super.getAngle() < 0){
			angle = 360 - Math.abs(super.getAngle() % 360);
		}
		else {
			angle = super.getAngle();
		}
		if (post){
			DriverStation.sendData("Gyro", angle);
		}
		return angle;
	}
		
	/**
	 * Gets the total angle turned.
	 * @return The total angle turned.
	 */
    @Override
	public double getRaw() {
		return getAngle();
	}
    
    //adds current angle to the original position
    public double add(double startAngle) {
    	return startAngle+getAngle();
    }
}
