package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.helper.AutoBot;
import org.firstinspires.ftc.teamcode.helper.Driver;
import org.firstinspires.ftc.teamcode.helper.Robot;

@TeleOp(name = "Encoder Test", group = "Auto")
public class EncoderSquare extends OpMode {

    private AutoBot ab;
    private int counter = 0;
    private ElapsedTime time = new ElapsedTime();


    public void init() {
        ab = new AutoBot(hardwareMap, telemetry);
        ab.setUpWheels();
    }
    @Override
    public void init_loop() {

    }
    @Override
    public void start() {

    }
    @Override
    public void loop() {
        if(counter % 2 == 0) {
            forward(equation(46), ab);
            counter++;
            time.reset();
            if (time.time() < 1) {
                ab.topLeft.setPower(0);
                ab.botRight.setPower(0);
                ab.topRight.setPower(0);
                ab.botLeft.setPower(0);
            }
        }
        else {
            rotate(-1985, ab);
            counter++;
            time.reset();
            if (time.time() < 1) {
                ab.topLeft.setPower(0);
                ab.botRight.setPower(0);
                ab.topRight.setPower(0);
                ab.botLeft.setPower(0);
            }
            if(counter > 7){
                ab.topLeft.setPower(0);
                ab.botRight.setPower(0);
                ab.topRight.setPower(0);
                ab.botLeft.setPower(0);
            }
        }


        //ab.move(new Driver(gamepad1).getPowerDriver());
        //telemetry.addLine("left:" + ab.topLeft.getCurrentPosition());
        //telemetry.addLine("right:" + ab.topRight.getCurrentPosition());

    }
    @Override
    public void stop() {

    }
    public static double equation(double distance)
    {
        return (360/(4*Math.PI))*distance;
    }
    public static double rotateEquation(double distance)
    {
        return 0;
    }

    public static void forward(double encode, AutoBot ab){
        if(ab.topLeft.getCurrentPosition() < encode && ab.topRight.getCurrentPosition() < encode){
            ab.topLeft.setPower(.5);
            ab.botRight.setPower(-.5);
            ab.topRight.setPower(-.5);
            ab.botLeft.setPower(.5);

        }
        else {
            ab.topLeft.setPower(0);
            ab.botRight.setPower(0);
            ab.topRight.setPower(0);
            ab.botLeft.setPower(0);
            ab.topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ab.topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ab.botLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ab.botRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        }
    }
    public static void rotate(double encode, AutoBot ab) {
        if (ab.topLeft.getCurrentPosition() > encode) {
            ab.topLeft.setPower(-.5);
            ab.botRight.setPower(-0.5);
            ab.topRight.setPower(-.5);
            ab.botLeft.setPower(-.5);

        } else {
            ab.topLeft.setPower(0);
            ab.botRight.setPower(0);
            ab.topRight.setPower(0);
            ab.botLeft.setPower(0);
            ab.topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ab.topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ab.botLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ab.botRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        }
    }





}
