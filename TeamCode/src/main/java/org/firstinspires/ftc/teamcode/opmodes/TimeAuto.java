package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.helper.AutoBot;


@Autonomous(group = "Timed Auto")
public class TimeAuto extends OpMode {

    AutoBot tamBot;
    ElapsedTime time;
    int step;

    @Override
    public void init() {
        tamBot = new AutoBot(hardwareMap, telemetry);
        setStep(0);
        tamBot.setUpWheels();
        tamBot.setupTensorCV();
        time = new ElapsedTime();
        time.reset();
    }

    @Override
    public void loop() {
        time.startTime();
        tamBot.detectTensor();
        if(step == 0){
            tamBot.phoneServo.setPosition(0.7);
            if(tamBot.pos == 0){
                forwardTime(3);
                setStep(1);
                if(step == 1){
                    tamBot.phoneServo.setPosition(0.2);
                    setStep(2);
                }
                if(step == 2){
                    backwardTime(1);
                    setStep(3);
                }
                if(step == 3){
                    rotateLeftTime(5);
                    setStep(4);
                }
                if(step == 4){
                    forwardTime(6);
                }
            }
            else if(tamBot.pos == -1){
                rotateLeftTime(1);
                setStep(1);
                if(step == 1){
                    forwardTime(2.5);
                    setStep(2);
                }
                if(step == 2){
                    rotateRightTime(1);
                    setStep(3);
                }
                if(step == 3){
                    forwardTime(1.5);
                    setStep(4);
                }
                if(step == 4){
                    tamBot.phoneServo.setPosition(0.2);
                }
            }
            else if(tamBot.pos == 1){
                rotateRightTime(1);
                setStep(1);
                if(step == 1){
                    forwardTime(2.5);
                    setStep(2);
                }
                if(step == 2){
                    rotateLeftTime(1);
                    setStep(3);
                }
                if(step == 3){
                    forwardTime(1.5);
                    setStep(4);
                }
                if(step == 4){
                    tamBot.phoneServo.setPosition(0.2);
                }
            }
        }
    }
    @Override
    public void stop()
    {
        tamBot.tfod.shutdown();
    }

    //Movement with time

    public void forwardTime(double time){
        this.time.reset();
        this.time.startTime();
        if(this.time.seconds() < time){
            tamBot.topLeft.setPower(-0.5);
            tamBot.botLeft.setPower(-0.5);
            tamBot.topRight.setPower(0.5);
            tamBot.botRight.setPower(0.5);
         }
         else{
            stopMotor();
        }
    }
    public void backwardTime(double time){
        this.time.reset();
        this.time.startTime();
        if(this.time.seconds() < time){
            tamBot.topLeft.setPower(0.5);
            tamBot.botLeft.setPower(0.5);
            tamBot.topRight.setPower(-0.5);
            tamBot.botRight.setPower(-0.5);
        }
        else{
            stopMotor();
        }
    }
    public void rotateLeftTime(double time){
        this.time.reset();
        this.time.startTime();
        if(this.time.seconds() < time){
            tamBot.topLeft.setPower(0.02);
            tamBot.botLeft.setPower(0.02);
            tamBot.topRight.setPower(0.02);
            tamBot.botRight.setPower(0.02);
        }
        else{
            stopMotor();
        }
    }
    public void rotateRightTime(double time){
        this.time.reset();
        this.time.startTime();
        if(this.time.seconds() < time){
            tamBot.topLeft.setPower(-0.02);
            tamBot.botLeft.setPower(-0.02);
            tamBot.topRight.setPower(-0.02);
            tamBot.botRight.setPower(-0.02);
        }
        else{
            stopMotor();
        }
    }

    public void stopMotor(){
        this.time.reset();
        this.time.startTime();
        if(time.seconds() < 0.5) {
            tamBot.topLeft.setPower(0);
            tamBot.botLeft.setPower(0);
            tamBot.topRight.setPower(0);
            tamBot.botRight.setPower(0);
        }
    }

    public void setStep(int x){
        if (!tamBot.topLeft.isBusy())
            step = x;
    }
}
