package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.helper.AutoBot;
import org.firstinspires.ftc.teamcode.helper.SamplingOrderDetector;


@Autonomous(name="DogeAutoCrater")

public class DogeAuto extends OpMode{
    private AutoBot shamsBot;
    public static int step = 0;
    SamplingOrderDetector.GoldLocation pos1;

    @Override
    public void init() {
        shamsBot = new AutoBot(hardwareMap, telemetry);
        shamsBot.setUpWheels();
        shamsBot.resetEncoder();
        shamsBot.setupliftmotor();
        shamsBot.setDogeCV();
        telemetry.addData("TopLeft Encoder", shamsBot.topLeft.getCurrentPosition());
        step =0;
        pos1=SamplingOrderDetector.GoldLocation.UNKNOWN;

    }

    /*
     * Code to run REPEATEDLY when the driver hits INIT
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        //Resetting the encoder value for the latch when starting the OpMode
        shamsBot.liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shamsBot.liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    //* Code to run REPEATEDLY when the driver hits PLAY

    @Override
    public void loop()
    {
        shamsBot.detectorsam.enable();
        //Displaying the En+coder Values nad Step(Debugging)
        telemetry.addData("TopLeft Encoder", shamsBot.topLeft.getCurrentPosition());
        telemetry.addData("TopRight Encoder", shamsBot.topRight.getCurrentPosition());
        telemetry.addData("BottomLeft Encoder", shamsBot.botLeft.getCurrentPosition());
        telemetry.addData("BottomRight Encoder", shamsBot.botRight.getCurrentPosition());
        telemetry.addData("liftMotor Encoder", shamsBot.liftMotor.getCurrentPosition());
        telemetry.addData("Step", step);

        //Start Detecting with Tensor

        //Latching Down & Aligning
        if (step == 0){
            shamsBot.latchdown();
        }
        else if(step == 1) {
            shamsBot.backward(4,0.3);
        }
        else if(step == 2){
            shamsBot.strafeleft(10, 0.3);
        }
        else if(step == 3) {
            shamsBot.forward(3.0,0.3);
        }
        else if(step == 4) {
            shamsBot.rotate("Right",90,0.3);
        }
        else if(step == 5){
            shamsBot.backward(5, 0.3);
        }
        else if(step == 6) {
            shamsBot.forward(0,0);
        }
        //Middle
        else if(step >= 7  && pos1.equals(SamplingOrderDetector.GoldLocation.CENTER)) {
            if(step == 7) {
                shamsBot.forward(50, 0.5);
            }

        }
        //Right
        else if(step >= 7 && pos1.equals(SamplingOrderDetector.GoldLocation.RIGHT)){
            if(step == 7) {
                rotateRight();
            }
            else if(step == 8){
                shamsBot.forward(25,0.5);
            }
            else if(step == 9){
                shamsBot.rotate("Left",100,0.5);
            }
            else if(step == 10){
                shamsBot.forward(50,0.5);
            }

        }
        //left
        else if(step >= 7 &&pos1.equals((SamplingOrderDetector.GoldLocation.LEFT))) {
            if(step == 7) {
                rotateLeft();
            }
            else if(step == 8){
                shamsBot.forward(25,0.5);
            }
            else if(step == 9){
                shamsBot.rotate("Right",100,0.5);
            }
            else if(step == 10){
                shamsBot.forward(50,0.5);
            }

        }
        else
        {
            telemetry.addLine("Nothing found");
            telemetry.addLine(shamsBot.pos+"");
        }
    }

    //Debugging
    /*
    @Override
    public void loop(){
        if(step == 0) {
            shamsBot.strafeleft(10, 1.0);
        }
        else if(step == 1)
        {
            shamsBot.rotate("Right",65,0.5);
            //shamsBot.topLeft.setPower(-0.5);
            //shamsBot.botRight.setPower(-0.5);
            //shamsBot.topRight.setPower(-0.5);
            //shamsBot.botLeft.setPower(-0.5);
        }
    }
    */

    @Override
    public void stop()
    {
        // Disable the detector
        shamsBot.tfod.shutdown();
    }

    public void rotateLeft() {
        if (!shamsBot.detectorsam.getAligned()) {
            shamsBot.topLeft.setPower(0.02);
            shamsBot.botRight.setPower(0.02);
            shamsBot.topRight.setPower(0.02);
            shamsBot.botLeft.setPower(0.02);
            shamsBot.isMoving = true;
            shamsBot.encoderForTopLeft = shamsBot.topLeft.getCurrentPosition();
        } else {
            stop();
        }
    }

    public void rotateRight() {
        if (!shamsBot.detectorsam.getAligned()) {
            shamsBot.topLeft.setPower(-0.02);
            shamsBot.botRight.setPower(-0.02);
            shamsBot.topRight.setPower(-0.02);
            shamsBot.botLeft.setPower(-0.02);
            shamsBot.isMoving = true;
            shamsBot.encoderForTopLeft = shamsBot.topLeft.getCurrentPosition();
        } else {
            stop();
        }
    }
}
