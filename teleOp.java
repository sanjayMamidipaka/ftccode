package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

@TeleOp(name = "teleOp")
public class teleOp extends  LinearOpMode {

    BallCollection collection = new BallCollection();
    robotLift robotLift = new robotLift();
    Hardware robot = new Hardware();



    public void runOpMode(){

        robot.init(hardwareMap);

        waitForStart();
        while(opModeIsActive()) {

            //BALL COLLECTION
            collection.lowerCollection(gamepad1.left_trigger);

            if (gamepad1.left_bumper) {
                collection.intake((float) robot.power);
            } else {
                collection.intake(0);
            }

            if (gamepad1.right_bumper) {
                collection.outake((float) robot.power);
            } else {
                collection.outake(0);
            }

            /*
            if (gamepad1.a) {
                collection.extendOut((float) robot.power);
            } else {
                collection.extendOut(0);
            }

            if (gamepad1.y) {
                collection.extendIn((float) robot.power);

            } else {
                collection.extendIn(0);
            }
            */
            /*
            //ROBOT LIFT
            if (gamepad1.dpad_up) {
                robotLift.lift(10);
            } else {
                robotLift.lift(0);
            }

            if (gamepad1.dpad_down) {
                robotLift.lower(10);

            } else {
                robotLift.lower(0);
            }

            if(gamepad1.dpad_left)
            {
                robotLift.lock();
            }

            if(gamepad1.dpad_right)
            {
                robotLift.unlock();
            }
            */

            sleep(40);

        }



    }
}
