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
            armMotor2.setPower(-1);
        }
        else if(b){
            armMotor2.setPower(1);
        }
        */
        if(left){
            armMotor1.setPower(1);
            armMotor2.setPower(1);
        }
        else if(right){
            armMotor1.setPower(-1);
            armMotor2.setPower(-1);
        }
        else{
            armMotor1.setPower(0);
            armMotor2.setPower(0);
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

    public double getArmMotorPower(){
        return armMotor1.getPower();
    }

    //scoring mechanism TBI
}
