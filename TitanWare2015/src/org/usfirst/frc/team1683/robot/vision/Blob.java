package org.usfirst.frc.team1683.robot.vision;

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
//	public final NumberArray data;
	
	/**
	 * Constructor
	 * @param table Network Table
	 * @param index Index of blob
	 */
	@SuppressWarnings("deprecation")
	public Blob(int index) {
		this.INDEX = index;
//		this.data = new NumberArray();
//		table.retrieveValue("blobs", data);
	}
	
	/**
	 * Gets center of blob specified.
	 * @param table
	 * @return
	 */
	public void getCenter(){
		int xPos;
		int yPos;
		try {
//			System.out.println("try works");
			final NumberArray COG_X = new NumberArray();
//			final NumberArray COG_Y = new NumberArray();
			Vision.table.retrieveValue("COG_X", COG_X);
//			Vision.table.retrieveValue("COG_Y", COG_Y);
			if (COG_X.size()>0){
				System.out.println(COG_X.get(0));// + ' ' + COG_X.get(1));
				}
			else {
				System.out.println("Nope");
			}
		}
		catch(TableKeyNotDefinedException exp) {
			System.out.println("Error");
		}
		
//		return new ScreenPos(xPos, yPos);
	}
}
