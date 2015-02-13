package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.main.DriverStation;
import org.usfirst.frc.team1683.robot.sensors.Gyro;


import edu.wpi.first.wpilibj.Timer;

public class GyroTest implements Tester{
    Gyro gyro;
    Timer timer;
    double sensitivity=0.008;
    double bestSensitivity;
    final int npoints =10;
    final double driftTime=10;
    final double range=0.001;
    final double sensitivityIncrement=2*range/npoints;
    double newDrift,bestDrift;
   
    public GyroTest(Gyro gyro,Timer timer){
    	this.gyro = gyro;
    	this.timer=timer;
    	timer.start();
    }
	@Override
	public void test() {
		// TODO Auto-generated method stub
		DriverStation.sendData("Current Direction of Gyro", (gyro.getDirection()));//-(timer.get()*Gyro.driftRate)));
		DriverStation.sendData("Current Angle from Gyro", (gyro.getAngle()));
		DriverStation.sendData("Gyro Voltage", gyro.getVoltage());
//		DriverStation.sendData("Gyro Sensitivity", 100000*(.0065+DriverStation.auxStick.getRawAxis(3)*0.0005));
//		gyro.setSensitivity(.0065+DriverStation.auxStick.getRawAxis(3)*0.0005);
		DriverStation.sendData("Drift rate", gyro.getAngle()/timer.get());
		Timer.delay(.2);
	}
	public double getAngleDelayed(double x){
		gyro.setSensitivity(x);
		gyro.reset();
		Timer.delay(driftTime);
		return gyro.getAngle();
	}
	public void getBestSensitivity(){
		bestDrift=Math.abs(getAngleDelayed(sensitivity));
		for(double x=sensitivity-range;x<sensitivity+range;x+=sensitivityIncrement){
			newDrift = Math.abs(getAngleDelayed(x));
			DriverStation.sendData("Current Sensitivity",x);
			DriverStation.sendData("Current Drift",newDrift);
			if(newDrift<bestDrift){
			  bestSensitivity=x;
			  bestDrift=newDrift;
			  DriverStation.sendData("Best Gyro Sensitivity",bestSensitivity);
			  DriverStation.sendData("Best Gyro Drift", bestDrift);
			}
			
		}
		
	}
}
