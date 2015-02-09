package org.usfirst.frc.team1683.robot.vision;

public interface Camera {
	public String getCamID();
	public void setContrast(int contrast);
	public void setBrightness(int brightness);
	public void setGamma(int gamma);
}
