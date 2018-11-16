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
    public DcMotor raiseMotor;
    public DcMotor extendMotor;

    //servo for score door;
    public Servo left;
    public Servo right;
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
            armServo.setPosition(0);
            Display("armServo : OK");
        }
        catch(Exception e) {
            Display("armServo is broken");
        }
    }

    public void setUpScoreMech(){
        try {
            raiseMotor = hwm.get(DcMotor.class, "raiseMotor");
            raiseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            raiseMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("raisMotor : OK");
        } catch (Exception e) {
            Display("scoremotor : ERROR");
        }
        try {
            extendMotor = hwm.get(DcMotor.class, "extendMotor");
            extendMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("extendMotor : OK");
        } catch (Exception e) {
            Display("extendMotor : ERROR");
        }

        try {
            left = hwm.get(Servo.class, "left");
            left.setPosition(0);
            Display("armServo : OK");
        }
        catch(Exception e) {
            Display("left is broken");
        }
        try {
            right = hwm.get(Servo.class, "right");
            right.setPosition(0);
            Display("right : OK");
        }
        catch(Exception e) {
            Display("right is broken");
        }
    }



    public void move(Power power) {
        topLeft.setPower(power.topLeft);
        topRight.setPower(power.topRight);
        botLeft.setPower(power.botLeft);
        botRight.setPower(power.botRight);
    }
}
