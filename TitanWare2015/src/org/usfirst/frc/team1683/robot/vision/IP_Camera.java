package org.usfirst.frc.team1683.robot.vision;

/**
 * Object representation of the Camera.
 * @author David
 *
 */
public class IP_Camera implements Camera{

	public final String CAM_ID;
	public final String IP_ADDRESS;
	
	/**
	 * Initializes camera with default settings.
	 * @param CAM_ID
	 * @param IP_ADDRESS
	 */
	public IP_Camera(final String CAM_ID, final String IP_ADDRESS) {
		this.CAM_ID = CAM_ID;
		this.IP_ADDRESS = IP_ADDRESS;
	}
	
	@Override
	public String getCamID() {
		return CAM_ID;		
	}

	@Override
	public void setContrast(int contrast) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBrightness(int brightness) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGamma(int gamma) {
		// TODO Auto-generated method stub
		
	}

}
