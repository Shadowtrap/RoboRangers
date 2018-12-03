/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.opmodes;


import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.helper.HSVColorFilter;
import org.firstinspires.ftc.teamcode.helper.SamplingOrderDetector;
import org.firstinspires.ftc.teamcode.helper.AutoBot;
import org.firstinspires.ftc.teamcode.helper.Robot;

@Autonomous(name="RobotAuto", group="DogeCV")

public class RobotAuto extends OpMode
{
    // Detector object

    private AutoBot RobotAuto;

    public SamplingOrderDetector detectorsam;
    public int counter = 1;
   //private double currentServoPos;

    @Override
    public void init() {
        telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");
        RobotAuto = new AutoBot(hardwareMap, telemetry);
        RobotAuto.setUpDropMotor();
        RobotAuto.setUpWheels();



        /////////////////////////////////////////////////////////////////////////////
        /*
        /// / Setup detectorsam
        detectorsam = new SamplingOrderDetector(); // Create the detector
        detectorsam.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize detector with app context and camera
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


       */
        RobotAuto.setDetectors();
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

    }

    /*
     * Code to run REPEATEDLY when the driver hits PLAY
     */
    @Override
    public void loop() {

        if(counter==1)
        {
            RobotAuto.forward(AutoBot.equation(1));
            counter++;
            telemetry.addLine("Step 1");
        }
        else if(counter==2)
        {
            RobotAuto.OrderAndAlginment(10,10,10, RobotAuto.detectorsam.getCurrentOrder());
            counter++;
            telemetry.addLine("Step 2");
        }



        telemetry.addData("IsAligned", RobotAuto.detectorsam.getAligned()); // Is the bot aligned with the gold mineral?
        telemetry.addData("Center X Pos", RobotAuto.detectorsam.getXPosition()); // Gold center X position.
        telemetry.addData("Current Order" , RobotAuto.detectorsam.getCurrentOrder().toString()); // The current result for the frame
        telemetry.addData("Last Order" , RobotAuto.detectorsam.getLastOrder().toString()); // The last known result

    }




    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        // Disable the detector
        RobotAuto.detectorsam.disable();
    }

}