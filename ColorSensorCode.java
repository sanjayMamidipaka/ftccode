package org.firstinspires.ftc.teamcode;


import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareConfig;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "RoverAutonmous")
public class ColorSensorCode extends LinearOpMode {

    ColorSensor sensorColor;

    //DistanceSensor sensorDistance;
    DcMotor rightRear;
    hardwareMapTest robot = new hardwareMapTest();



    @Override
    public void runOpMode() throws InterruptedException {
    robot.init(hardwareMap);

        // get a reference to the color sensor.
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        rightRear = hardwareMap.dcMotor.get("rightRear");

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
        waitForStart();

        // loop and read the RGB and distance data.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive()) {
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
            boolean isYellow = true;
            while (isYellow) { //constantly check for change
                if (sensorColor.red() > sensorColor.green()) { //since yellow has a higher red value than green
                    telemetry.addData("Yellow Detected: True", 1);
                    isYellow = false; //gets you out of the for loop
                    //encoderDrive(0.2, 2, 2, 2, 2, 1); //method for moving, in Hardware.java
                    robot.run();

                } else {
                    telemetry.addData("Yellow Detected: False", 1);
                    //isYellow = false;
                }
            }
            telemetry.addData("Alpha", sensorColor.alpha());
            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }

        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });

    }


}

