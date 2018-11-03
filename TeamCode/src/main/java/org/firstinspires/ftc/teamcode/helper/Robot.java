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
    
    //Motors for Wheels
    private DcMotor topLeft;
    private DcMotor topRight;
    private DcMotor botLeft;
    private DcMotor botRight;

    //Motor for Arm
	private DcMotor armMotor1;
	private DcMotor armMotor2;

    //Servo for Arm
    private Servo armServo;

    public Robot(HardwareMap hardwareMap, Telemetry tele){
        hwm = hardwareMap;
        t = tele;
    }
    
    public void Display(String s){
        t.addLine(s);
    }

    //Code for Wheels
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

    public DcMotor getTopLeft() {
        return topLeft;
    }

    public DcMotor getTopRight() {
        return topRight;
    }

    public DcMotor getBotLeft() {
        return botLeft;
    }

    public DcMotor getBotRight() {
        return botRight;
    }

    //Code for armMotor
	public void setUpArmMotor(){
		Display("Setting up the armMotor");
        try {
            armMotor1 = hwm.get(DcMotor.class, "armMotor1");
            armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            armMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("armMotor1 : OK");
        } catch (Exception e) {
            Display("armMotor1 : ERROR");
        }

        try {
           armMotor2 = hwm.get(DcMotor.class, "armMotor2");
           armMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           armMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("armMotor2 : OK");
        } catch (Exception e) {
            Display("armMotor2 : ERROR");
        }
	}
	
	public void armMotorMovement(Gamepad gamepad1) {
        boolean left = gamepad1.dpad_left;
		boolean right = gamepad1.dpad_right;
        boolean x= gamepad1.x;
        boolean b = gamepad1.b;
		if(x){
		    armMotor2.setPower(-1);
        }
        else if(b){
		    armMotor2.setPower(1);
        }
		else if(left){
			armMotor1.setPower(1);
		}
		else if(right){
			armMotor1.setPower(-1);
		}
		else{
			armMotor1.setPower(0);
			armMotor2.setPower(0);
		}
    }
	
	public double getArmMotorPower(){
		return armMotor1.getPower();
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

    public DcMotor getArmMotor1() {
        return armMotor1;
    }

    public DcMotor getArmMotor2() {
        return armMotor2;
    }

    //Servo Code

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

    public Servo getArmServo() {
        return armServo;
    }
}
