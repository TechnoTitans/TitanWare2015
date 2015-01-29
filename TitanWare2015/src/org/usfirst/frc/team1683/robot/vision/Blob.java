package org.usfirst.frc.team1683.robot.vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 * Representation of blobs seen by RoboRealm
 * @author David
 *
 */
public class Blob {
	
	public ScreenPos center;
	public int height;
	public int width;
	public final int INDEX;
	NetworkTable table;
//	public final NumberArray data;
	
	/**
	 * Constructor
	 * @param table Network Table
	 * @param index Index of blob
	 */
	public Blob(NetworkTable table, int index) {
		this.INDEX = index;
		this.table = table;
//		this.data = new NumberArray();
	}
	
	/**
	 * Gets center of blob specified.
	 * @param table
	 * @return
	 */
	public ScreenPos getCenter(){
		int xPos;
		int yPos;
		try {
			xPos = (int) table.getNumber("COG_X");
//			final NumberArray COG_X = new NumberArray();
//			final NumberArray COG_Y = new NumberArray();
//			table.retrieveValue("COG_X", COG_X);
//			Vision.table.retrieveValue("COG_Y", COG_Y);
//			if (COG_X.size()>0){
//				System.out.println(COG_X.get(0));// + ' ' + COG_X.get(1));
//				}
//			else {
//				System.out.println("Array is empty.");
			System.out.println(xPos);
//			}
		}
		catch(TableKeyNotDefinedException exp) {
			System.out.println("TableKeyNotDefinedException");
		}
		
		return null;
	}
}
