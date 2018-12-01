package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


import java.text.SimpleDateFormat;
import java.util.Date;


@TeleOp(name = "SampleTest")
@Disabled
public class DriveTest extends OpMode {
    /* Declare OpMode members. */
    
    private DcMotor topLeft;
    private DcMotor topRight;
    private DcMotor botRight;
    private DcMotor botLeft;
    private Blinker expansion_Hub_2;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        topLeft = hardwareMap.get(DcMotor.class, "topLeft");
        topRight = hardwareMap.get(DcMotor.class, "topRight");
        botRight = hardwareMap.get(DcMotor.class, "botRight");
        botLeft = hardwareMap.get(DcMotor.class, "botLeft");
        
        topLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        topRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        botLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        botRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        expansion_Hub_2 = hardwareMap.get(Blinker.class, "Expansion Hub 2");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {}
    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        
        
        
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double power = 0;
        
        float lStickY = -this.gamepad1.left_stick_y;
        float lStickX = this.gamepad1.left_stick_x;
        
        telemetry.addLine("Y: "+lStickY+" X: "+lStickX);
        
        if(lStickY >= 0.5 || lStickY <= -0.5){
            power = lStickY;
            topLeft.setPower(power);
            topRight.setPower(power);
            botLeft.setPower(power);
            botRight.setPower(power);
        }
        
        else if(lStickX >= 0.1 || lStickX <= -0.1){
            power = lStickX;
            topLeft.setPower(power);
            topRight.setPower(power);
            botLeft.setPower(-power);
            botRight.setPower(-power);
        }
        
        else {
            power = 0;
            topLeft.setPower(power);
            topRight.setPower(power);
            botLeft.setPower(-power);
            botRight.setPower(-power);
        }
    
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
    
    
}
