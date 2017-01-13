package org.usfirst.frc.team4859.robot.subsystems;

import org.usfirst.frc.team4859.robot.Robot;
import org.usfirst.frc.team4859.robot.RobotMap;
import org.usfirst.frc.team4859.robot.ThrottleLookup.ThrottleLookup;
import org.usfirst.frc.team4859.robot.commands.DriveWithJoystick;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends Subsystem {
	// Creating and setting motors
	public static CANTalon motor1 = new CANTalon(RobotMap.talonIDMotor1);
	
	public static CANTalon motor2 = new CANTalon(RobotMap.talonIDMotor2);

	// Creates robot drive configuration with four motors
	static RobotDrive chassisDrive = new RobotDrive(motor1, motor2);
	
	public Chassis() {
		super();
		
		motor1.changeControlMode(TalonControlMode.PercentVbus);
		motor2.changeControlMode(TalonControlMode.PercentVbus);
		
		//motorChassisRight.changeControlMode(TalonControlMode.Speed);
		//motorChassisLeft.changeControlMode(TalonControlMode.Speed);
		
		// Set a timeout for the motors (1 seconds)
		chassisDrive.setSafetyEnabled(true);
		chassisDrive.setExpiration(1);
	}
	
	public void initDefaultCommand () {
		setDefaultCommand(new DriveWithJoystick());
	}
	
	public void driveWithJoystick(Joystick joystickP0) {
		// Get raw values from joystick controller
		double yAxis = joystickP0.getY();
		double twist = joystickP0.getTwist();
		
		// Apply translations to the values from the controller
		yAxis = (RobotMap.pMode) ? ThrottleLookup.calcJoystickCorrection("SlowY", yAxis) : ThrottleLookup.calcJoystickCorrection("NormY", yAxis);
		twist = (RobotMap.pMode) ? ThrottleLookup.calcJoystickCorrection("SlowT", twist) : ThrottleLookup.calcJoystickCorrection("NormT", twist);
		
		SmartDashboard.putString("ROBOT MODE", (RobotMap.pMode) ? "Slow" : "Normal");	
				
		SmartDashboard.putNumber("JoystickY", yAxis);
		SmartDashboard.putNumber("JoystickTwist", twist);
		SmartDashboard.putBoolean("Precision Mode", RobotMap.pMode);
		
		chassisDrive.arcadeDrive(yAxis, twist*0.8);
	}
	
	public void DriveStraight(double inputSpeed)
	{
		Chassis.motor1.changeControlMode(TalonControlMode.PercentVbus);
		Chassis.motor2.changeControlMode(TalonControlMode.PercentVbus);
		chassisDrive.arcadeDrive(inputSpeed,0);
	}
	
	public void DriveStraightGyro(double inputSpeed)
	{
		Chassis.motor1.changeControlMode(TalonControlMode.PercentVbus);
		Chassis.motor2.changeControlMode(TalonControlMode.PercentVbus);
//		chassisDrive.arcadeDrive(inputSpeed,Robot.gyro.getAngle()*0.06);
	}
	
	public void DriveBackwards(double inputSpeed){		
		chassisDrive.arcadeDrive(-inputSpeed,0);
	}
	
	public void DriveStop(){
		chassisDrive.arcadeDrive(0,0);
	}
	
	public void DriveLeftCenter(double inputSpeed){
		chassisDrive.arcadeDrive(0,inputSpeed);
	}
	
	public void DriveLeftForwards(double inputSpeed){

		chassisDrive.arcadeDrive(inputSpeed,inputSpeed);
	}
	
	public void DriveLeftBackwards(double inputSpeed){
		chassisDrive.arcadeDrive(-inputSpeed,inputSpeed);
	}
	
	public void DriveRightCenterGyro(double angle){
//		chassisDrive.arcadeDrive(0,(Robot.gyro.getAngle()%360-angle)*0.04);
	}
	
	public void DriveRightForwards(double inputSpeed){
		chassisDrive.arcadeDrive(inputSpeed,-inputSpeed);
		}
	
	public void DriveRightBackwards(double inputSpeed){
		chassisDrive.arcadeDrive(-inputSpeed,-inputSpeed);
		}
}