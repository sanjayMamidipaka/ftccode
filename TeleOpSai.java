package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOpSai")
public class TeleOpSai extends LinearOpMode {
    //DRIVE TRAIN
    public DcMotor BR;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor FL;

    public CRServo intake;

    public DcMotor arm;

    public DcMotor hook1;
    public DcMotor hook2;


    public void runOpMode() throws InterruptedException {

        BR = hardwareMap.get(DcMotor.class, "rightRear");

        BL = hardwareMap.get(DcMotor.class, "leftRear");

        FR = hardwareMap.get(DcMotor.class, "rightFront");

        FL = hardwareMap.get(DcMotor.class, "leftFront");

        //intake = hardwareMap.get(CRServo.class, "intake");

        //arm = hardwareMap.get(DcMotor.class, "arm" );


        hook1 = hardwareMap.get(DcMotor.class, "hangingMotor");
        hook2 = hardwareMap.get(DcMotor.class, "hangingMotor1");


        //BR.setDirection(DcMotorSimple.Direction.REVERSE);
        //FR.setDirection(DcMotorSimple.Direction.REVERSE);


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


                FL.setPower(v1);
                FR.setPower(v2);
                BL.setPower(v3);
                BR.setPower(v4);
                /*
                //COLLECTION
               if (gamepad1.right_bumper)
               {
                   intake.setPower(0.2);
               }
               else
               {
                   intake.setPower(0);
               }
               if(gamepad1.left_bumper)
               {
                   intake.setPower(-0.2);
               }
               else
               {
                   intake.setPower(0);
               }
*/
                //ARM
                //arm.setPower(gamepad1.left_trigger);
                //arm.setPower(-gamepad1.right_trigger);

                //LIFT

                if (gamepad1.a) {
                    hook1.setPower(1);
                    hook2.setPower(-1);
                } else {
                    hook2.setPower(0);
                    hook1.setPower(0);
                }

                if (gamepad1.y) {
                    hook1.setPower(-1);
                    hook2.setPower(1);
                } else {
                    hook2.setPower(0);
                    hook1.setPower(0);
                }
            }
        }
    }
}
