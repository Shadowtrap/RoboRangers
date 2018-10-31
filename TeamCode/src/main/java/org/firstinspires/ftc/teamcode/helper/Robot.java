package org.firstinspires.ftc.teamcode.helper;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.helper.Power;
public class Robot {
    
    private final HardwareMap hwm;
    private final Telemetry t;
    
    //motors for wheels
    private DcMotor topLeft;
    private DcMotor topRight;
    private DcMotor botLeft;
    private DcMotor botRight;
    //Motor for Arm
	private DcMotor armMotor;
    //Servo for Arm
    private Servo armServo;
    
    public Robot(HardwareMap hardwareMap, Telemetry tele){
        hwm = hardwareMap;
        t = tele;
    }
    
    public void Display(String s){
        t.addLine(s);
    }
    
    public void setUpWheels() {
        Display("Setting up the wheels");

        try {
            topLeft = hwm.get(DcMotor.class, "topLeft");
            topLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            topLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("topLeft : OK");
        } catch (Exception e) {
            Display("topLeft : ERROR");
        }

        try {
            topRight = hwm.get(DcMotor.class, "topRight");
            topRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            topRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("topRight : OK");
        } catch (Exception e) {
            Display("topRight : ERROR");
        }

        try {
            botLeft = hwm.get(DcMotor.class, "botLeft");
            botLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            botLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("botLeft : OK");
        } catch (Exception e) {
            Display("botLeft : ERROR");
        }

        try {
            botRight = hwm.get(DcMotor.class, "botRight");
            botRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            botRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("botRight : OK");
        } catch (Exception e) {
            Display("botRight : ERROR");
        }

    }
    
    public void move(Power power) {
        topLeft.setPower(power.topLeft);
        topRight.setPower(power.topRight);
        botLeft.setPower(power.botLeft);
        botRight.setPower(power.botRight);
    }

    //code for armMotor
	public void setUpArmMotor(){
		Display("Setting up the armMotor");
        try {
            armMotor = hwm.get(DcMotor.class, "armMotor");
            armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("armMotor : OK");
        } catch (Exception e) {
            Display("armMotor : ERROR");
        }
	}
	
	public void armMotorMovement(Gamepad gamepad1) {
        boolean left = gamepad1.dpad_left;
		boolean right = gamepad1.dpad_right;
		if(left){
			armMotor.setPower(1);
		}
		else if(right){
			armMotor.setPower(-1);
		}
		else{
			armMotor.setPower(0);
		}
    }
	
	public double getArmMotorPower(){
		return armMotor.getPower();
	}

    //Code for armServo
    public void setUpArmServo(){
        Display("Setting up the Servo");
        try {
            armServo = hwm.get(Servo.class, "armServo");
            armServo.setPosition(0);
            Display("armServo : OK");
        }
        catch(Exception e) {
            Display("armServo is broken");
        }
    }

    public void armServoMovement(Gamepad gamepad1){
		boolean up = gamepad1.dpad_up;
		boolean down = gamepad1.dpad_down;
        if(up) {
            armServo.setPosition(0);
        }
        else if(down){
            armServo.setPosition(1);
        }
		else{
			armServo.setPosition(0.5);
		}

    }

    public double getArmServoPosition(){
        return armServo.getPosition();
    }

}
