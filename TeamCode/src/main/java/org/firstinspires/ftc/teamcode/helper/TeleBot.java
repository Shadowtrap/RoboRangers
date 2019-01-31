package org.firstinspires.ftc.teamcode.helper;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TeleBot extends Robot {

    public TeleBot(HardwareMap hardwareMap, Telemetry tele) {
        super(hardwareMap, tele);
    }

    public boolean moving1 =  false, moving2 = false, moving3 = false, moving4 = false, moving5 = false;

    public void scoreMechMovement(Gamepad g){

        //Elbow: -246 , -215

        boolean buttonA = g.a;
        /*
        boolean  buttonB = g.b;
        boolean  buttonX = g.x;
        boolean  buttonY = g.y;
        boolean  buttonA = g.a;


        if(buttonB||moving1){
            if(elbowMotor.getCurrentPosition() > -246){
                elbowMotor.setPower(-0.7);
                moving1 = true;
            }
            else {
                elbowMotor.setPower(0);
                moving1 = false;
            }
        }
        else if(buttonY||moving2||moving3){

            if(elbowMotor.getCurrentPosition() < -215){
                elbowMotor.setPower(0.3);
                moving2 = true;
            }
            else{
                elbowMotor.setPower(0);
                moving2 = false;
            }

            if(baseMotor.getCurrentPosition() > -550){
                baseMotor.setPower(-0.3);
                moving3 = true;
            }
            else{
                baseMotor.setPower(0);
                moving3 = false;
            }
        }

        else if(buttonX||moving4||moving5){

            if(elbowMotor.getCurrentPosition() > 0){
                elbowMotor.setPower(-0.7);
                moving4 = true;
            }
            else{
                elbowMotor.setPower(0);
                moving4 = false;
            }//i get that part but im just, wait give a sec to read over this again

            if(baseMotor.getCurrentPosition() < 0){
                baseMotor.setPower(0.3);
                moving5 = true;
            }
            else{
                baseMotor.setPower(0);
                moving5 = false;
            }
        }


        */

        //Non Encoder Movement
        float rightT = g.right_trigger;
        float leftT = g.left_trigger;
        boolean rightB = g.right_bumper;
        boolean leftB = g.left_bumper;

        if(rightT > 0.35){
            elbowMotor.setPower(0.4);
        }
        else if(leftT > 0.35){
            elbowMotor.setPower(-0.4);
        }
        else{
            elbowMotor.setPower(0);
        }

        if(rightB){
            baseMotor.setPower(0.3);
        }
        else if(leftB){
            baseMotor.setPower(-0.3);
        }
        else {
            baseMotor.setPower(0);
        }
        if(buttonA){
            baseMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            baseMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elbowMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            elbowMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
    public void liftMechMovement(Gamepad g){
        boolean up = g.dpad_up;
        boolean down = g.dpad_down;
        if(up){
            liftMotor.setPower(.75);
        }
        else if(down){
            liftMotor.setPower(-.75);
        }
        else{
            liftMotor.setPower(0);
        }
    }

    public void goldAndSilverMove(Gamepad g) {
        boolean buttonA = g.a;
        if (buttonA) {
            goldServo.setPosition(0);
            silverServo.setPosition(0);
        } else {
            goldServo.setPosition(0.5);
            silverServo.setPosition(0.5);
        }
    }

    public void phoneServoMovement(Gamepad g){
        boolean dpadleft = g.dpad_left;
        if(dpadleft){
            phoneServo.setPosition(0);
        }
        else{
            phoneServo.setPosition(0.5);
        }
    }

    public void intakeMovement(Gamepad g){
        boolean dpadright = g.dpad_right;
        if(dpadright){
            intakeServo.setPosition(-1);
        }
        else{
            intakeServo.setPosition(1);
        }
    }
    public void wristMovement(Gamepad g){

    }
}
