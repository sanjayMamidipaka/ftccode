package org.firstinspires.ftc.teamcode;


import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.Robot;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.robotcontroller.external.samples.HardwareConfig;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "RoverAutonmous")
public class ColorSensorCode extends LinearOpMode {

    public ColorSensorCode() {

    }

    //DistanceSensor sensorDistance;

    /*static final double COUNTS_PER_MOTOR_REV = 1220;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 025;*/

    public DcMotor rightRear;
    public DcMotor leftRear;
    public DcMotor rightFront;
    public DcMotor leftFront;
    public ColorSensor sensorColor;
    public DcMotor hangingMotor;
    public DcMotor hangingMotor1;


    //from TeleOp


    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        //init(hardwareMap);
        //first, the robot will have to land onto the playing field, no code for that yet

        boolean isYellow = false;
        boolean isRight = false;
        double power = 0.2;
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensorColor");
        hangingMotor = hardwareMap.dcMotor.get("hangingMotor");
        hangingMotor1 = hardwareMap.dcMotor.get("hangingMotor1");
        //markerMotor = hardwareMap.dcMotor.get("markerMotor");


        hangingMotor.setPower(-0.5); //brings robot down
        hangingMotor1.setPower(0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 2000)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
            telemetry.update();
        }
        hangingMotor.setPower(0);
        hangingMotor1.setPower(0);


        rightFront.setPower(-0.4); //strafes right a little to relieve the natural slant
        rightRear.setPower(0.4);
        leftFront.setPower(-0.3);
        leftRear.setPower(0.3);
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 250)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
            telemetry.update();
        }
        rightFront.setPower(0);
        rightRear.setPower(0);
        leftFront.setPower(0);
        leftRear.setPower(0);
        runtime.reset();

        hangingMotor.setPower(-0.5); //brings robot down again
        hangingMotor1.setPower(0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 1000)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
            telemetry.update();
        }
        hangingMotor.setPower(0);
        hangingMotor1.setPower(0);


        rightFront.setPower(-0.3); //moving forward to remove hook
        rightRear.setPower(-0.3);
        leftFront.setPower(0.3);
        leftRear.setPower(0.3);
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 1000)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
            telemetry.update();
        }
        rightFront.setPower(0);
        rightRear.setPower(0);
        leftFront.setPower(0);
        leftRear.setPower(0);
        sleep(2000);
        runtime.reset();

        rightFront.setPower(0.3); //strafes right
        rightRear.setPower(-0.3);
        leftFront.setPower(-0.3);
        leftRear.setPower(0.3);
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 3000)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
            telemetry.update();
        }
        rightFront.setPower(0);
        rightRear.setPower(0);
        leftFront.setPower(0);
        leftRear.setPower(0);
        sleep(2000);
        runtime.reset();




        telemetry.addData("Not going yet", 0);


        while (isYellow == false) {

            // get a reference to the distance sensor that shares the same name.
            //sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");

            // hsvValues is an array that will hold the hue, saturation, and value information.
            float hsvValues[] = {0F, 0F, 0F};

            // values is a reference to the hsvValues array.
            final float values[] = hsvValues;

            // sometimes it helps to multiply the raw RGB values with a scale factor
            // to amplify/attentuate the measured values.
            final double SCALE_FACTOR = 255;

            // get a reference to the RelativeLayout so we can change the background
            // color of the Robot Controller app to match the hue detected by the RGB sensor.
            int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
            final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

            // wait for the start button to be pressed.
            // loop and read the RGB and distance data.
            // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
            // convert the RGB values to HSV values.
            // multiply by the SCALE_FACTOR.
            // then cast it back to int (SCALE_FACTOR is a double)
            Color.RGBToHSV((int) (sensorColor.red() * SCALE_FACTOR),
                    (int) (sensorColor.green() * SCALE_FACTOR),
                    (int) (sensorColor.blue() * SCALE_FACTOR),
                    hsvValues);

            // send the info back to driver station using telemetry function.
            //telemetry.addData("Distance (cm)",
            //String.format(Locale.US, "%.02f", sensorDistance.getDistance(DistanceUnit.CM)));


            if (sensorColor.red() > sensorColor.green()) { //since yellow has a higher red value than green
                telemetry.addData("Yellow Detected: True", 1);
                //movement(0.2, 100, -1, -1, -1, -1);
                rightFront.setPower(0.5); //moves forward
                rightRear.setPower(0.5);
                leftFront.setPower(-0.5);
                leftRear.setPower(-0.5);
                runtime.reset();
                while (opModeIsActive() && (runtime.milliseconds() < 1700)) {
                    telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
                    telemetry.update();
                }
                rightFront.setPower(0);
                rightRear.setPower(0);
                leftFront.setPower(0);
                leftRear.setPower(0);
                sleep(5000);
                isYellow = true;


            } else {

                telemetry.addData("Yellow Detected: False", 1);
                //movement(0.2, 100, 1, -1, -1, 1);


                telemetry.addData("Alpha", sensorColor.alpha());
                telemetry.addData("Red  ", sensorColor.red());
                telemetry.addData("Green", sensorColor.green());
                telemetry.addData("Blue ", sensorColor.blue());
                telemetry.addData("Hue", hsvValues[0]);

                rightFront.setPower(-0.3); //strafes left
                rightRear.setPower(0.3);
                leftFront.setPower(0.3);
                leftRear.setPower(-0.3);
                runtime.reset();
                while (opModeIsActive() && (runtime.milliseconds() < 2000)) {
                    telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
                    telemetry.update();
                }
                rightFront.setPower(0);
                rightRear.setPower(0);
                leftFront.setPower(0);
                leftRear.setPower(0);
                sleep(2000);
                runtime.reset();

                if (sensorColor.red() > sensorColor.green()) // checks if the sample is yellow
                {
                    telemetry.addData("Yellow: True", 0);
                    sleep(1000);
                    runtime.reset();
                    rightFront.setPower(0.5); //moves forward
                    rightRear.setPower(0.5);
                    leftFront.setPower(-0.5);
                    leftRear.setPower(-0.5);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.milliseconds() < 1700)) {
                        telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
                        telemetry.update();
                    }
                    rightFront.setPower(0);
                    rightRear.setPower(0);
                    leftFront.setPower(0);
                    leftRear.setPower(0);
                    isYellow = true;


                } else //if there is white
                {
                    telemetry.addData("Yellow: False", 0);
                    sleep(1000);
                    runtime.reset();

                    sleep(2000);

                    runtime.reset();
                    rightFront.setPower(-0.4); //strafe all the way right
                    rightRear.setPower(0.4);
                    leftFront.setPower(-0.3);
                    leftRear.setPower(0.3);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.milliseconds() < 4000)) {
                        telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
                        telemetry.update();
                    }
                    rightFront.setPower(0);
                    rightRear.setPower(0);
                    leftFront.setPower(0);
                    leftRear.setPower(0);
                    isYellow = true;

                    runtime.reset();
                    rightFront.setPower(0.5); //moves forward
                    rightRear.setPower(0.5);
                    leftFront.setPower(-0.5);
                    leftRear.setPower(-0.5);
                    runtime.reset();
                    while (opModeIsActive() && (runtime.milliseconds() < 1700)) {
                        telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
                        telemetry.update();
                    }
                    rightFront.setPower(0);
                    rightRear.setPower(0);
                    leftFront.setPower(0);
                    leftRear.setPower(0);


                    // change the background color to match the color detected by the RGB sensor.
                    // pass a reference to the hue, saturation, and value array as an argument
                    // to the HSVToColor method.
                    relativeLayout.post(new Runnable() {
                        public void run() {
                            relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                        }
                    });

                    telemetry.update();
                    stop();
                }

            }

        }



    }
}



