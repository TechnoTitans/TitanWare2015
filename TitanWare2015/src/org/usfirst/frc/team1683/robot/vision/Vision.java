package org.usfirst.frc.team1683.robot.vision;

import edu.wpi.first.wpilibj.CameraServer;

/**
 * Class to interpret output from RoboRealm
 * @author David
 *
 */
public class Vision {
	
//	public static NetworkTable table;
	
	/**
	 * Constructor
	 */
	public Vision() {
		CameraServer.getInstance().startAutomaticCapture("cam1");
//		table = NetworkTable.getTable("Vision"); // Table to get data from RoboRealm
	}
	
//	/**
//	 * @return Array of blobs and their centers
//	 */
//	public ScreenPos[] getBlobCenter() {		
//		return null;
//	}
	
}
