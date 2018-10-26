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
    public DcMotor DrawerOutake;


    int intakevalue = 3200;
    int rotateIntake2 = 540;
    int glyphServoCounter = 1;





    public void runOpMode ()throws InterruptedException{
        BR = hardwareMap.get(DcMotor.class, "rightRear");

        BL = hardwareMap.get(DcMotor.class, "leftRear");

        FR = hardwareMap.get(DcMotor.class, "rightFront");

        FL = hardwareMap.get(DcMotor.class, "leftFront");

        intake = hardwareMap.get(DcMotor.class, "intake");

        rotateIntake = hardwareMap.get(DcMotor.class, "rotateIntake");

        DrawerIntake = hardwareMap.dcMotor.get("DI");

        DrawerOutake = hardwareMap.dcMotor.get("hangMotor");


        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        FL.setDirection(DcMotorSimple.Direction.REVERSE);





        waitForStart();
        if(opModeIsActive()) {
            while(opModeIsActive()){
                double r = Math.hypot(-gamepad1.left_stick_x, -gamepad1.left_stick_y);
                double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
                double rightX = (gamepad1.right_stick_x)/2.0;
                final double v1 = r * Math.cos(robotAngle) + rightX;
                final double v2 = r * Math.sin(robotAngle) - rightX;
                final double v3 = r * Math.sin(robotAngle) + rightX;
                final double v4 = r * Math.cos(robotAngle) - rightX;



                FL.setPower(v1);
                FR.setPower(v2);
                BL.setPower(v3);
                BR.setPower(v4);


                rotateIntake.setPower(gamepad1.left_trigger*2);

                rotateIntake.setPower(-gamepad1.right_trigger*2);

                  /*  if(gamepad1.b){
                        BasketServo.setPosition(0.5);
                    } else if(gamepad1.x){
                        BasketServo.setPosition(0);
                    } */
                if (gamepad1.right_bumper) {
                    intake.setTargetPosition(intakevalue);
                    encoderIntake(0.3);
                }
                if (gamepad1.left_bumper) {
                    intake.setTargetPosition(-intakevalue);
                    encoderIntake(0.3);
                }


                if(gamepad1.dpad_up){
                    DrawerIntake.setPower(-.3);
                    Thread.sleep(1000);
                }
                else{
                    DrawerIntake.setPower(0);
                }
                if (gamepad1.dpad_down){
                    DrawerIntake.setPower(.3);
                    Thread.sleep(1000);
                }else {
                    DrawerIntake.setPower(0);
                }

                if (gamepad1.a){

                    DrawerOutake.setPower(0.3);
                    Thread.sleep(1000);

                }else{
                    DrawerOutake.setPower(0);
                }
                if (gamepad1.y){

                    DrawerOutake.setPower(-0.3);
                    Thread.sleep(1000);

                }else{
                    DrawerOutake.setPower(0);
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