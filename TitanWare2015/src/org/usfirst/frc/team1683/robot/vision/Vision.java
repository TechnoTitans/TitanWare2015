package org.usfirst.frc.team1683.robot.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * 
 * @author David
 *
 */
public class Vision {
	
	private NetworkTable table;
	
	public Vision() {
		table = NetworkTable.getTable("Vision"); // Table to get data from RoboRealm
	}
	
	/**
	 * @return Array of blobs and their centers
	 */
	public ScreenPos[] getBlobCenter() {		
		return null;
	}
}
