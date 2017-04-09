package org.usfirst.frc.team4859.robot.commands;

import org.usfirst.frc.team4859.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class MotorBackward extends Command {

    public MotorBackward() {
        requires(Robot.motorss);
    }

    protected void initialize() {
    	Robot.motorss.motorBackward();
    }

    protected void execute() {
    	Robot.motorss.motorBackward();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.motorss.motorStop();
    }

    protected void interrupted() {
    	Robot.motorss.motorStop();
    }
}