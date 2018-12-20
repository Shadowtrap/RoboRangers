package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.helper.Robot;
import org.firstinspires.ftc.teamcode.helper.Driver;
import org.firstinspires.ftc.teamcode.helper.TeleBot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "RobotTele")

public class RobotTele extends OpMode {
    /* Declare OpMode members. */
    //Robot bot;
    TeleBot teleBot;
    Driver driver;
    boolean up;
    boolean down;
    boolean left;
    boolean right;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.addLine("init");
        //bot = new Robot(hardwareMap,telemetry);
        teleBot = new TeleBot(hardwareMap,telemetry);
        driver = new Driver(gamepad1);
        up = gamepad1.dpad_up;
        down = gamepad1.dpad_down;
        left = gamepad1.dpad_left;
        right = gamepad1.dpad_right;
        //bot.setUpArmServo();
		//bot.setUpArmMotor();
        //bot.setUpWheels();
        teleBot.setUpArmMotor();
        //teleBot.setUpArmServo();
        teleBot.setUpWheels();
        //teleBot.setUpScoreMotor();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        //telemetry.addLine("initLoop");
        //Display("initLoop");
        
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
        /*
        telemetry.addLine("loop");
        telemetry.addLine("topLeft power : " + driver.getPowerDriver().topLeft);
        telemetry.addLine("topRight power : " + driver.getPowerDriver().topRight);
        telemetry.addLine("botLeft power : " + driver.getPowerDriver().botLeft);
        telemetry.addLine("botRight power : " + driver.getPowerDriver().topLeft);
		telemetry.addLine("armMotor power : " + bot.getArmMotorPower());
		*/
        //telemetry.addLine("armServo position : " + bot.armServo.getPosition());

        //teleBot.armMotorMovement(gamepad1);
        //teleBot.armServoMovement(gamepad1);
        teleBot.move(driver.getPowerDriver());
        /*if(up){
            teleBot.armMotor1.setPower(0.5);
        }
        else if(down){
            teleBot.armMotor1.setPower(-0.5);
        }
        else if(left){
            teleBot.armMotor1.setPower(0.5);
            teleBot.topRight.setPower(0.5);
            teleBot.botLeft.setPower(0.5);
            teleBot.botRight.setPower(0.5);
        }
        else if(right){
            teleBot.armMotor1.setPower(-0.5);
            teleBot.topRight.setPower(-0.5);
            teleBot.botLeft.setPower(-0.5);
            teleBot.botRight.setPower(-0.5);
        }
        else{
            teleBot.armMotor1.setPower(0);
            teleBot.topRight.setPower(0);
            teleBot.botLeft.setPower(0);
            teleBot.botRight.setPower(0);
        }
        */
        teleBot.armMotorMovement(gamepad1);
        //teleBot.scoreMech(gamepad1);
        //telemetry.addLine("MotorPos : " + teleBot.scoreMotor.getCurrentPosition());
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
