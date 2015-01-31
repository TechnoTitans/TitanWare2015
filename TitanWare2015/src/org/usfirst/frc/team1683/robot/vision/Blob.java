package org.usfirst.frc.team1683.robot.vision;

/**
 * Representation of blobs seen by RoboRealm
 * @author David
 *
 */
public class Blob {
	
	public final int INDEX;
	public final int HEIGHT;
	public final int WIDTH;
	public final int X_POS;
	public final int Y_POS;	
	
	/**
	 * Constructor
	 * @param INDEX		Index of blob
	 * @param HEIGHT	Height of blob
	 * @param WIDTH		Width of blob
	 * @param X_POS		X position of center of blob
	 * @param Y_POS		Y position of center of blob
	 */
	public Blob(int INDEX, int HEIGHT, int WIDTH, int X_POS, int Y_POS) {
		this.INDEX 	= INDEX;
		this.HEIGHT = HEIGHT;
		this.WIDTH  = WIDTH;
		this.X_POS 	= X_POS;
		this.Y_POS 	= Y_POS;
	}
	
	
}
