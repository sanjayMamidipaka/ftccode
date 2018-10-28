package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOpSai")
public class TeleOpSai extends LinearOpMode {
    public DcMotor BR;
    public DcMotor BL;
    public DcMotor FR;
    public DcMotor FL;
    public DcMotor intake;
    public DcMotor rotateIntake;
    public DcMotor DrawerIntake;
    public Servo   BasketServo;
    public DcMotor hangLift;
    public Servo mineralServo;

    int servoCounter;







    public void runOpMode ()throws InterruptedException{
        BR = hardwareMap.get(DcMotor.class, "rightRear");

        BL = hardwareMap.get(DcMotor.class, "leftRear");

        FR = hardwareMap.get(DcMotor.class, "rightFront");

        FL = hardwareMap.get(DcMotor.class, "leftFront");

        intake = hardwareMap.get(DcMotor.class, "intake");

        rotateIntake = hardwareMap.get(DcMotor.class, "rotateIntake");

        DrawerIntake = hardwareMap.dcMotor.get("DI");

        hangLift = hardwareMap.dcMotor.get("hangMotor");

        mineralServo = hardwareMap.servo.get("mineralServo");

        BasketServo = hardwareMap.servo.get("bk");


        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);





        waitForStart();
        if(opModeIsActive()) {
            while(opModeIsActive()){
                double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
                double robotAngle = Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = (-gamepad1.right_stick_x)/2.0;
                final double v1 = r * Math.cos(robotAngle) + rightX;
                final double v2 = r * Math.sin(robotAngle) - rightX;
                final double v3 = r * Math.sin(robotAngle) + rightX;
                final double v4 = r * Math.cos(robotAngle) - rightX;



                FL.setPower(v1);
                FR.setPower(v2);
                BL.setPower(v3);
                BR.setPower(v4);


                rotateIntake.setPower(gamepad1.left_trigger);

                rotateIntake.setPower(-gamepad1.right_trigger);

                    if(gamepad1.b){
                        BasketServo.setPosition(0.8);
                    } else if(gamepad1.x){
                        BasketServo.setPosition(0.2);
                    }
                if (gamepad1.right_bumper) {
                    intake.setPower(1);
                }
                else
                {
                    intake.setPower(0);
                }

                /*while (isLeftDown)
                {
                    intake.setTargetPosition(-intakevalue);
                    if(!isLeftDown)
                    {
                        break;
                    }
                }*/
                if (gamepad1.left_bumper)
                {
                    intake.setPower(-1);
                }
                else
                {
                    intake.setPower(0);
                }


                if(gamepad1.dpad_up){
                    DrawerIntake.setPower(-1);
                }
                else{
                    DrawerIntake.setPower(0);
                }
                if (gamepad1.dpad_down){
                    DrawerIntake.setPower(.3);
                }
                else {
                    DrawerIntake.setPower(0);
                }

                if (gamepad1.a){

                    hangLift.setPower(0.3);

                }else{
                    hangLift.setPower(0);
                }
                if (gamepad1.y){

                    hangLift.setPower(-0.3);

                }else{
                    hangLift.setPower(0);
                }

                if(gamepad1.x && servoCounter%2 == 0)
                {
                    mineralServo.setPosition(0.5);
                    servoCounter++;
                }
                else if (servoCounter%2 != 0 )
                {
                    mineralServo.setPosition(0);
                    servoCounter++;
                }




            }






        }
    }
    public void encoderIntake(double setPower){
        if(opModeIsActive()){
            intake.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            intake.setPower(setPower);

            intake.setPower(0);

            intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }

}