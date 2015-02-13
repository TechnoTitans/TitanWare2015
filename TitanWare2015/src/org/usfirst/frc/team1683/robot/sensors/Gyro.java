package org.usfirst.frc.team1683.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class Gyro extends edu.wpi.first.wpilibj.Gyro implements Sensor {

	public final static double SENSITIVITY = 0.00656693; //0.0072464; 
	public final static double driftRate = 0.3333;
	public final static double HDRIVE_THRESHOLD=5;
    AnalogInput channel;
	
	public Gyro(int channel) {
		super(channel);
	    super.initGyro();
	    super.reset();
	    super.setSensitivity(SENSITIVITY);
	}
	
//	public Gyro(AnalogInput channel){
//		super(channel);
//		this.channel=channel;
//		super.setSensitivity(SENSITIVITY);
//		super.initGyro();
//		super.reset();
//	}
	public double getVoltage(){
		return channel.getAverageVoltage();
	}
	public double getDirection(){
		return super.getAngle()%360;
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
