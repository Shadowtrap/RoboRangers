package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.helper.AutoBot;

@Autonomous(group = "Crater Auto")
public class CraterAuto extends OpMode {

    AutoBot arnieBot;
    int step;

    @Override
    public void init() {
        arnieBot =  new AutoBot(hardwareMap, telemetry);
        setStep(0);
        arnieBot.setUpWheels();
        arnieBot.resetEncoder();
        arnieBot.setupTensorCV();

    }

    @Override
    public void loop() {
        if(step == 0) {
            arnieBot.phoneServo.setPosition(0.7);
            arnieBot.detectTensor();
            if (arnieBot.pos == 0) {
                arnieBot.forward(24, 0.5);
                setStep(1);
            }
            else if(arnieBot.pos == -1){
                arnieBot.rotateLeft();
                setStep(1);
                if(step == 1){
                    arnieBot.forward(12, 0.5);
                    setStep(2);
                }
                if(step == 2){
                    arnieBot.rotateRight(arnieBot.encoderForTopLeft);
                    setStep(3);
                }
                if(step == 3){
                    arnieBot.forward(12, 0.5);
                }
            }
            else if(arnieBot.pos == 1){
                arnieBot.rotateRight();
                setStep(1);
                if(step == 1){
                    arnieBot.forward(12, 0.5);
                    setStep(2);
                }
                if(step == 2){
                    arnieBot.rotateLeft(arnieBot.encoderForTopLeft);
                    setStep(3);
                }
                if(step == 3){
                    arnieBot.forward(12, 0.5);
                }
            }
        }
    }

    @Override
    public void stop()
    {
        arnieBot.tfod.shutdown();
    }

    public void setStep(int x){
        if (!arnieBot.topLeft.isBusy())
            step = x;
    }
}
