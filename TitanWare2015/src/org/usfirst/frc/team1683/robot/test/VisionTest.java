package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.vision.Vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionTest implements Tester{
	
//	Blob testBlob = new Blob(0);
	Vision vision;
	NetworkTable table;
	
	public VisionTest() {
		vision = new Vision();
		table = NetworkTable.getTable("Vision");
	}
	
	public void test() {
//		Blob testBlob = new Blob(table, 0);
//		testBlob.getCenter();
	}
}
