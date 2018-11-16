package org.firstinspires.ftc.teamcode.helper;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.helper.Power;

public class Robot {
    
    public final HardwareMap hwm;
    public final Telemetry t;
    
    //Motors for Wheels
    public DcMotor topLeft;
    public DcMotor topRight;
    public DcMotor botLeft;
    public DcMotor botRight;

    //Motor for Arm
	public DcMotor armMotor1;
	public DcMotor armMotor2;

    //Servo for Arm
    public Servo armServo;

    //Motor for scoring
    public DcMotor scoreMotor;

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

	//Code for armServo
    public void setUpArmServo(){
        Display("Setting up the Servo");
        try {
            armServo = hwm.get(Servo.class, "armServo");
            //armServo.setPosition(0);
            Display("armServo : OK ");
        }
        catch(Exception e) {
            Display("armServo is broken");
        }
    }

    public void setUpScoreMotor(){
        try {
            botRight = hwm.get(DcMotor.class, "scoreMotor");
            botRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            botRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("scoremotor : OK");
        } catch (Exception e) {
            Display("scoremotor : ERROR");
        }
    }

    public void move(Power power) {
        topLeft.setPower(power.topLeft);
        topRight.setPower(power.topRight);
        botLeft.setPower(power.botLeft);
        botRight.setPower(power.botRight);
    }

    public void armServoMovement(Gamepad gamepad1){
        boolean up = gamepad1.dpad_up;
        boolean down = gamepad1.dpad_down;
        if(up) {
            armServo.setPosition(0.0);
        }
        else if(down){
            armServo.setPosition(1.0);
        }
        else{
            armServo.setPosition(0.5);
        }
    }
}
