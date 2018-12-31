package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.helper.AutoBot;
import org.firstinspires.ftc.teamcode.helper.SamplingOrderDetector;

@Autonomous(group = "Doge Auto")
public class DogeAuto extends OpMode {

    AutoBot katBot;
    int step;

    @Override
    public void init() {
        katBot = new AutoBot(hardwareMap, telemetry);
        katBot.setUpWheels();
        katBot.setDogeCV();
        katBot.resetEncoder();
        setStep(0);
    }

    @Override
    public void loop() {
        katBot.phoneServo.setPosition(0.7);
        SamplingOrderDetector.GoldLocation pos = katBot.detectorsam.getCurrentOrder();
        if(step == 0){
            if(pos.equals(SamplingOrderDetector.GoldLocation.CENTER)){
                katBot.forward(24, 0.5);
                setStep(1);
                if(step == 1){
                    katBot.phoneServo.setPosition(0.2);
                    setStep(2);
                }
                if(step == 2){
                    katBot.backward(16, 0.5);
                    setStep(3);
                }
                if(step == 3){
                    katBot.rotate("Right", 45, 0.5);
                    setStep(4);
                }
                if(step == 4){
                    katBot.forward(40, 0.5);
                }
            }
            else if(pos.equals(SamplingOrderDetector.GoldLocation.LEFT)){
                rotateLeft();
                setStep(1);
                if(step == 1){
                    katBot.forward(12, 0.5);
                    setStep(2);
                }
                if(step == 2){
                    katBot.rotateRight(katBot.encoderForTopLeft);
                    setStep(3);
                }
                if(step == 3){
                    katBot.forward(12, 0.5);
                    setStep(4);
                }
                if(step == 4){
                    katBot.phoneServo.setPosition(0.2);
                }
            }
            else if(pos.equals(SamplingOrderDetector.GoldLocation.RIGHT)){
                rotateRight();
                setStep(1);
                if(step == 1){
                    katBot.forward(12, 0.5);
                    setStep(2);
                }
                if(step == 2){
                    katBot.rotateLeft(katBot.encoderForTopLeft);
                    setStep(3);
                }
                if(step == 3){
                    katBot.forward(12, 0.5);
                    setStep(4);
                }
                if(step == 4){
                    katBot.phoneServo.setPosition(0.2);
                }
            }
        }
    }

    @Override
    public void stop() {
        katBot.detectorsam.disable();
    }

    public void rotateLeft() {
        if (!katBot.detectorsam.getAligned()) {
            katBot.topLeft.setPower(0.02);
            katBot.botRight.setPower(0.02);
            katBot.topRight.setPower(0.02);
            katBot.botLeft.setPower(0.02);
            katBot.isMoving = true;
            katBot.encoderForTopLeft = katBot.topLeft.getCurrentPosition();
        } else {
            stop();
        }
    }

    public void rotateRight() {
        if (!katBot.detectorsam.getAligned()) {
            katBot.topLeft.setPower(-0.02);
            katBot.botRight.setPower(-0.02);
            katBot.topRight.setPower(-0.02);
            katBot.botLeft.setPower(-0.02);
            katBot.isMoving = true;
            katBot.encoderForTopLeft = katBot.topLeft.getCurrentPosition();
        } else {
            stop();
        }
    }

    public void setStep(int x){
        if (!katBot.topLeft.isBusy())
            step = x;
    }
}
