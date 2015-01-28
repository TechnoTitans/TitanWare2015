package org.usfirst.frc.team1683.robot.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Class to interpret output from RoboRealm
 * @author David
 *
 */
public class Vision {
	
	public NetworkTable table;
	
	/**
	 * Constructor
	 */
	public Vision() {
		this.table = NetworkTable.getTable("Vision"); // Table to get data from RoboRealm
	}
	
//	/**
//	 * @return Array of blobs and their centers
//	 */
//	public ScreenPos[] getBlobCenter() {		
//		return null;
//	}
	
}
