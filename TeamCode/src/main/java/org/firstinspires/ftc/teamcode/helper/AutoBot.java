package org.firstinspires.ftc.teamcode.helper;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.helper.SamplingOrderDetector;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.opmodes.MarkerAuto;

import java.util.List;

public class AutoBot extends Robot {
    public ElapsedTime time = new ElapsedTime();
    public SamplingOrderDetector detectorsam;
    public int once = 1;
    public int pos = 2;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "ATMeJeb/////AAAAGaZ47DzTRUyOhcXnfJD+z89ATBWAF+fi+oOutLvXaf0YT/RPuf2mu6VJsJowCDiWiOzGMHUjXKsLBqA4Ziar76oZY/juheUactiQaY6Z3qPHnGmchAMlYuqgKErvggTuqmFca8VvTjtB6YOheJmAbllTDTaCudODpnIDkuFNTa36WCTr4L8HcCnIsB7bjF8pZoivYEBwPkfCVtfAiEpqxbyDAZgNXnuCyp6v/oi3FYZbp7JkFVorcM182+q0PVN5gIr14SKEMlDcDFDiv/sQwNeQOs5iNBd1OSkCoTe9CYbdmtE0gUXxKN2w9NqwATYC6YRJP9uoopxqmr9zkepI10peh2/RnU6pHOmR0KKRAVh8";
    private VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;
    public boolean alignedTensor = false;
    public boolean isMoving = false;
    public int encoderForTopLeft = 0;

    public AutoBot(HardwareMap hardwareMap, Telemetry tele) {
        super(hardwareMap, tele);
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

    public void setupliftmotor()
    {
        try {
            liftMotor = hwm.get(DcMotor.class, "liftMotor");
            liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Display("liftMotor : OK");
        } catch (Exception e) {
            Display("liftMotor : ERROR");
        }
    }

    public void setupservo()
    {
        try {
            phoneServo = hwm.get(Servo.class,"phoneServo");
            //phoneServo.setPosition(0.5);
            Display("phoneServo : OK");
        } catch (Exception e) {
            Display("phoneServo : ERROR");
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
        if (tfod != null && pos == 2) {
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
                            goldCenterX = center((int) r.getLeft(), (int) r.getRight());
                            goldCenterY = center((int) r.getTop(), (int) r.getBottom());
                            alignedTensor = isAligned(goldCenterX);
                            goldHeight = (int) r.getHeight();
                        }
                    }
                }

                if (updatedRecognitions.size() == 3) {
                    goldMineralX = -1;
                    int silverMineral1X = -1;
                    int silverMineral2X = -1;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
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
                //distance = (int) (((2 * focalLength) / goldHeight) + 2);
                t.addLine("Gold Center: (" + goldCenterX + ", " + goldCenterY + ")");
                t.addLine("Aligned: " + alignedTensor);
                t.update();
            }
        }


        /**
         * Initialize the Vuforia localization engine.
         */
    }
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hwm.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hwm.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    private static int center(int lower, int higher){
        return lower + ((higher - lower) / 2);
    }

    private static boolean isAligned(int centerX){
        if(centerX > 610 && centerX < 670){
            return true;
        }
        return false;
    }

    public static double equation(double distance) {
        return (1090 / (4*Math.PI)) * distance;
    }

    public void forward(double distance,double pow) {
        if(topLeft.getCurrentPosition() > (int)equation(-distance) && once == 1) {
            topLeft.setPower(-pow);
            botRight.setPower(pow);
            topRight.setPower(pow);
            botLeft.setPower(-pow);
            isMoving = true;
        }
        else
        {

            stop();

        }
    }

    public void backward(double distance,double pow) {
        if(topLeft.getCurrentPosition() < (int)equation(distance) && once == 1) {
            topLeft.setPower(pow);
            botRight.setPower(-pow);
            topRight.setPower(-pow);
            botLeft.setPower(pow);
            isMoving = true;
        }
        else
        {
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
                isMoving = true;
            }
            else {

                stop();
            }
        } else if (str.equals("Left")==true||str.equals("left")==true) {
            if (topLeft.getCurrentPosition() < (int) (deg * 22.0555555556) && once == 1) {
                topLeft.setPower(pow);
                botRight.setPower(pow);
                topRight.setPower(pow);
                botLeft.setPower(pow);
                isMoving = true;
            } else {

                stop();
            }
        }
        else {
            stop();
        }
    }

    public void rotateLeft(){
        if(!alignedTensor) {
            topLeft.setPower(0.02);
            botRight.setPower(0.02);
            topRight.setPower(0.02);
            botLeft.setPower(0.02);
            isMoving= true;
            encoderForTopLeft = topLeft.getCurrentPosition();
        }
        else
        {

            stop();
        }
    }
    public void rotateRight(){
        if(!alignedTensor) {
            topLeft.setPower(-0.02);
            botRight.setPower(-0.02);
            topRight.setPower(-0.02);
            botLeft.setPower(-0.02);
            isMoving = true;
            encoderForTopLeft = topLeft.getCurrentPosition();
        }
        else
        {
            stop();

        }
    }

    public void rotateLeft(int ticks) {
        if(topLeft.getCurrentPosition() < ticks && once == 1) {
            topLeft.setPower(0.02);
            botRight.setPower(0.02);
            topRight.setPower(0.02);
            botLeft.setPower(0.02);
            isMoving = true;
        }
        else {
            stop();

        }
    }

    public void rotateRight(int ticks) {
        if(topLeft.getCurrentPosition()> -ticks && once == 1){
            topLeft.setPower(-0.2);
            botRight.setPower(-0.2);
            topRight.setPower(-0.2);
            botLeft.setPower(-0.2);
            isMoving = true;
        }
        else{
            stop();
        }
    }

    public void latchdown()
    {
        if(liftMotor.getCurrentPosition() > -11000 && once == 1) {
            liftMotor.setPower(-0.5);
            isMoving = true;
        }
        else{
            stop();
        }
    }
    public void retract()
    {
        if(liftMotor.getCurrentPosition()<100){
            liftMotor.setPower(-0.5);
            isMoving = true;
        }
        else{
            stop();
        }
    }

    public void straferight(double distance, double pow)
    {
        if(topLeft.getCurrentPosition() < (int)equation(distance) && once == 1) {
            topLeft.setPower(pow);
            botRight.setPower(-pow);
            topRight.setPower(pow);
            botLeft.setPower(-pow);
            isMoving = true;
        }
        else
        {
            stop();

        }
    }

    public void strafeleft(double distance, double pow)
    {
        if(topLeft.getCurrentPosition() > (int)equation(-distance) && once == 1) {
            topLeft.setPower(-pow);
            botRight.setPower(pow);
            topRight.setPower(-pow);
            botLeft.setPower(pow);
            isMoving = true;
        }
        else
        {
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
        if(time.milliseconds() < 1000 && once == 2)
        {
            topLeft.setPower(0);
            botRight.setPower(0);
            topRight.setPower(0);
            botLeft.setPower(0);
            topLeft.setPower(0);
            liftMotor.setPower(0);
            resetEncoder();
        }
        else
        {
            MarkerAuto.step++;
            once = 1;
            isMoving = false;
        }

    }

    public void resetEncoder(){
        topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        botLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        botRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        botLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        botRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}