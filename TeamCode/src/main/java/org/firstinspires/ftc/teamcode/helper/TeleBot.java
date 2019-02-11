package org.firstinspires.ftc.teamcode.helper;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TeleBot extends Robot {

    public TeleBot(HardwareMap hardwareMap, Telemetry tele) {
        super(hardwareMap, tele);
    }

    public boolean moving1, moving2;

    public void scoreMechMovement(Gamepad g){
        boolean xButton = g.x;
        boolean yButton = g.y;
        boolean bButton = g.b;
        boolean aButton = g.a;

        if(xButton&&!moving1&&!moving2){
            if(baseMotor.getCurrentPosition() < 300){
                baseMotor.setPower(0.5);
                moving1 = true;
            }
            else{
                baseMotor.setPower(0);
                moving1 = false;
            }

            if(elbowMotor.getCurrentPosition() > -300){
                elbowMotor.setPower(-0.5);
                moving2 = true;
            }
            else {
                elbowMotor.setPower(0);
                moving2 = false;
            }
            if(!moving1){
                baseMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                baseMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if(!moving2){
                elbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                elbowMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }
        else if(yButton){

        }
        else if(bButton){

        }
        else if(aButton){

        }

    }

    public void liftMechMovement(Gamepad g){
        boolean up = g.dpad_up;
        boolean down = g.dpad_down;
        if(up){
            liftMotor.setPower(1);
        }
        else if(down){
            liftMotor.setPower(-1);
        }
        else{
            liftMotor.setPower(0);
        }
    }

    public void goldAndSilverMove(Gamepad g){
        boolean rightTrigger = g.right_trigger < 0.35;
        if(rightTrigger){
            goldServo.setPosition(0);
            silverServo.setPosition(0);
        }
        else{
            goldServo.setPosition(0.5);
            silverServo.setPosition(0.5);
        }
    }

    public void movingArm(Gamepad g){
        boolean rightTrigger = g.right_trigger > 0.35;
        boolean leftTrigger = g.left_trigger > 0.35;
        if(rightTrigger){
            armServo1.setPosition(1);
            armServo2.setPosition(0);
        }
        else if(leftTrigger){
            armServo1.setPosition(0);
            armServo2.setPosition(1);
        }
        else{
            goldServo.setPosition(0.5);
            silverServo.setPosition(0.5);
        }
    }
}
