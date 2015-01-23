package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.vision.Blob;
import org.usfirst.frc.team1683.robot.vision.Vision;

public class VisionTest implements Tester{
	
//	Blob testBlob = new Blob(0);
	Vision vision;
	
	public VisionTest() {
		vision = new Vision();
	}
	
	public void test() {
		Blob testBlob = new Blob(0);
		testBlob.getCenter();
	}
}
