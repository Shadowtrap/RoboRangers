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
import org.firstinspires.ftc.robotcore.external.navigation.Position;
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

public class RobotAuto extends OpMode
{
    // Detector object

    private AutoBot Auto;

    //public SamplingOrderDetector detectorsam;
    public int step = 0;
   //private double currentServoPos;

    @Override
    public void init() {
        telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");
        Auto = new AutoBot(hardwareMap, telemetry);
        Auto.setUpDropMotor();
        Auto.setUpWheels();
        Auto.time = new ElapsedTime();
        Auto.once = 1;
        Auto.setupTensorCV();//Starting camera detection hence called tfod
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
    public void loop() {
        Auto.detectTensor();//Checking where the object is
        if(Auto.counter == 0 )
        alignAndGo(Auto);
        else if (Auto.counter == 1) {
            Auto.stop();
        }
    }






    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        // Disable the detector
        Auto.tfod.shutdown();
    }

    public void alignAndGo(AutoBot Auto){
        if(Auto.pos == 0 ){
            telemetry.addLine("CENTER");
            Auto.forward(Auto.equation(25), 0.5);
        }
        else if(Auto.pos == -1){
            telemetry.addLine("LEFT");
            if (Auto.alignedTensor) {
                Auto.forward(Auto.equation(25), 0.5);
            }
            else {
                Auto.topLeft.setPower(0.02);
                Auto.botRight.setPower(0.02);
                Auto.topRight.setPower(0.02);
                Auto.botLeft.setPower(0.02);
            }
        }
        else if(Auto.pos == 1) {
            telemetry.addLine("RIGHT");
            if (Auto.alignedTensor) {
                Auto.forward(Auto.equation(25), 0.5);

            }
            else {
                Auto.topLeft.setPower(-0.02);
                Auto.botRight.setPower(-0.02);
                Auto.topRight.setPower(-0.02);
                Auto.botLeft.setPower(-0.02);
            }
        }
    }
}