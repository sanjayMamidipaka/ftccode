package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOpSai1")
public class TeleOpSai1 extends LinearOpMode {
    //DRIVE TRAIN



    public void runOpMode() throws InterruptedException {

        HardwarePushbot1 robot = new HardwarePushbot1();
        robot.init(hardwareMap);



        robot.FL.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.BL.setDirection(DcMotorSimple.Direction.REVERSE);

        double leftServoPosition = 0;
        double rightServoPosition = 1;


        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {


                double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
                double robotAngle = Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = (-gamepad1.right_stick_x) / 2.0;
                final double v1 = r * Math.cos(robotAngle) + rightX;
                final double v2 = r * Math.sin(robotAngle) - rightX;
                final double v3 = r * Math.sin(robotAngle) + rightX;
                final double v4 = r * Math.cos(robotAngle) - rightX;


                robot.FL.setPower(v1);
                robot.FR.setPower(v2);
                robot.BL.setPower(v3);
                robot.BR.setPower(v4);


                //COLLECTION







               if (gamepad1.y) //moves the hook up
               {
                   robot.hangingMotor.setPower(1);
                   robot.hangingMotor1.setPower(1);
               }
               else
               {
                   robot.hangingMotor.setPower(0);
                   robot.hangingMotor1.setPower(0);
               }


               if (gamepad1.a) //moves the hook down
               {
                   robot.hangingMotor.setPower(-1);
                   robot.hangingMotor1.setPower(-1);
               }
               else
               {
                   robot.hangingMotor.setPower(0);
                   robot.hangingMotor1.setPower(0);
               }

               if (gamepad1.left_trigger > 0.5)
               {
                   robot.birdBox.setPower(0.7);
                   robot.birdBox2.setPower(0.7);
               }
               else
               {
                   robot.birdBox.setPower(0);
                   robot.birdBox2.setPower(0);
               }

               if (gamepad1.right_trigger > 0.5)
               {
                   robot.birdBox.setPower(-0.7);
                   robot.birdBox2.setPower(-0.7);
               }
               else
               {
                   robot.birdBox.setPower(0);
                   robot.birdBox2.setPower(0);
               }







                //ARM
                //arm.setPower(gamepad1.left_trigger);
                //arm.setPower(-gamepad1.right_trigger);

                //LIFT


            }
        }
    }
}
