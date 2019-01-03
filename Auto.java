package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoTensor")
public class Auto extends LinearOpMode {

    Robot robot = new Robot();

    @Override
    public void runOpMode() {
        robot.rightRear = hardwareMap.dcMotor.get("rightRear");
        robot.leftRear = hardwareMap.dcMotor.get("leftRear");
        robot.rightFront = hardwareMap.dcMotor.get("rightFront");
        robot.leftFront = hardwareMap.dcMotor.get("leftFront");
        //sensorColor = hardwareMap.get(ColorSensor.class, "sensorColor");
        robot.hangingMotor = hardwareMap.dcMotor.get("hangingMotor");
        robot.hangingMotor1 = hardwareMap.dcMotor.get("hangingMotor1");
        robot.markerServo = hardwareMap.servo.get("markerServo");

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                robot.sampleMove();
            }
        }
    }
}
