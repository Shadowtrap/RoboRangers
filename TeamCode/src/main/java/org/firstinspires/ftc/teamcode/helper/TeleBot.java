package org.firstinspires.ftc.teamcode.helper;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TeleBot extends Robot {

    public TeleBot(HardwareMap hardwareMap, Telemetry tele) {
        super(hardwareMap, tele);
    }

    //latching mechanism

    public void armMotorMovement(Gamepad gamepad1) {
        boolean left = gamepad1.dpad_left;
        boolean right = gamepad1.dpad_right;
        //boolean x = gamepad1.x;
        //boolean b = gamepad1.b;
        /*
        if(x){
            armMotor2.setPower(-0.2);
        }
        else if(b){
            armMotor2.setPower(0.2);
        }
        */
        if(left){
            armMotor1.setPower(0.5);
           // armMotor2.setPower(1);
        }
        else if(right){
            armMotor1.setPower(-0.5);
            //armMotor2.setPower(-1);
        }
        else{
            armMotor1.setPower(0);
           // armMotor2.setPower(0);
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

    //scoring mechanism TBI

    public void scoreMech(Gamepad gamepad1){
        //boolean rb = gamepad1.right_bumper;
       // boolean lb = gamepad1.left_bumper;

        boolean y = gamepad1.y;
        boolean a = gamepad1.a;

        boolean x = gamepad1.x;
        boolean b = gamepad1.b;

        boolean lb = gamepad1.left_bumper;
        boolean rb = gamepad1.right_bumper;

        if(!raiseMotor.equals(null)){
            if(y){
                raiseMotor.setPower(1);
                r2.setPower(-1);
            }
            else if(a){
                raiseMotor.setPower(-1);
                r2.setPower(1);
            }
            else
            {
                raiseMotor.setPower(0);
                r2.setPower(0);
            }
        }

        if(!extendMotor.equals(null)){
            if(x){
                extendMotor.setPower(-1);
            }
            else if(b){
                extendMotor.setPower(1);
            }
            else
            {
                extendMotor.setPower(0);
            }
        }

        if (!score.equals(null)){
            if(lb){
                score.setPosition(1);
            }
            else if(rb){
                score.setPosition(0);
            }
            else{
                score.setPosition(0.5);
            }
        }

        /*
        if(!right.equals(null)){
            if(rb){
                right.setPosition(-1);
            }
            else{
                right.setPosition(0);
            }
        }

        if(!left.equals(null)){
            if(lb){
                left.setPosition(1);
            }
            else{
                left.setPosition(0);
            }
        }*/


    }
}
