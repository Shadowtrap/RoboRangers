package org.firstinspires.ftc.teamcode.helper;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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
            Display("pressing left");
        }
        else if(right){
            armMotor1.setPower(-1);
            armMotor2.setPower(-1);
            Display("pressing right");
        }
        else{
            armMotor1.setPower(0);
            armMotor2.setPower(0);
            Display("stop, not pressing left or right");
        }
    }

    public void armServoMovement(Gamepad gamepad1){
        boolean up = gamepad1.dpad_up;
        boolean down = gamepad1.dpad_down;
        if(up)
        {
            Display("Go up:");
            armServo.setPosition(0.0);
        }
        else if(down)
        {
            Display("Go down " );
            armServo.setPosition(1.0);
        }
        Display("Servo pos " + armServo.getPosition());
        /*else
        {
            Display("middle");
            armServo.setPosition(0.5);
        }*/
    }

    //scoring mechanism TBI

    public void armMech(Gamepad gamepad1){
        boolean rb = gamepad1.right_bumper;
        boolean lb = gamepad1.left_bumper;
        if(rb){
            scoreMotor.setPower(.25);
        }
        else if(lb){
            scoreMotor.setPower(-.25);
        }
    }
}
