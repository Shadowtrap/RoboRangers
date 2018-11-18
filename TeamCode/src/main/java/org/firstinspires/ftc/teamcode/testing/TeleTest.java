package org.firstinspires.ftc.teamcode.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.helper.Driver;
import org.firstinspires.ftc.teamcode.helper.TeleBot;

@TeleOp(name = "Testing TeleOp")
public class TeleTest extends OpMode {

    TeleBot bot;
    Driver driver;
    @Override
    public void init() {
        bot = new TeleBot(hardwareMap,telemetry);
        driver = new Driver(gamepad1);

        bot.setUpScoreMech();
        bot.setUpWheels();
        //bot.setUpArmServo();
        //bot.setUpArmMotor();
    }

    @Override
    public void loop() {
        //bot.armMotorMovement(gamepad1);
        //bot.armServoMovement(gamepad1);
        bot.move(driver.getPowerDriver());
        bot.scoreMech(gamepad1);
    }
}
