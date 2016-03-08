package org.usfirst.frc.team4859.robot.autonomous;

import org.usfirst.frc.team4859.robot.Robot;
import org.usfirst.frc.team4859.robot.commands.FeedStop;
import org.usfirst.frc.team4859.robot.commands.FlywheelStop;
import org.usfirst.frc.team4859.robot.commands.PivotDown;
import org.usfirst.frc.team4859.robot.commands.PivotFlat;
import org.usfirst.frc.team4859.robot.commands.PivotStop;
import org.usfirst.frc.team4859.robot.subsystems.Chassis;

import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LowBarAndGoal extends CommandGroup {
	
    public  LowBarAndGoal() {
		Chassis.motorChassisRight.changeControlMode(TalonControlMode.PercentVbus);
		Chassis.motorChassisLeft.changeControlMode(TalonControlMode.PercentVbus);
		
    	addSequential(new DriveStop(0));
    	addSequential(new PivotDownTime(1));
    	addSequential(new DriveStraightGyro(0.6, 5));
    	addSequential(new DriveStraightGyro(0.6, 2.02));
    	//addSequential(new DriveToDistance(0.6, 25));
    	addSequential(new TurnToAngle(52, 1.5));
    	addSequential(new DriveStop(0));
//    	addSequential(new DriveToDistance(0.6, 70));
    	addParallel(new PivotFlat());
        addParallel(new FlywheelForwardTime(3.75));
    	addSequential(new DriveStraightGyro(0.6, 1.75));
    	

        addParallel(new FlywheelForwardTime(5));
        addSequential(new FlywheelFeedOutTime(5));
        
        addParallel(new PivotStop());
        addParallel(new FlywheelStop());
        addSequential(new FeedStop());
        
//      addSequential(new DriveStraightGyro(-0.6, 1));
//      addSequential(new TurnToAngle(210, 2));
//      addSequential(new DriveToDistance(0.6, 30, true));
//      addSequential(new TurnToAngle(-90, 2));
//      addSequential(new DriveStraightGyro(0.6, 1));
        
    }
}