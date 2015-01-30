package org.usfirst.frc.team1683.robot.test;

import org.usfirst.frc.team1683.robot.vision.Blob;
import org.usfirst.frc.team1683.robot.vision.Vision;

import edu.wpi.first.wpilibj.networktables.NetworkTable;


public class VisionTest implements Tester{
	
	Vision vision;
	NetworkTable table;
	Blob blobs;
	
	public VisionTest() {
		vision = new Vision();
		table = NetworkTable.getTable("Vision");
		blobs = new Blob(table);
	}
	
	public void test() {
		blobs.getCenter();
	}
}
