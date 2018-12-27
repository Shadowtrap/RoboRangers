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
            //Center
            if(random == 0){
                markoBot.forward(24, 0.5);
                step = 1;
            }
            //Left
            else if(random == 1){
                markoBot.rotate("Left", 45, 0.02);
                step = 1;
                if(step == 1){
                    markoBot.forward(24, 0.5);
                }
            }
            //Right
            else if (random == 2){
                markoBot.rotate("Right", 45, 0.02);
                step = 1;
                if(step == 1){
                    markoBot.forward(24, 0.5);
                }
            }
        }

    }
}
