package org.firstinspires.ftc.teamcode.helper;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.helper.SamplingOrderDetector;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoBot extends Robot {
    
    ElapsedTime secound = new ElapsedTime();
    private ElapsedTime time = new ElapsedTime();
    public int counter;
    private HardwareMap hardwaremap;
    public SamplingOrderDetector detectorsam;

    public AutoBot(HardwareMap hardwareMap, Telemetry tele) {
        super(hardwareMap, tele);
    }

    private int once = 1;

    public void setUpDropMotor() {
        Display("Setting up the armMotor");
        try {
            armMotor1 = hwm.get(DcMotor.class, "armMotor1");
            armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            armMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("armMotor1 : OK");
        } catch (Exception e) {
            Display("armMotor1 : ERROR");
        }

        try {
            armMotor2 = hwm.get(DcMotor.class, "armMotor2");
            armMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("armMotor2 : OK");
        } catch (Exception e) {
            Display("armMotor2 : ERROR");
        }
    }

    public void setUpWheels() {
        Display("Setting up the wheels");

        try {
            topLeft = hwm.get(DcMotor.class, "topLeft");
            topLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            topLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("topLeft : OK");
        } catch (Exception e) {
            Display("topLeft : ERROR");
        }

        try {
            topRight = hwm.get(DcMotor.class, "topRight");
            topRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            topRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("topRight : OK");
        } catch (Exception e) {
            Display("topRight : ERROR");
        }

        try {
            botLeft = hwm.get(DcMotor.class, "botLeft");
            botLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            botLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("botLeft : OK");
        } catch (Exception e) {
            Display("botLeft : ERROR");
        }

        try {
            botRight = hwm.get(DcMotor.class, "botRight");
            botRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            botRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("botRight : OK");
        } catch (Exception e) {
            Display("botRight : ERROR");
        }
    }

    public void setDetectors() {
        // Setup detectorsam
        detectorsam = new SamplingOrderDetector(); // Create the detector
        detectorsam.init(hwm.appContext, CameraViewDisplay.getInstance()); // Initialize detector with app context and camera
        detectorsam.useDefaults(); // Set detector to use default settings

        detectorsam.downscale = 0.4; // How much to downscale the input frames

        // Optional tuning
        detectorsam.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detectorsam.maxAreaScorer.weight = 0.001;

        // Optional tuning
        detectorsam.alignSize1 = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detectorsam.alignPosOffset1 = 0; // How far from center frame to offset this alignment zone.
        detectorsam.downscale = 0.4; // How much to downscale the input frames

        detectorsam.ratioScorer.weight = 15;
        detectorsam.ratioScorer.perfectRatio = 1.0;

        detectorsam.enable(); // Start detectorsam
    }

    //Equation for Ticks
    public static double equation(double distance) {
        return (360 / (4 * Math.PI)) * distance;
    }


    //Forward using encoders
    public void forward(double encode) {
        if (topLeft.getCurrentPosition() < encode && topRight.getCurrentPosition() < encode) {
            topLeft.setPower(.5);
            botRight.setPower(-.5);
            topRight.setPower(-.5);
            botLeft.setPower(.5);

        } else {

            stop();
        }
    }

    //Backwards using encoders
    public void backwards(double encode) {
        if (topLeft.getCurrentPosition() < encode && topRight.getCurrentPosition() < encode) {
            topLeft.setPower(-.5);
            botRight.setPower(.5);
            topRight.setPower(.5);
            botLeft.setPower(-.5);

        } else {

            stop();
        }
    }

    //Rotation Left or Right
    public void rotate(int goldPos, int deg) {
        if (goldPos == 1) {
            if (topLeft.getCurrentPosition() > (int) (deg * -22.0555555556)) {
                topLeft.setPower(-.5);
                botRight.setPower(-0.5);
                topRight.setPower(-.5);
                botLeft.setPower(-.5);
            } else {
                topLeft.setPower(0);
                botRight.setPower(0);
                topRight.setPower(0);
                botLeft.setPower(0);
                topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                botLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                botRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                stop();
            }
        } else if (goldPos == -1) {
            if (topLeft.getCurrentPosition() < (int) (deg * 22.0555555556)) {
                topLeft.setPower(.5);
                botRight.setPower(0.5);
                topRight.setPower(.5);
                botLeft.setPower(.5);
            } else {
                topLeft.setPower(0);
                botRight.setPower(0);
                topRight.setPower(0);
                botLeft.setPower(0);
                topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                botLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                botRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                stop();
            }
        } else {
            topLeft.setPower(0);
            botRight.setPower(0);
            topRight.setPower(0);
            botLeft.setPower(0);
            topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            botLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            botRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }



        public void stop()
        {

            if(once==1) {
                time.reset();
                time.startTime();
                once++;
            }

            if(time.milliseconds() < 3000 && once==2)
            {
                topLeft.setPower(0);
                botRight.setPower(0);
                topRight.setPower(0);
                botLeft.setPower(0);

                topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                botLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                botRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            }
            else// if(once==2)
            {//after i stopped
                once = 1;
                counter++;//go to next phase,

                topLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                topRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                botLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                botRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

        }


    //Alginment and Postion
    public void OrderAndAlginment(double distance, double distance1, double distance2, SamplingOrderDetector.GoldLocation goldPosition)
    {
        int counter1 = 1;

        if(goldPosition.equals(SamplingOrderDetector.GoldLocation.CENTER))
        {
            forward(distance);
        }
        else if (goldPosition.equals(SamplingOrderDetector.GoldLocation.LEFT))
        {
            if(detectorsam.getAligned()==false)
            {
                topLeft.setPower(.25);
                botRight.setPower(0.25);
                topRight.setPower(.25);
                botLeft.setPower(.25);
            }
            else {
                  if(counter1==1)
                  {
                      stop();
                      forward(distance1);
                      stop();
                  }
            }
        }
        else if (goldPosition.equals(SamplingOrderDetector.GoldLocation.RIGHT))
        {
            if(detectorsam.getAligned()==false)
            {
                topLeft.setPower(-.25);
                botRight.setPower(-0.25);
                topRight.setPower(-.25);
                botLeft.setPower(-.25);
            }
            else {
                if(counter1==1)
                {
                    stop();
                    forward(distance2);
                    stop();

                }
            }
        }
        else
        {
            stop();
        }
    }
}
