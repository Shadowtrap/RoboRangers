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

    //Motor for lift
    public DcMotor liftMotor;

    //Servo for Arm
    public Servo wristServo;
    public Servo intakeServo;

    //Motor for scoring
    public DcMotor baseMotor;
    public DcMotor elbowMotor;

    public Servo phoneServo;

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
    public void setUpLift(){
        try{
            liftMotor = hwm.get(DcMotor.class, "liftMotor");
            liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("liftMotor : OK");
        }
        catch(Exception e){
            Display("liftMotor is broken :(");
        }
    }

    public void setUpPhoneServo(){
        try{
            phoneServo = hwm.get(Servo.class, "phoneServo");
            phoneServo.setPosition(0);
            Display("phoneServo : OK");
        }
        catch(Exception e){
            Display("phoneServo is broken :(");
        }
    }

    public void setUpScoreMech(){
        try{
            baseMotor = hwm.get(DcMotor.class, "baseMotor");
            baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            baseMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("baseMotor : OK");
        }
        catch(Exception e){
            Display("baseMotor is broken :(");
        }

        try{
            elbowMotor = hwm.get(DcMotor.class, "elbowMotor");
            elbowMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            elbowMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("elbowMotor : OK");
        }
        catch(Exception e){
            Display("elbowMotor is broken :(");
        }

        try{
            wristServo = hwm.get(Servo.class, "wristServo");
            wristServo.setPosition(0);
            Display("wristServo : OK");
        }
        catch(Exception e){
            Display("wristServo is broken :(");
        }
        try{
            intakeServo = hwm.get(Servo.class, "intakeServo");
            intakeServo.setPosition(0);
            Display("intakeServo : OK");
        }
        catch(Exception e){
            Display("intakeServo is broken :(");
        }
    }



    public void move(Power power) {
        topLeft.setPower(power.topLeft);
        topRight.setPower(power.topRight);
        botLeft.setPower(power.botLeft);
        botRight.setPower(power.botRight);
    }
}
