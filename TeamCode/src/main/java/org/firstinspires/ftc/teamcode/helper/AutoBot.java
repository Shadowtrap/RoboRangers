package org.firstinspires.ftc.teamcode.helper;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoBot extends Robot {

    //private Timer time = new Timer();
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

    public void setUpDetector(GoldAlignDetector detector){
        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(this.hwm.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
        detector.useDefaults(); // Set detector to use default settings

        // Optional tuning
        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
        detector.downscale = 0.4; // How much to downscale the input frames

        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
        detector.maxAreaScorer.weight = 0.005; //

        detector.ratioScorer.weight = 5; //
        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment
        detector.enable(); // Start the detector!
    }

    public void drop(){

        /*VALUES NEED TO BE DETERMINED
        99999 needs to be set to whatever position we find using enconders
        setPower needs to be adjusted for optimal use
         */
        if(armMotor1.getCurrentPosition() < 10000  && armMotor2.getCurrentPosition() < 10000){
            armMotor1.setPower(-0.75);
            armMotor2.setPower(-0.75);
        }
    }

    public void moveBack(){
        if(topLeft.getCurrentPosition() < 5000){
            topLeft.setPower(1);
        }
        if(topRight.getCurrentPosition() < 5000){
            topRight.setPower(-1);
        }
        if(botLeft.getCurrentPosition() < 5000){
            botLeft.setPower(1);
        }
        if(botRight.getCurrentPosition() < 5000){
            botRight.setPower(-1);
        }
    }

    public void seekAndDestroy(boolean found, double x, boolean aligned){
        if(!found){
            topLeft.setPower(-0.4);
            topRight.setPower(-0.4);
            botLeft.setPower(-0.4);
            botRight.setPower(-0.4);
        }
        else{
            topLeft.setPower(0.0);
            topRight.setPower(0.0);
            botRight.setPower(0.0);
            botLeft.setPower(0.0);
        }

        if(found && aligned){
            if(topLeft.getCurrentPosition() < 10000){
                topLeft.setPower(-1);
            }
            if(topRight.getCurrentPosition() < 10000){
                topRight.setPower(-1);
            }
            if(botLeft.getCurrentPosition() < 10000){
                botLeft.setPower(-1);
            }
            if(botRight.getCurrentPosition() < 10000){
                botRight.setPower(-1);
            }
        }
    }
/*
    public void seekAndDestroy(boolean found, Timer t, double x, GoldAlignDetector detector){

        if(!found){
            topLeft.setPower(-0.5);
            topRight.setPower(-0.5);
            botLeft.setPower(-0.5);
            botRight.setPower(-0.5);
        }
        else{
            topLeft.setPower(0.0);
            topRight.setPower(0.0);
            botRight.setPower(0.0);
            botLeft.setPower(0.0);
        }
        t.reset();

        //drive forward a bit so that we dont hit the lander VALUES NEED TO BE UPDATED
        if(t.hasReached(1000)){
            topLeft.setPower(0.5);
            topRight.setPower(0.5);
            botRight.setPower(0.5);
            botLeft.setPower(0.5);
        }
        align(x,detector.getAligned());

        t.reset();
        //time needed to  reach and push away block
        //NEED EDIT DUHHHHHHH
        //INCORPO
        if(!t.hasReached(999)){
            topLeft.setPower(0.5);
            topRight.setPower(-0.5);
            botRight.setPower(-0.5);
            botLeft.setPower(0.5);
        }
        //SAME VALUE AS IN THE IF
        else if(t.hasReached(999)){
            topLeft.setPower(0.0);
            topRight.setPower(0.0);
            botRight.setPower(0.0);
            botLeft.setPower(0.0);
        }
        //longer value than inital this is the return trip back to its spot
        else if(!t.hasReached(99999)){
            topLeft.setPower(-0.5);
            topRight.setPower(0.5);
            botRight.setPower(0.5);
            botLeft.setPower(-0.5);
        }
    }
*/
    //motor values MAY NEED FIXING both for neg/pos + speed val
    public void align(double x, boolean aligned){
        if(!aligned && x  < 9999){
            topLeft.setPower(-.5);
            botLeft.setPower(.5);
            topRight.setPower(-.5);
            botRight.setPower(.5);
        }
        else if(!aligned && x > 9999999){
            topLeft.setPower(.5);
            botLeft.setPower(-.5);
            topRight.setPower(.5);
            botRight.setPower(-.5);
        }
        else{
            topLeft.setPower(0);
            botLeft.setPower(0);
            topRight.setPower(0);
            botRight.setPower(0);
        }
    }
}
