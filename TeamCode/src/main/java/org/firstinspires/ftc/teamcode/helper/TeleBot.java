package org.firstinspires.ftc.teamcode.helper;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TeleBot extends Robot {

    public TeleBot(HardwareMap hardwareMap, Telemetry tele) {
        super(hardwareMap, tele);
    }

    //latching mechanism

    public void armMotorMovement(Gamepad g) {

    }

    public void scoreMechMovement(Gamepad g){
    }

    public void liftMechMovement(Gamepad g){

    }
    public void phoneMovement(Gamepad g){
        boolean a = g.a;
        boolean b = g.b;
        if(a){
            phoneServo.setPosition(0);
        }
        if(b){
            phoneServo.setPosition(1);
        }
        else{
            phoneServo.setPosition(0.5);
        }
    }
}
