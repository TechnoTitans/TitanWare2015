package org.usfirst.frc.team1683.robot.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Representation of blobs seen by RoboRealm
 * @author David
 *
 */
public class Blob {
	
	public ScreenPos center;
	public int height;
	public int width;
	
	@SuppressWarnings("deprecation")
	public Blob(NetworkTable table) {
		height = table.getInt("height");
	}
}
