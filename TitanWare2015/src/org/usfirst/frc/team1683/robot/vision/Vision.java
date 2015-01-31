package org.usfirst.frc.team1683.robot.vision;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 * Class to interpret output from RoboRealm
 * @author David
 *
 */
public class Vision {
	
	public static NetworkTable table;
	NumberArray COG_X;
	NumberArray COG_Y;
	NumberArray HEIGHT;
	NumberArray WIDTH;
	int BLOB_COUNT;
	Blob[] blobs;
	
	/**
	 * Constructor
	 */
	public Vision() {
		CameraServer.getInstance().startAutomaticCapture("cam1");
		table 		= NetworkTable.getTable("Vision"); // Table to get data from RoboRealm
		BLOB_COUNT 	= (int) Vision.table.getNumber("BLOB_COUNT");
		COG_X		= new NumberArray();
		COG_Y 		= new NumberArray();
		HEIGHT		= new NumberArray();
		WIDTH		= new NumberArray();
	}
	
	/**
	 * @return Returns all the blobs seen on screen and their properties.
	 */
	public Blob[] getData() {
		blobs = new Blob[BLOB_COUNT];
		try {
			Vision.table.retrieveValue("COG_X", 		COG_X);
			Vision.table.retrieveValue("COG_Y", 		COG_Y);
			Vision.table.retrieveValue("HEIGHT", 		HEIGHT);
			Vision.table.retrieveValue("WIDTH", 		WIDTH);			
		}
		catch(TableKeyNotDefinedException exp) {
			System.out.println("TableKeyNotDefinedException");
		}
		
		for (int i = 0; i < blobs.length; i++) {
			blobs[i] = new Blob(i, (int) HEIGHT.get(i), (int) WIDTH.get(i), (int) COG_X.get(i), (int) COG_Y.get(i));
		}
		
		return blobs;
	}
	
}
