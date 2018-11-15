package org.firstinspires.ftc.teamcode.testing;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.helper.AutoBot;
import org.firstinspires.ftc.teamcode.helper.Timer;

@Autonomous(name = "AutoTest")
public class AutoTest extends LinearOpMode {

    private AutoBot bot;
    private GoldAlignDetector detector;
    private Timer time;
    @Override
    public void runOpMode() throws InterruptedException {

        bot = new AutoBot(hardwareMap,telemetry);
        detector = new GoldAlignDetector();
        time = new Timer();

        bot.setUpWheels();
        bot.setUpDetector(detector);
        bot.setUpDropMotor();

        boolean found = detector.isFound();

        waitForStart();

        double xPos = detector.getXPosition();
        //isStopRequested is a method from opmode
        if(!isStopRequested()){
            bot.drop();
            //bot.seekAndDestroy(found,time,xPos,detector);

        }
    }

    public void waitFor(long milSec){
        time.reset();
        while (!isStopRequested() && !time.hasReached(milSec));
    }


}
