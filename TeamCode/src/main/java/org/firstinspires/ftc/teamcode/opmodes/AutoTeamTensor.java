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


@Autonomous(name="AutoTeamTensor")
public class AutoTeamTensor extends OpMode{
    private AutoBot shamsBot;
    private int step;


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
        telemetry.addData("Step", step);
        telemetry.addData("Left Motor Encoder Value After Rotation", shamsBot.encoderForTopLeft);

        //Enter Auto Step 0
        if (step == 0)
        {
            if (shamsBot.pos == 0) {
                telemetry.addLine("Forward (Middle)");
                shamsBot.forward(24, 0.5);
                setStep(1);
                if(step == 1){
                    shamsBot.backward(24, 0.5);
                }
            }
            else if (shamsBot.pos == -1)
            {
                //telemetry.addLine("LEFT");
                shamsBot.rotateLeft();
                setStep(1);
                if (step == 1)
                {
                    //telemetry.addLine("Forward (Left)");
                    shamsBot.forward(24, 0.5);
                    setStep(2);
                }
                if(step == 2){
                    //telemetry.addLine("Backward (Left)");
                    shamsBot.backward(24, 0.5);
                    setStep(3);
                }
                if(step == 3){

                }
            }
            else if (shamsBot.pos == 1)
            {
                //telemetry.addLine("RIGHT");
                //Rotate Left
                shamsBot.rotateRight();
                //Set to Step 1
                setStep(1);
                if (step == 1)
                {
                    //telemetry.addLine("Forward (Right)");
                    shamsBot.forward(24,0.5);
                }
                if(step == 2){
                    //telemetry.addLine("Backward (Left)");
                    shamsBot.backward(24, 0.5);
                    setStep(3);
                }
                if(step == 3){

                }
            }
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


    /*Problems with Auto as of now
            - Encoder values are not being reset after rotation(Possibly)
                - Causing the robot to not stop after rotation
     */

}

