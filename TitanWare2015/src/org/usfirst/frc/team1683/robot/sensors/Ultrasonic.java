package org.usfirst.frc.team1683.robot.sensors;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

public class Ultrasonic extends edu.wpi.first.wpilibj.Ultrasonic implements Sensor {

	public Ultrasonic(DigitalOutput pingChannel, DigitalInput echoChannel) {
		super(pingChannel, echoChannel);
		// TODO Auto-generated constructor stub
	}
	public Ultrasonic(DigitalOutput pingChannel, DigitalInput echoChannel,
			Unit units) {
		super(pingChannel, echoChannel, units);
		// TODO Auto-generated constructor stub
	}
	public Ultrasonic(int pingChannel, int echoChannel) {
		super(pingChannel, echoChannel);
		// TODO Auto-generated constructor stub
	}
	public Ultrasonic(int pingChannel, int echoChannel, Unit units) {
		super(pingChannel, echoChannel, units);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getRaw() {
		return 0;
	}




}
