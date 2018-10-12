/*
Copyright 2018 FIRST Tech Challenge Team 10675

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@TeleOp(name = "plz work")

public class Drive extends LinearOpMode {
    private Gyroscope imu;
    private DcMotor topLeft;
    private DcMotor topRight;
    private DcMotor botRight;
    private DcMotor botLeft;
    private Blinker expansion_Hub_2;


    @Override
    public void runOpMode() {
        imu = hardwareMap.get(Gyroscope.class, "imu");
        
        topLeft = hardwareMap.get(DcMotor.class, "topLeft");
        
        topRight = hardwareMap.get(DcMotor.class, "topRight");
        botRight = hardwareMap.get(DcMotor.class, "botRight");
        botLeft = hardwareMap.get(DcMotor.class, "botLeft");
        expansion_Hub_2 = hardwareMap.get(Blinker.class, "Expansion Hub 2");
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            
            topLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            topRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            botLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            botRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            
            topLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            topRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            botLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            botLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            
            
            double power = 0;
        
            float lStickY = -this.gamepad1.left_stick_y;
            float lStickX = this.gamepad1.left_stick_x;
            
            /*float rStickY = -this.gamepad1.right_stick_y;
            float rStickX = this.gamepad1.right_stick_x;
            */
            
            if(lStickY == 1 || lStickY == -1){
                power = lStickY;
                topLeft.setPower(-power);
                topRight.setPower(power);
                botLeft.setPower(-power);
                botRight.setPower(power);
                telemetry.addData("Moving","forward / backward");
            }
            
            else if(lStickX == 1 || lStickX == -1){
                power = lStickX;
                topLeft.setPower(power);
                topRight.setPower(power);
                botLeft.setPower(-power);
                botRight.setPower(-power);
                telemetry.addData("moving","strafing");
                
            }
            
            else if(lStickY == 0 && lStickX == 0){
                topLeft.setPower(0);
                topRight.setPower(0);
                botLeft.setPower(0);
                botRight.setPower(0);
            }
            /*
            if(rStickX == 1){
                topLeft.setPower(1);
                topRight.setPower(1);
                botLeft.setPower(1);
                botRight.setPower(1);
            }
            else if(rStickX == -1){
                topLeft.setPower(-1);
                topRight.setPower(-1);
                botLeft.setPower(-1);
                botRight.setPower(-1);
            }
            else{
                topLeft.setPower(0);
                topRight.setPower(0);
                botLeft.setPower(0);
                botRight.setPower(0);
            }*/
            
        }
    }
}
