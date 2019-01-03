package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "FinalTeleop")
public class FinalTeleop extends LinearOpMode {
    //DRIVE TRAIN
    public DcMotor BR;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor FL;



    public DcMotor hook1;
    public DcMotor hook2;

    public DcMotor markerMotor;
    public DcMotor markerMotor1;

    public CRServo mainIntake;

    public Servo rotationLeft;
    public Servo rotationRight;

    public DcMotor hangingMotor;
    public DcMotor hangingMotor1;


    public void runOpMode() throws InterruptedException {

        BR = hardwareMap.get(DcMotor.class, "rightRear");

        BL = hardwareMap.get(DcMotor.class, "leftRear");

        FR = hardwareMap.get(DcMotor.class, "rightFront");

        FL = hardwareMap.get(DcMotor.class, "leftFront");

        hangingMotor = hardwareMap.dcMotor.get("hangingMotor");
        hangingMotor1 = hardwareMap.dcMotor.get("hangingMotor1");




        hook1 = hardwareMap.get(DcMotor.class, "hangingMotor");
        hook2 = hardwareMap.get(DcMotor.class, "hangingMotor1");

        mainIntake = hardwareMap.crservo.get("mainIntake");
        rotationLeft = hardwareMap.servo.get("rotationLeft");
        rotationRight = hardwareMap.servo.get("rotationRight");
        markerMotor = hardwareMap.get(DcMotor.class, "markerMotor");
        markerMotor1 = hardwareMap.get(DcMotor.class, "markerMotor1");

        


        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

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


                FL.setPower(v1);
                FR.setPower(v2);
                BL.setPower(v3);
                BR.setPower(v4);


                //COLLECTION


                if (gamepad1.dpad_left && leftServoPosition < 0.5) //moves the left claw left
                {
                    leftServoPosition += 0.1;
                    rotationLeft.setPosition(leftServoPosition);
                }
                else
                {
                    rotationLeft.setPosition(leftServoPosition);
                }

                if (gamepad1.dpad_right && leftServoPosition > 0) //moves the left claw right
                {
                    leftServoPosition -= 0.1;
                    rotationLeft.setPosition(leftServoPosition);
                }
                else
                {
                    rotationLeft.setPosition(leftServoPosition);
                }

                if (gamepad1.x && rightServoPosition <1) //moves the right claw left
                {
                    rightServoPosition += 0.1;
                    rotationRight.setPosition(rightServoPosition);
                }
                else
                {
                    rotationRight.setPosition(rightServoPosition);
                }

                if (gamepad1.b && rightServoPosition >0.5) //moves the right claw right
                {
                    rightServoPosition -= 0.1;
                    rotationLeft.setPosition(rightServoPosition);
                }
                else
                {
                    rotationRight.setPosition(rightServoPosition);
                }

                if (gamepad1.left_trigger > 0) //rotates the mechanism up
                {
                    markerMotor.setPower(1);
                    markerMotor1.setPower(-1);
                }
                else
                {
                    markerMotor.setPower(0);
                    markerMotor1.setPower(0);
                }

                if (gamepad1.left_bumper) //rotates the mechanism down
                {
                    markerMotor.setPower(-1);
                    markerMotor1.setPower(1);
                }
                else
                {
                    markerMotor.setPower(0);
                    markerMotor1.setPower(0);
                }

                if (gamepad1.right_trigger > 0) //moves the mechanism out
                {
                    mainIntake.setPower(1);
                }

                else
                {
                    mainIntake.setPower(0);
                }

                if (gamepad1.right_bumper) //brings the mechanism back in
                {
                    mainIntake.setPower(-1);
                }
                else
                {
                    mainIntake.setPower(0);
                }


                if (gamepad1.a) //moves the hook up
                {
                    hangingMotor.setPower(1);
                    hangingMotor1.setPower(1);
                }
                else
                {
                    hangingMotor.setPower(0);
                    hangingMotor1.setPower(0);
                }


                if (gamepad1.y) //moves the hook down
                {
                    hangingMotor.setPower(-1);
                    hangingMotor1.setPower(-1);
                }
                else
                {
                    hangingMotor.setPower(0);
                    hangingMotor1.setPower(0);
                }





                //ARM
                //arm.setPower(gamepad1.left_trigger);
                //arm.setPower(-gamepad1.right_trigger);

                //LIFT


            }
        }
    }
}
