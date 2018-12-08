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
    private int counter;
    private ElapsedTime time;
    private int once;

    public void init() {
        ab = new AutoBot(hardwareMap, telemetry);
        ab.setUpWheels();
        counter = 0;
        time = new ElapsedTime();
        once = 1;

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
            forward(equation(37));
            telemetry.addLine("FORWARD MODE");
        }
        else {
            rotate(-1985);
            telemetry.addLine("rOTATE MODE" );
        }

        telemetry.addLine("Counter:" + counter);
        telemetry.addLine("Once: " + once);
        telemetry.addLine("Time: " + time.milliseconds());
        //ab.move(new Driver(gamepad1).getPowerDriver());
        //telemetry.addLine("left:" + ab.topLeft.getCurrentPosition());
        //telemetry.addLine("right:" + ab.topRight.getCurrentPosition());

    }
    @Override
    public void stop() {

    }
    public static double equation(double distance)
    {
        return (360/(Math.PI))*distance;
    }//4242
    public static double rotateEquation(double distance)
    {
        return 0;
    }

    public void forward(double encode){
        if(ab.topLeft.getCurrentPosition() < encode && ab.topRight.getCurrentPosition() < encode&&once==1){
            ab.topLeft.setPower(.5);
            ab.botRight.setPower(-.5);
            ab.topRight.setPower(-.5);
            ab.botLeft.setPower(.5);
            telemetry.addLine("going forward");
        }
        else {
            timedStop();
        }
    }
    public void rotate(double encode) {
        if (ab.topLeft.getCurrentPosition() > encode&&once == 1) {
            ab.topLeft.setPower(-.5);
            ab.botRight.setPower(-0.5);
            ab.topRight.setPower(-.5);
            ab.botLeft.setPower(-.5);
            telemetry.addLine("rotating");
        } else {
            timedStop();

        }
    }

    public void timedStop()
    {

        if(once==1) {
            time.reset();
            time.startTime();
            once++;
        }

        if(time.milliseconds() < 3000 && once==2)
        {
            /*ab.topLeft.setPower(0);
            ab.botRight.setPower(0);
            ab.topRight.setPower(0);
            ab.botLeft.setPower(0);
*/
            ab.topLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ab.topRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ab.botLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ab.botRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            telemetry.addLine("STOPPPP");

        }
        else// if(once==2)
        {//after i stopped
            once = 1;
            counter++;//go to next phase,
            telemetry.addLine("GO TO NEXT PHASE");
            telemetry.addLine("GO TO NEXT PHASE");
            telemetry.addLine("GO TO NEXT PHASE");
            telemetry.addLine("GO TO NEXT PHASE");
            telemetry.addLine("GO TO NEXT PHASE");
            telemetry.addLine("GO TO NEXT PHASE");

            ab.topLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ab.topRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ab.botLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ab.botRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
}
