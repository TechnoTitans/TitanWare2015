package org.usfirst.frc.team1683.robot.drivetrain;

/**
 * Class to handle the receiving and interpreting data from the encoders
 */
public class Encoder extends edu.wpi.first.wpilibj.Encoder{
    //private final double TURNING_WHEEL_DISTANCE_PER_PULSE = 0.01840659135;
    //private static final double WHEEL_DISTANCE_PER_PULSE=47.0/700.0;//inches/pulse
    public static final double WHEEL_RADIUS = 0.0; //must be determined through measurement
    //Above value has been confirmed from past revisions as the constant for the robot not the mule
    //may require re-tuning on 2nd day of Competition.
    double wheelDistancePerPulse;
    /**
     * Constructor
     * @param aChannel
     * @param bChannel
     * @param reverseDirection
     * @param wheelDistancePerPulse
     */
    public Encoder(int aChannel, int bChannel, boolean reverseDirection, double wheelDistancePerPulse){
            super(aChannel, bChannel, reverseDirection);
            this.wheelDistancePerPulse = wheelDistancePerPulse;
    }
    /**
     * Gets distance from encoder in terms of inches
     * @return distance in inches
     */
//     Needs to be changed such that WHEEL_DISTANCE_PER_PULSE is set in the constructor;
    public double getDistance () {
        this.setDistancePerPulse(wheelDistancePerPulse); // Need to figure out DISTANCE_PER_PULSE
        return Math.abs(super.getDistance()); 
    }
    /**
     * Gets displacement from encoder in inches.
     * @param DISTANCE_PER_PULSE 
     * @return displacement in inches.
     */
    public double getDisplacement(double DISTANCE_PER_PULSE) {
    	this.setDistancePerPulse(DISTANCE_PER_PULSE);
    	return super.getDistance();
    }
    /**
     * Gets distance from encoder in terms of feet
     * @return distance in feet
     */
     public double getDistanceFt(){
        return getDistance()/12;
    }
     /**
      * Gets distance from encoder in terms of meters
      * @return distance in meters
      */
    // converts distance from feet to meters. 
    public double getDistanceMeters(){
        return getDistanceFt()*0.3048;
    }
       
}