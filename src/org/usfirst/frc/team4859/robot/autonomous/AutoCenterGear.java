package org.usfirst.frc.team4859.robot.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCenterGear extends CommandGroup {
	
    public  AutoCenterGear() {
    	addSequential(new DriveStraight(0.1, 1.5));
    }
}