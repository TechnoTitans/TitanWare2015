package org.usfirst.frc.team1683.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class Gyro extends edu.wpi.first.wpilibj.Gyro implements Sensor {

	public final static double SENSITIVITY = 0.009; 
    AnalogInput channel;
	
	public Gyro(int channel) {
		super(channel);
	    super.setSensitivity(SENSITIVITY);
	    super.initGyro();
	    super.reset();
	}
	
	public Gyro(AnalogInput channel){
		super(channel);
		this.channel=channel;
		super.setSensitivity(SENSITIVITY);
		super.initGyro();
		super.reset();
	}
	public double getVoltage(){
		return channel.getAverageVoltage();
	}
	
		
    @Override
	public double getRaw() {
		return getAngle();
	}
    
    //adds current angle to the original position
    public double add(double startAngle) {
    	return startAngle+getAngle();
    }
}
