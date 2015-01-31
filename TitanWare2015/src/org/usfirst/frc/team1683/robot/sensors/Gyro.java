package org.usfirst.frc.team1683.robot.sensors;

public class Gyro extends edu.wpi.first.wpilibj.Gyro implements Sensor {

	private final double SENSITIVITY = 0.0067; 

	public Gyro(int channel) {
		super(channel);
	    super.setSensitivity(SENSITIVITY);
	    super.initGyro();
	    super.reset();
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
