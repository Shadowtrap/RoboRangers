package org.firstinspires.ftc.teamcode.helper;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.helper.Power;
import org.firstinspires.ftc.teamcode.helper.Power;

public class Driver {

    //A Useless class...

    public Gamepad gamepad;

    public Driver(Gamepad gamepad){
        this.gamepad = gamepad;
    }

    public Power getPowerDriver(){
        return new Power(gamepad.left_stick_x,gamepad.left_stick_y,gamepad.right_stick_x);
    }
}
