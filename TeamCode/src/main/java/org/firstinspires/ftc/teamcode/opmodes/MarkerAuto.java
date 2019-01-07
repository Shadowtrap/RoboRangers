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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.helper.AutoBot;


@Autonomous(name="Marker Auto")

public class MarkerAuto extends OpMode{
    private AutoBot shamsBot;
    private int step;


    @Override
    public void init() {
        shamsBot = new AutoBot(hardwareMap, telemetry);
        shamsBot.setUpWheels();
        shamsBot.resetEncoder();
        telemetry.addData("TopLeft Encoder", shamsBot.topLeft.getCurrentPosition());
        shamsBot.setupTensorCV();
        shamsBot.setupliftmotor();
        shamsBot.setupservo();
        setStep(5);
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

        telemetry.addData("TopLeft Encoder", shamsBot.topLeft.getCurrentPosition());
        telemetry.addData("TopRight Encoder", shamsBot.topRight.getCurrentPosition());
        telemetry.addData("BottomLeft Encoder", shamsBot.botLeft.getCurrentPosition());
        telemetry.addData("BottomRight Encoder", shamsBot.botRight.getCurrentPosition());
        telemetry.addData("Step", step);

        shamsBot.detectTensor();
        //Enter Auto Step 0
        if (step == 0){
            shamsBot.latchdown();
            if(!shamsBot.liftMotor.isBusy())
                step = 1;
        }
        else if(step == 1) {
            shamsBot.backward(5,0.5);
            setStep(2);
        }
        else if(step == 2){
            shamsBot.strafeRight(3);
            setStep(3);
        }
        else if(step == 3) {
            shamsBot.forward(5,0.5);
            setStep(4);
        }
        else if(step == 4) {
            shamsBot.rotate("Right",90,0.5);
            setStep(5);
        }
        else if(step == 5) {
            shamsBot.phoneServo.setPosition(1);
            shamsBot.detectTensor();
            step = 6;
        }
        //Middle
        else if(step == 6&& shamsBot.pos == 0) {
            if(shamsBot.pos == 0) {
                shamsBot.forward(39, 0.5);
                setStep(7);
            }
            else if(step == 7){
                shamsBot.phoneServo.setPosition(0.4);
                setStep(8);
            }
            else if(step == 8) {
                shamsBot.backward(16,0.5);
                setStep(9);
            }
            else if(step == 9){
                shamsBot.rotate("Right",135,0.5);
                setStep(10);
            }
            else if(step == 10)
                shamsBot.forward(85,0.5);
        }
        //Right
        else if(step == 6 && shamsBot.pos == 1) {
            if(shamsBot.pos == 1) {
                shamsBot.rotateRight();
                setStep(7);
            }
            else if(step == 7){
                shamsBot.forward(12,0.5);
                setStep(8);
            }
            else if(step == 8){
                shamsBot.rotateLeft(shamsBot.encoderForTopLeft);
                setStep(9);
            }
            else if(step == 9){
                shamsBot.forward(13,0.5);
                setStep(10);
            }
            else if(step == 10){
                shamsBot.rotate("Left",100,0.5);
                setStep(11);
            }
            else if(step == 11){
                shamsBot.forward(24,0.5);
                setStep(12);
            }
            else if(step == 12){
                shamsBot.strafeRight(24);
                setStep(13);
            }
            else if(step == 13){
                shamsBot.phoneServo.setPosition(0.4);
                setStep(14);
            }
            else if(step == 14){
                shamsBot.rotate("Left",180,0.5);
                setStep(15);
            }
            else if(step == 15)
                shamsBot.forward(80,0.5);

        }
        //Left
        else if(step == 6 && shamsBot.pos == -1) {
            if(shamsBot.pos == -1) {
                shamsBot.rotateLeft();
                setStep(7);
            }
            else if(step == 7){
                shamsBot.forward(12,0.5);
                setStep(8);
            }
            else if(step == 8){
                shamsBot.rotateRight(shamsBot.encoderForTopLeft);
                setStep(9);
            }
            else if(step == 9){
                shamsBot.forward(13,0.5);
                setStep(10);
            }
            else if(step == 10){
                shamsBot.rotate("Left",100,0.5);
                setStep(11);
            }
            else if(step == 11){
                shamsBot.forward(24,0.5);
                setStep(12);
            }
            else if(step == 12){
                shamsBot.strafeRight(24);
                setStep(13);
            }
            else if(step == 13){
                shamsBot.phoneServo.setPosition(0.4);
                setStep(14);
            }
            else if(step == 14){
                shamsBot.rotate("Left",180,0.5);
                setStep(15);
            }
            else if(step == 15)
                shamsBot.forward(80,0.5);

        }

    }

    @Override
    public void stop()
    {
        // Disable the detector
        shamsBot.tfod.shutdown();
    }

    public void setStep(int x)
    {
        if (!shamsBot.topLeft.isBusy())
            step = x;
    }
}