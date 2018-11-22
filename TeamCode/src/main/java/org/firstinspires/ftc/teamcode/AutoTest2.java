package org.firstinspires.ftc.teamcode;


import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.helper.AutoBot;

@Autonomous(name = "Bahti Test", group = "Auto")
public class AutoTest2 extends LinearOpMode {
    AutoBot robot;
    //Variables
    private GoldAlignDetector detector;


    @Override
    public void runOpMode() throws InterruptedException {
        // Set up detector
        detector = new GoldAlignDetector(); // Create detector
        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
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

        //Set Up wheels
        robot = new AutoBot(hardwareMap, telemetry);
        robot.setUpWheels();


        telemetry.addLine("Ready to start");
        telemetry.update();

        /*

        waitForStart();

        if (!isStopRequested()){

            //DO STUFF HERE
            while(!detector.isFound())
            {
                robot.rotateleft();
                log("Step One");//Backwards
            }

            robot.stop();

            if(detector.getXPosition()<=305) {
                while (!detector.getAligned()) {
                    robot.straftLeft();
                    log("Step two");//Backwards
                }
            }
            else{
                while (!detector.getAligned()) {
                    robot.straftRight();
                    log("Step two");//Backwards
                }
            }

            robot.stop();

            robot.driveForward(0.5,2500);



            //robot.driveForward(0.5,3000);

            log("Rotating LEft");//Backwards
            robot.rotateLeft(0.4,1000);

            log("Rotating rightnow");
            robot.rotateRight(0.4,1000);

            log("back to home");
            robot.driveBackward(0.5,1000);


            while (!isStopRequested()){

                telemetry.addLine("Finished auto");
                telemetry.update();
            }

        }


 */
    }
}



