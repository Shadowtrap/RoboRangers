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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraCalibration;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.helper.AutoBot;
import org.firstinspires.ftc.teamcode.helper.Robot;

@Autonomous(name="RobotAuto", group="DogeCV")

public class RobotAuto extends OpMode {
    private AutoBot shamsBot;
    public int step = 0;

    @Override
    public void init() {
        shamsBot = new AutoBot(hardwareMap, telemetry);
        shamsBot.setUpWheels();
        shamsBot.setupTensorCV();
        shamsBot.time = new ElapsedTime();
        step = 0;
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
    public void loop()
    {
        shamsBot.detectTensor();
        telemetry.addData("TopLeft Encoder", shamsBot.topLeft.getCurrentPosition());
        telemetry.addData("TopRight Encoder", shamsBot.topRight.getCurrentPosition());
        telemetry.addData("BottomLeft Encoder", shamsBot.botLeft.getCurrentPosition());
        telemetry.addData("BottomRight Encoder", shamsBot.botRight.getCurrentPosition());

        //Enter Auto Step 0
        if (step == 0)
        {
            //Gold Mineral Straight
            if (shamsBot.pos == 0) {

                //Robot Forward
                telemetry.addLine("Forward (Middle)");
                shamsBot.forward(24, 0.5);


                //Shift Phase 2
                setStep(2);
            }

            //Gold Mineral Left
            else if (shamsBot.pos == -1)
            {
                telemetry.addLine("LEFT");

                //Rotate Left
                shamsBot.rotateLeft();

                //Set to Step 1
                setStep(1);

                //Step 1 -(Left)
                if (step == 1)
                {
                    //Forward 24 inches
                    telemetry.addLine("Forward (Left)");
                    shamsBot.forward(24, 0.5);

                    //Change to Step 2
                    //setStep(2);
                }
                //Step 2 -(Left)
                //else if (step == 2)
                //{
                    //Rotate Left Degree 45
                    //shamsBot.rotate("Left", 45, 0.5);
            }
            //Gold Mineral on the Right
            else if (shamsBot.pos == 1)
            {
                telemetry.addLine("RIGHT");
                //Rotate Left
                shamsBot.rotateRight();

                //Set to Step 1
                setStep(1);

                //Step 1 -(Left)
                if (step == 1)
                {
                    telemetry.addLine("Forward (Right)");
                    //Forward 24 inches
                    shamsBot.forward(shamsBot.equation(12), 0.5);

                    //Change to Step 2
                    //setStep(2);
                }
            }
        }
    }

    @Override
    public void stop(){
        // Disable the detector
        shamsBot.tfod.shutdown();
    }
    public void setStep(int x)
    {
        if (!shamsBot.isMoving)
            step = x;
    }



}