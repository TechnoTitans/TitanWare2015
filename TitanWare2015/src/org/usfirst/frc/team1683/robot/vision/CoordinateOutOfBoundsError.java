package org.usfirst.frc.team1683.robot.vision;

/**
 * Error thrown when coordinate is outside of resolution of camera.
 * @author David Luo
 *
 */
public class CoordinateOutOfBoundsError extends Error {
	
	/**
	 * I don't actually know what this variable is for.
	 */
	private static final long serialVersionUID = 6701124814662527049L;

	public CoordinateOutOfBoundsError(String message) {
		super(message);
	}
}
