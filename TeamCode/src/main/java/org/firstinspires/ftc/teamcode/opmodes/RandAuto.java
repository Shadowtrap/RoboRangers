package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.helper.AutoBot;

@Autonomous(name="Random Auto")

public class RandAuto extends OpMode {

    AutoBot markoBot = new AutoBot(hardwareMap, telemetry);
    int random;
    int step = 0;

    @Override
    public void init() {
        markoBot.setUpWheels();
        random = (int)(Math.random() * 3);
    }

    @Override
    public void loop() {

        if(step == 0){
            markoBot.phoneServo.setPosition(0.7);
            //Center
            if(random == 0){
                markoBot.forward(24, 0.5);
                step = 1;
                if(step == 1){
                    markoBot.phoneServo.setPosition(0.2);
                }
            }
            //Left
            else if(random == 1){
                markoBot.rotate("Left", 45, 0.02);
                step = 1;
                if(step == 1){
                    markoBot.forward(12, 0.5);
                    step = 2;
                }
                if(step == 2){
                    markoBot.rotate("Right", 45, 0.02);
                    step = 3;
                }
                if(step == 3){
                    markoBot.forward(12, 0.5);
                    step = 4;
                }
                if(step == 4){
                    markoBot.phoneServo.setPosition(0.2);
                }
            }
            //Right
            else if (random == 2){
                markoBot.rotate("Right", 45, 0.02);
                step = 1;
                if(step == 1){
                    markoBot.forward(12, 0.5);
                    step = 2;
                }
                if(step == 2){
                    markoBot.rotate("Left", 45, 0.02);
                    step = 3;
                }
                if(step == 3){
                    markoBot.forward(12, 0.5);
                    step = 4;
                }
                if(step == 4){
                    markoBot.phoneServo.setPosition(0.2);
                }
            }
        }
    }
}
