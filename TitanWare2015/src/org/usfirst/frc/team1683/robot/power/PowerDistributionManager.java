package org.usfirst.frc.team1683.robot.power;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PowerDistributionManager {
	
	PowerDistributionPanel powerDistPanel;
	int[] channelsToWatch;
	
	public PowerDistributionManager() {
		
	}
	
	public PowerDistributionManager(int[] channelsToWatch) {
		powerDistPanel = new PowerDistributionPanel();
		this.channelsToWatch = channelsToWatch;
	}

}
