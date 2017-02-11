package org.usfirst.frc.team4859.robot.subsystems;

import org.usfirst.frc.team4859.robot.Robot;
import org.usfirst.frc.team4859.robot.RobotMap;
import org.usfirst.frc.team4859.robot.ThrottleLookup.ThrottleLookup;
import org.usfirst.frc.team4859.robot.commands.DriveWithJoystick;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Chassis extends Subsystem {
	// Creating and setting motors
	public static CANTalon motorChassisFrontLeft = new CANTalon(RobotMap.talonIDChassisFrontLeft);
	public static CANTalon motorChassisFrontRight = new CANTalon(RobotMap.talonIDChassisFrontRight);
	
	public static CANTalon motorChassisBackLeft = new CANTalon(RobotMap.talonIDChassisBackLeft);
	public static CANTalon motorChassisBackRight = new CANTalon(RobotMap.talonIDChassisBackRight);
	
	public static DigitalOutput lightStrip = new DigitalOutput(0);

	// Creates robot drive configuration with four motors
	public static RobotDrive chassisDrive = new RobotDrive(motorChassisFrontLeft, motorChassisBackLeft, motorChassisFrontRight, motorChassisBackRight);
	
	public Chassis() {
		chassisDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		chassisDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		// Set a timeout for the motors (0.1 seconds)
		chassisDrive.setSafetyEnabled(true);
		chassisDrive.setExpiration(1);
	}
	
	public void initDefaultCommand () {
		setDefaultCommand(new DriveWithJoystick());
	}
	
	public void driveWithJoystick(Joystick joystickP0) {
		// Get raw values from joystick controller
		double y = joystickP0.getY();
		double x = joystickP0.getX();
		double twist = joystickP0.getTwist();
		
		SmartDashboard.putNumber("Y input", y);
		SmartDashboard.putNumber("X input", x);
		SmartDashboard.putNumber("Twist input", twist);
		
		// Apply translations to the values from the controller
		y = (RobotMap.pMode) ? ThrottleLookup.calcJoystickCorrection("SlowY", y) : ThrottleLookup.calcJoystickCorrection("NormY", y);
		x = (RobotMap.pMode) ? ThrottleLookup.calcJoystickCorrection("SlowX", x) : ThrottleLookup.calcJoystickCorrection("NormX", x);
		twist = (RobotMap.pMode) ? ThrottleLookup.calcJoystickCorrection("SlowT", twist) : ThrottleLookup.calcJoystickCorrection("NormT", twist);
		
		// Apply flip if the flip button is toggled
		if (RobotMap.fMode) {
			y *= -1;
			x *= -1;
		}
		
		//final joystick value adjustments
		x *= RobotMap.xAxisScale;
		y *= RobotMap.yAxisScale;
		twist *= RobotMap.twistScale;
		
		SmartDashboard.putString("Robot Mode", (RobotMap.pMode) ? "Slow" : "Normal");	
		
		SmartDashboard.putNumber("Y output", y);
		SmartDashboard.putNumber("X output", x);
		SmartDashboard.putNumber("Twist output", twist);
		
		chassisDrive.mecanumDrive_Cartesian(x, y, twist, 0);
	}
	
	public void driveStraight(double inputSpeed)
	{
		inputSpeed = inputSpeed * 500;
		
//		chassisDrive.mecanumDrive_Cartesian(0, inputSpeed, 0, 0);
		motorChassisFrontLeft.set(inputSpeed);
		motorChassisFrontRight.set(inputSpeed);
		motorChassisBackLeft.set(inputSpeed);
		motorChassisBackRight.set(inputSpeed);
	}
	
	public void driveStraightGyro(double inputSpeed) {
		double twist = -(Robot.ahrs.getYaw()%360)*5;
		inputSpeed = inputSpeed * 500;
		
		motorChassisFrontLeft.set(inputSpeed + twist);
		motorChassisFrontRight.set(inputSpeed - twist);
		motorChassisBackLeft.set(inputSpeed + twist);
		motorChassisBackRight.set(inputSpeed - twist);
	}
	
	public void driveBackwards(double inputSpeed) {
		inputSpeed = inputSpeed * 500;
		
		motorChassisFrontLeft.set(-inputSpeed);
		motorChassisFrontRight.set(-inputSpeed);
		motorChassisBackLeft.set(-inputSpeed);
		motorChassisBackRight.set(-inputSpeed);
	}
	
	public void strafeLeft(double inputSpeed) {
		inputSpeed = inputSpeed * 500;
		
		motorChassisFrontLeft.set(-inputSpeed);
		motorChassisFrontRight.set(inputSpeed);
		motorChassisBackLeft.set(inputSpeed);
		motorChassisBackRight.set(-inputSpeed);
	}
	
	public void strafeRight(double inputSpeed) {
		inputSpeed = inputSpeed * 500;
		
		motorChassisFrontLeft.set(inputSpeed);
		motorChassisFrontRight.set(-inputSpeed);
		motorChassisBackLeft.set(-inputSpeed);
		motorChassisBackRight.set(inputSpeed);
	}
	
	public void driveStop() {
		motorChassisFrontLeft.set(0);
		motorChassisFrontRight.set(0);
		motorChassisBackLeft.set(0);
		motorChassisBackRight.set(0);
	}
	
	public void turnToAngle(double targetAngle) {
		double sensorAngle = Robot.ahrs.getYaw() % 360;
		double turn = (targetAngle-sensorAngle)*2;
		//if (sensorAngle < 2) { turn *= (1/sensorAngle)*20; }
		//if ((sensorAngle < 2) && (turn > 50)) { turn = 20; }
		
		SmartDashboard.putNumber("anglemod", Robot.ahrs.getYaw()%360);
		SmartDashboard.putNumber("turnspd", turn);
		
		motorChassisFrontLeft.set(turn);
		motorChassisFrontRight.set(-turn);
		motorChassisBackLeft.set(turn);
		motorChassisBackRight.set(-turn);
	}
	
	public void circleTurn(double speed, double ratio) {
		speed *= 500;
		
		motorChassisFrontLeft.set(speed);
		motorChassisFrontRight.set(speed*ratio);
		motorChassisBackLeft.set(speed);
		motorChassisBackRight.set(speed*ratio);
	}
}