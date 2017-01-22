package org.usfirst.frc.team4859.robot.autonomous;

import org.usfirst.frc.team4859.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class DriveBackwards extends Command {

	private double time;
	private double speed;
	
    public DriveBackwards(double inputSpeed, double inputTime) {
        requires(Robot.chassis);
        
        speed = inputSpeed;
        time = inputTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.driveBackwards(speed);
    	setTimeout(time);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.chassis.driveBackwards(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.driveStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.chassis.driveStop();
    }
}