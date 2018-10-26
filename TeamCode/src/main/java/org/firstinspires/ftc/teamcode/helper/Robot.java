package org.firstinspires.ftc.teamcode.helper;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.helper.Power;
public class Robot {
    
    private final HardwareMap hwm;
    private final Telemetry t;
    
    //motors for wheels
    private DcMotor topLeft;
    private DcMotor topRight;
    private DcMotor botLeft;
    private DcMotor botRight;
    private DcMotor arm;
    
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

    public void setUpArm(){
        try {
            arm = hwm.get(DcMotor.class, "arm");
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Display("arm : OK");
        }
        catch(Exception e) {
            Display("arm: is broken");
        }
    }

    public void armMovement(Power power){
        arm.setPower(power.arm);
    }

}
