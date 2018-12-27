package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.helper.Robot;
import org.firstinspires.ftc.teamcode.helper.Driver;
import org.firstinspires.ftc.teamcode.helper.TeleBot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "RobotTele")

public class RobotTele extends OpMode {
    TeleBot teleBot;
    Driver driver1,driver2;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.addLine("init");
        teleBot = new TeleBot(hardwareMap,telemetry);
        driver1 = new Driver(gamepad1);

        //may not be needed but we will need to use gamepad 2 for arm movement potentially
        driver2 = new Driver(gamepad2);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        //telemetry.addLine("initLoop");

    }

    /*
     * Code to run ONCE when the driver hits PLAY
     *
     */
    @Override
    public void start() {
        telemetry.addLine("start");
        teleBot.setUpWheels();
        teleBot.setUpScoreMech();
        teleBot.setUpLift();
        teleBot.setUpPhoneServo();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        teleBot.move(driver1.getPowerDriver());
        teleBot.phoneMovement(gamepad1);

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        telemetry.addLine("stop");
    }

}
