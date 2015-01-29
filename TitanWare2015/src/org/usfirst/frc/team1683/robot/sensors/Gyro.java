package org.usfirst.frc.team1683.robot.sensors;

public class Gyro extends edu.wpi.first.wpilibj.Gyro implements Sensor {

	private final double SENSITIVITY = 0.009; 

	public Gyro(int channel) {
		super(channel);
	    setSensitivity(SENSITIVITY);
	    this.initGyro();
	    reset();
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
