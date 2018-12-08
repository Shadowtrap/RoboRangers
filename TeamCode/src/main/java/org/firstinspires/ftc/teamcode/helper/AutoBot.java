package org.firstinspires.ftc.teamcode.helper;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraCalibration;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.helper.SamplingOrderDetector;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class AutoBot extends Robot {
    public ElapsedTime time = new ElapsedTime();
    public int counter = 0;
    private HardwareMap hardwaremap;
    public SamplingOrderDetector detectorsam;
    public int once = 1;
    public int pos = 2;
    int counter1 = 1;

    //Vuforia
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "ATMeJeb/////AAAAGaZ47DzTRUyOhcXnfJD+z89ATBWAF+fi+oOutLvXaf0YT/RPuf2mu6VJsJowCDiWiOzGMHUjXKsLBqA4Ziar76oZY/juheUactiQaY6Z3qPHnGmchAMlYuqgKErvggTuqmFca8VvTjtB6YOheJmAbllTDTaCudODpnIDkuFNTa36WCTr4L8HcCnIsB7bjF8pZoivYEBwPkfCVtfAiEpqxbyDAZgNXnuCyp6v/oi3FYZbp7JkFVorcM182+q0PVN5gIr14SKEMlDcDFDiv/sQwNeQOs5iNBd1OSkCoTe9CYbdmtE0gUXxKN2w9NqwATYC6YRJP9uoopxqmr9zkepI10peh2/RnU6pHOmR0KKRAVh8";
    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;
    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    public TFObjectDetector tfod;
    private float focalLength = 0;
    private int distance = -1;
    public boolean alignedTensor = false;

    public AutoBot(HardwareMap hardwareMap, Telemetry tele) {
        super(hardwareMap, tele);
    }

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

    public void setDogeCV() {
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
        return (360 / (Math.PI)) * distance;
    }


    //Forward using encoders
    public void forward(double encode,double pow) {
        if (topLeft.getCurrentPosition() < encode && topRight.getCurrentPosition() < encode && once==1) {
            topLeft.setPower(-pow);
            botRight.setPower(pow);
            topRight.setPower(pow);
            botLeft.setPower(-pow);

        } else {
            counter++;
            stop();
        }
    }

    //Backwards using encoders
    public void backwards(double encode,double pow) {
        if (topLeft.getCurrentPosition() < encode && topRight.getCurrentPosition() < encode && once==1) {
            topLeft.setPower(-pow);
            botRight.setPower(pow);
            topRight.setPower(pow);
            botLeft.setPower(-pow);

        } else {
            counter++;
            stop();
        }
    }

    //Rotation Left or Right
    public void rotate(String str, int deg, double pow) {

        if (str.equals("Right")==true||str.equals("right")==true)
        {
            if (topLeft.getCurrentPosition() > (int) (deg * -22.0555555556) && once == 1)
            {
                topLeft.setPower(-pow);
                botRight.setPower(-pow);
                topRight.setPower(-pow);
                botLeft.setPower(-pow);
            } else {
                stop();
            }
        } else if (str.equals("Left")==true||str.equals("left")==true) {
            if (topLeft.getCurrentPosition() < (int) (deg * 22.0555555556) && once == 1) {
                topLeft.setPower(pow);
                botRight.setPower(pow);
                topRight.setPower(pow);
                botLeft.setPower(pow);
            } else {
                counter++;
                stop();
            }
        }
        else {
            stop();
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
    public void OrderAndAlginment(int position, boolean aligned)
    {

         if (position == -1)//left
        {
            if(counter == 1) {
                if (aligned == true) {
                        forward(equation(23), 0.5);
                } else {
                    topLeft.setPower(0.02);
                    botRight.setPower(0.02);
                    topRight.setPower(0.02);
                    botLeft.setPower(0.02);
                }
            }
            else if(counter == 2)
            {

            }
        }
        else if (position == 1)
        {
            if(aligned==true)
            {

                if(counter == 1){
                    forward(equation(23), 0.5);
                    counter1=2;
                    }
            }
            else
            {
                topLeft.setPower(-0.02);
                botRight.setPower(-0.02);
                topRight.setPower(-0.02);
                botLeft.setPower(-0.02);
            }
        }
        else if(position == 0)
        {
            forward(equation(26),0.5);
            t.addLine("Straight");
        }
    }
    public void setupTensorCV() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        initTfod();


        /** Wait for the game to begin */
        //time = new ElapsedTime();
        t.update();
        tfod.activate();
    }

    public void detectTensor(){
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                t.addData("# Object Detected", updatedRecognitions.size());
                int goldMineralX = -1;
                int goldY = -1;
                int goldCenterX = -1;
                int goldCenterY = -1;
                int goldHeight = -1;
                alignedTensor = false;
                if (updatedRecognitions.size() >= 1) {
                    for (Recognition r : updatedRecognitions) {
                        if (r.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) r.getLeft();
                            goldY = (int) r.getTop();
                            goldCenterX = center((int) r.getLeft(), (int) r.getRight());
                            goldCenterY = center((int) r.getTop(), (int) r.getBottom());
                            alignedTensor = isAligned(goldCenterX);
                            goldHeight = (int) r.getHeight();
                        }
                    }
                }

                if (updatedRecognitions.size() == 3) {
                    goldMineralX = -1;
                    goldY = -1;
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                            goldY = (int) recognition.getTop();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        } else {
                            silverMineral2X = (int) recognition.getLeft();
                        }
                    }
                    if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                            t.addData("Gold Mineral Position", "Left");
                            t.addLine("Left code was updated");
                            pos = -1;
                        } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                            t.addData("Gold Mineral Position", "Right");
                            t.addLine("Right code was updated");
                            pos = 1;
                        } else {
                            t.addData("Gold Mineral Position", "Center");
                            t.addLine("Center code was updated");
                            pos = 0;
                        }
                    }
                }

                //After camera detects where gold mineral is
                distance = (int) (((2 * focalLength) / goldHeight) + 2);
                        /*
                        if(position == 0){
                            //Distance is always decreasing meaning ticks checked are decreasing meaning will not go full length
                            //Need to find the initial distance, when camera starts detecting
                            forward(equation(distance));
                        }
                        else{
                            //Distance is always decreasing meaning ticks checked are decreasing meaning will not go full length
                            //Need to find the initial distance, when camera starts detecting, and aligned with gold mineral

                            rotate(position, aligned);
                            if(aligned){
                                forward(equation(distance));
                            }
                        }
                        */
                t.addLine("Gold Top Left Corner: (" + goldMineralX + ", " + goldY + ")");
                t.addLine("Gold Center: (" + goldCenterX + ", " + goldCenterY + ")");
                t.addLine("Aligned: " + alignedTensor);
                t.addLine("Focal Length: " + focalLength);
                t.addLine("Distance: " + distance + "in");
                t.update();
            }
        }

    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        CameraCalibration cc = vuforia.getCameraCalibration();
        float[] focalLengths = cc.getFocalLength().getData();
        focalLength = focalLengths[1];

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hwm.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hwm.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.70;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    private static int center(int lower, int higher){
        return lower + ((higher - lower) / 2);
    }
    private static boolean isAligned(int centerX){
        if(centerX > 590 && centerX < 690){
            return true;
        }
        return false;
    }
}
