package org.usfirst.frc.team1683.robot.vision;

/**
 * Representation of coordinates on RoboRealm. Bottom left corner represents (0,0).
 * @author David
 *
 */

public class ScreenPos {

	// Resolution of the image.
	private final int yResolution = 240;
	private final int xResolution = 320;
	
	private int xPos;
	private int yPos;

	/**
	 * Constructor
	 * @param xPos The X position of the coordinate.
	 * @param yPos The Y position of the coordinate.
	 */
	public ScreenPos(int xPos, int yPos) throws CoordinateOutOfBoundsError {
		if(coordValid(xPos,'x') && coordValid(yPos,'y')) {
			this.xPos = xPos;
			this.yPos = yPos;
		}
		else {
			throw new CoordinateOutOfBoundsError("Coordinate out of bounds!");
		}
	}

//	/**
//	 * Alternate Constructor.
//	 * Defaults xPos and yPos to 0.
//	 */
//	public ScreenPos() {
//		this.xPos = 0;
//		this.yPos = 0;
//	}

	/**
	 * @param xPos The X position of the coordinate.
	 */
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * @param yPos The Y position of the coordinate.
	 */
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}

	/**
	 * @return The X position of the coordinate.
	 */
	public double getXPos() {
		return xPos;
	}

	/**
	 * @return The Y position of the coordinate.
	 */
	public double getYPos() {
		return yPos;
	}

	/**
	 * Checks if given position is valid on the coordinate plane
	 * @param pos Position to be checked
	 * @param axis Axis the Position is located.
	 * @return If the position is valid
	 */
	private boolean coordValid(int pos, char axis) {
		if (axis == 'x') {
			return (pos >= 0 && pos <= xResolution - 1);
		}
		else if (axis == 'y') {
			return (pos >= 0 && pos <= yResolution - 1);
		}
		else {
			return false;
		}
	}
}
