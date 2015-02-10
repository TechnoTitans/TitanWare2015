package org.usfirst.frc.team1683.robot.vision;

import org.usfirst.frc.team1683.robot.drivetrain.TankDrive;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 * Class to interpret output from RoboRealm
 * @author David Luo
 */
public class Vision {
	
	public static NetworkTable table;
	// Variables to hold values retrieved from NetworkTable (RoboRealm).
	private NumberArray COG_X;
	private NumberArray COG_Y;
	private NumberArray HEIGHT; // Height of blobs in Pixels
	private NumberArray WIDTH; // Width of blobs in Pixels
	private NumberArray AREA; // Area of blobs in Pixels
	private int blobCount;
	
	// Camera ID manually retrieved from RoboRIO web interface.
//	private final String CAM_ID 			= "cam1";
	
	// Predetermined actual dimensions of object represented by blob.
	// All variables are in Meters.
//	private final double REF_HEIGHT 		= 19.5*2.54/100; 
	private final double REF_WIDTH 			= 29.25*2.54/100;
	private final double TOTE_WIDTH 		= 26.9*2.54/100;
//	private final double TOTE_HEIGHT 		= 12.1*2.54/100;
	private final double REF_DISTANCE		= 21*2.54/100; // Always in meters.
	// Resolution of picture.
	private final int REF_HEIGHT_PIXELS 	= 240;
	private final int REF_WIDTH_PIXELS		= 320;
	// Calculated Field of View
//	private final double HORIZ_FOV_DEG		= 67; //69.7089;
//	private final double VERT_FOV_DEG		= 49.8095;
	
	private final double CENTER_X = REF_HEIGHT_PIXELS/2;
//	private final double CENTER_Y = REF_WIDTH_PIXELS/2;
	
	private final double FOCAL_LENGTH		= (REF_WIDTH_PIXELS*REF_DISTANCE)/REF_WIDTH;
	
	// Distance away from center needed in order to be considered centered.
	private final double CENTER_THRESHOLD	= 10.0;
	
	/**
	 * Constructor
	 * Starts camera server for USB camera
	 */
	public Vision() {
//		CameraServer.getInstance().startAutomaticCapture(CAM_ID);
		table 		= NetworkTable.getTable("Vision"); // Table to get data from RoboRealm
		COG_X		= new NumberArray();
		COG_Y 		= new NumberArray();
		HEIGHT		= new NumberArray();
		WIDTH		= new NumberArray();
		AREA		= new NumberArray();
	} 
	
	/**
	 * Gets the data about blobs.
	 * @return Returns all the blobs seen on screen and their properties in an array.
	 */
	public Blob[] getData() {
		// Should not ever be null.
		blobCount 	= (int) Vision.table.getNumber("BLOB_COUNT");
		Blob[] blobs = new Blob[blobCount];
		
		// Might want to wrap in if statement instead.
		try {
			Vision.table.retrieveValue("COG_X", 	COG_X);
			Vision.table.retrieveValue("COG_Y", 	COG_Y);
			Vision.table.retrieveValue("HEIGHT", 	HEIGHT);
			Vision.table.retrieveValue("WIDTH", 	WIDTH);
			Vision.table.retrieveValue("AREA", 		AREA);
		}
		catch(TableKeyNotDefinedException exp) {
//			System.out.println("TableKeyNotDefinedException");
		}
		
		for (int i = 0; i < blobs.length; i++) {
			blobs[i] = new Blob(i, HEIGHT.get(i), WIDTH.get(i), COG_X.get(i), COG_Y.get(i), AREA.get(i));
		}
		
		return blobs;
	}
	
	/**
	 * Calculates the approximate distance from blob.
	 * @param Blob who's distance is to be calculated.
	 * @return Array distance away from blob in meters.
	 */
	public double calcDistance(Blob blob) {
//		double apparentWidth = TOTE_WIDTH * ((double)blob.WIDTH/REF_WIDTH);
//		double distance = (apparentWidth/2)/Math.tan(Math.toRadians(HORIZ_FOV_DEG));
		
//		double distance = TOTE_WIDTH*REF_WIDTH/(2*blob.WIDTH*Math.tan(Math.toRadians(HORIZ_FOV_DEG)));
		
		final double COMPENSATION = (4.0/3.0);
		
		double distance = (TOTE_WIDTH*FOCAL_LENGTH)/blob.WIDTH * COMPENSATION;
		
		return distance;
	}

	/**
	 * Gets the closest Blob in the array.
	 * @param blobs The array of Blobs.
	 * @return The closest Blob.
	 */
	public Blob getClosestBlob(Blob[] blobs) {
		Blob closest = blobs[0];
		// Successful use of for each loop.
		for (Blob blob : blobs) {
			if (calcDistance(blob) < calcDistance(closest)) {
				closest = blob;
			}
		}
		return closest;
	}
	
	/**
	 * Another implementation of getting the closest Blob in an array.
	 * Uses area of blob instead of calculated distance.
	 * May or may not be more accurate.
	 * @param blobs Array of blobs.
	 * @return The closest Blob
	 */
	public Blob getClosestBlob2(Blob[] blobs) {
		Blob closest = blobs[0];
		// Successful use of for each loop.
		for (Blob blob : blobs) {
			if (blob.AREA < closest.AREA) {
				closest = blob;
			}
		}
		return closest;
	}
	
//	/**
//	 * Gets the vector pointing towards the center.
//	 * @return Vector object representing center vector;
//	 */
//	public Vector getCenterVector(Blob blob) {
//		Vector center = new Vector(blob.X_POS, blob.Y_POS, CENTER_X, CENTER_Y);
//		return center;
//	}
	
//	/**
//	 * Attempts to center the closest blob on screen horizontally.
//	 */
//	public void centerClosestBlob(TankDrive driver) {
//		Blob closest = getClosestBlob(getData());
//		
//		final double SPEED = 3;
//		
//		if (Math.abs(closest.X_POS - CENTER_X) <= CENTER_THRESHOLD) {
//			driver.stop(); // No idea how this method is supposed to work yet.
//		}
//		// If right of tote, go left.
//		else if (closest.X_POS - CENTER_X > 0) {
//			driver.goSideways(-SPEED);
//		}
//		// If left of tote, go right.
//		else if(closest.X_POS - CENTER_X < 0) {
//			driver.goSideways(SPEED);
//		}
//		else {
//			driver.stop();
//		}
//	}
	
	/**
	 * Checks if the blob is centered.
	 * @param blob The blob to be checked.
	 * @return The side of the blob the robot is on. 0 for centered, -1 for on the left of blob, +1 for on the right, 2 for unexpected error.
	 */
	public int isCentered(Blob blob) {
		if (Math.abs(blob.X_POS - CENTER_X) <= CENTER_THRESHOLD) {
			return 0;
		}
		else if (blob.X_POS - CENTER_X > 0) {
			return -1;
		}
		else if (blob.X_POS - CENTER_X < 0) {
			return 1;
		}
		else {
			return 2;
		}
	}
	
	/**
	 * Determines how far off center the target blob is.
	 * @param blob The target blob
	 * @return How far off center the target blob is.
	 */
	public double centerOffset(Blob blob) {
		return blob.X_POS - CENTER_X;
	}
}