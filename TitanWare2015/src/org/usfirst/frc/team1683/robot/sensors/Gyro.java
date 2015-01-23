package org.usfirst.frc.team1683.robot.sensors;

public class Gyro extends edu.wpi.first.wpilibj.Gyro implements Sensor {

	private final double SENSITIVITY = 0.009; 

	public Gyro(int channel) {
		super(channel);
	    setSensitivity(SENSITIVITY);
	    this.initGyro();
	    reset();
	}
	
	//returns the actual angle in degrees that the robot is currently facing.
    public double getAngle(){
    	return getAngle();
    }
	
    @Override
	public double getRaw() {
		// TODO Auto-generated method stub
		return getAngle();
	}
    
    //adds current angle to the original position
    public double add(double startAngle) {
    	return startAngle+getAngle();
    }
}
