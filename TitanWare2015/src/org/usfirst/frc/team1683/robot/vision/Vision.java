package org.usfirst.frc.team1683.robot.vision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 * Class to interpret output from RoboRealm
 * @author David Luo
 */
public class Vision {
	
	public static NetworkTable table;
	private NumberArray COG_X;
	private NumberArray COG_Y;
	private NumberArray HEIGHT; // Height of blobs in Pixels
	private NumberArray WIDTH; // Width of blobs in Pixels
	private int BLOB_COUNT;
	private final String CAM_ID 			= "cam1";
	
	// Predetermined actual dimensions of object represented by blob.
	private final int REF_HEIGHT_INCHES 	= 0; 
	private final int REF_WIDTH_INCHES 		= 0;
	private final int REF_HEIGHT_PIXELS 	= 0;
	private final int REF_WIDTH_PIXELS		= 0;
	private final int REF_DISTANCE			= 0; // Always in inches.
	
	/**
	 * Constructor
	 * Starts camera server for USB camera
	 */
	public Vision() {
		CameraServer.getInstance().startAutomaticCapture(CAM_ID);
		table 		= NetworkTable.getTable("Vision"); // Table to get data from RoboRealm
		BLOB_COUNT 	= (int) Vision.table.getNumber("BLOB_COUNT");
		COG_X		= new NumberArray();
		COG_Y 		= new NumberArray();
		HEIGHT		= new NumberArray();
		WIDTH		= new NumberArray();
	} 
	
	/**
	 * @return Returns all the blobs seen on screen and their properties in an array.
	 */
	public Blob[] getData() {
		Blob[] blobs = new Blob[BLOB_COUNT];
		
		// Might want to wrap in if statement instead.
		try {
			Vision.table.retrieveValue("COG_X", 	COG_X);
			Vision.table.retrieveValue("COG_Y", 	COG_Y);
			Vision.table.retrieveValue("HEIGHT", 	HEIGHT);
			Vision.table.retrieveValue("WIDTH", 	WIDTH);			
		}
		catch(TableKeyNotDefinedException exp) {
			System.out.println("TableKeyNotDefinedException");
		}
		
		for (int i = 0; i < blobs.length; i++) {
			blobs[i] = new Blob(i, (int) HEIGHT.get(i), (int) WIDTH.get(i), (int) COG_X.get(i), (int) COG_Y.get(i));
		}
		
		return blobs;
	}
	
	/**
	 * Calculates the approximate distance from blob with reference heights and widths
	 * @return Array of distances to all blobs on screen.
	 */
	public int[] calcDistance() {
		
		return null;
	}
}
