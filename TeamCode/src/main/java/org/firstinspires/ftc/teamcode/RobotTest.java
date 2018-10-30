package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.helper.Robot;
import org.firstinspires.ftc.teamcode.helper.Driver;
import org.firstinspires.ftc.teamcode.helper.TeleBot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "RobotTest")

public class RobotTest extends OpMode {
    /* Declare OpMode members. */
    Robot bot;
    //TeleBot botbot;
    Driver driver;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.addLine("init");
        bot = new Robot(hardwareMap,telemetry);
        //botbot = new TeleBot(hardwareMap,telemetry);
        driver = new Driver(gamepad1);
        bot.setUpArm();
        bot.setUpWheels();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        telemetry.addLine("initLoop");
        Display("initLoop");
        
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     *
    @Override
    public void start() {
        telemetry.addLine("start");
    }*/

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addLine("loop");
        telemetry.addLine("topLeft power : " + driver.getPowerDriver().topLeft);
        telemetry.addLine("topRight power : " + driver.getPowerDriver().topRight);
        telemetry.addLine("botLeft power : " + driver.getPowerDriver().botLeft);
        telemetry.addLine("botRight power : " + driver.getPowerDriver().topLeft);
        telemetry.addLine("arm position : " + bot.getArmPosition());
        bot.armMovement(gamepad1);
        bot.move(driver.getPowerDriver());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     
    @Override
    public void stop() {
        telemetry.addLine("stop");
    }*/
    
    public void Display(String s){
        telemetry.addLine(s);
    }
}
