package org.firstinspires.ftc.teamcode;

/**
 * Created by sanjaymamidipaka on 10/6/18.
 */

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareConfig;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;


public class hardwareMapTest extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //CONSTANTS
    static final double COUNTS_PER_MOTOR_REV = 1220;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;


    public DcMotor rightRear = null;
    public DcMotor leftRear;
    public DcMotor rightFront;
    public DcMotor leftFront;
    public DcMotor lowerMotor;
    public DcMotor collectionMotor;
    public DcMotor liftCollection;

    HardwareMap hwMap = null;

    public hardwareMapTest(){

    }

    @Override
    public void runOpMode() {

    }

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        rightRear = hwMap.get(DcMotor.class, "rightRear");
        leftRear = hwMap.get(DcMotor.class, "leftRear");
        rightFront = hwMap.get(DcMotor.class, "rightFront");
        leftFront = hwMap.get(DcMotor.class, "leftFront");
        ColorSensor sensorColor = hwMap.get(ColorSensor.class, "sensor_color_distance");;

        //SET ALL DC MOTOR MOTOR TO DRIVE WITH ENCODER
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void encoderDrive(double speed,
                             double leftBackInches, double rightBackInches, double leftFrontInches, double rightFrontInches,
                             double timeoutS) {

        int newLeftTarget;
        int newRightTarget;
        int newLeft1Target;
        int newRIght1Target;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftRear.getCurrentPosition() + (int) (leftBackInches * COUNTS_PER_INCH);
            newRightTarget = rightRear.getCurrentPosition() + (int) (rightBackInches * COUNTS_PER_INCH);
            newLeft1Target = leftFront.getCurrentPosition() + (int) (leftFrontInches * COUNTS_PER_INCH);
            newRIght1Target = rightFront.getCurrentPosition() + (int) (rightFrontInches * COUNTS_PER_INCH);
            leftRear.setTargetPosition(newLeftTarget);
            rightRear.setTargetPosition(newRightTarget);
            leftFront.setTargetPosition(newLeft1Target);
            rightFront.setTargetPosition(newRIght1Target);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            // reset the timeout time and start motion.
            runtime.reset();
            leftRear.setPower(Math.abs(speed));
            leftFront.setPower(Math.abs(speed));
            rightRear.setPower(Math.abs(speed));
            rightFront.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftRear.isBusy() && leftFront.isBusy() && rightRear.isBusy() && rightFront.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        leftRear.getCurrentPosition(),
                        leftFront.getCurrentPosition(),
                        rightFront.getCurrentPosition(),
                        rightRear.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftRear.setPower(0);
            leftFront.setPower(0);
            rightRear.setPower(0);
            rightFront.setPower(0);


            // Turn off RUN_TO_POSITION
            leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move
        }
    }

}
