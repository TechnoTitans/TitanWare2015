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
			final NumberArray data = new NumberArray();
			Vision.table.retrieveValue("Vision", data);
			if (data.size()>0){
				System.out.println(data.get(0) + ' ' + data.get(1));
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
