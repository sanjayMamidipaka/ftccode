package org.firstinspires.ftc.teamcode;

/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareConfig;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Pushbot: Auto Drive By Encoder")
//@Disabled
public class AutoTest extends LinearOpMode {

    /* Declare OpMode members. */
      // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();
    HardwareConfig robot   = new HardwareConfig();
    static final double     COUNTS_PER_MOTOR_REV    = 1220 ;    // eg: AndyMark Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    public ColorSensor ColorSensor_Sample;
    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();
        robot.BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.BL.getCurrentPosition(),
                robot.FL.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout


        
        sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    // To strafe right negate only one variable

    int blue = ColorSensor_Sample.blue();
    int red = ColorSensor_Sample.red();
    int green = ColorSensor_Sample.green();




    public void strafeleft(double setTargetPostiton, double power){
        if (opModeIsActive()){
            robot.BL.setTargetPosition((int) setTargetPostiton);
            robot.FL.setTargetPosition(-(int) setTargetPostiton);
            robot.FR.setTargetPosition((int) setTargetPostiton);
            robot.BR.setTargetPosition(-(int) setTargetPostiton);

            // Turn On RUN_TO_POSITION
            robot.BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.BR.setPower(power);
            robot.FR.setPower(power);
            robot.FL.setPower(power);
            robot.BL.setPower(power);

            // Stop all motion;
            robot.BL.setPower(0);
            robot.FL.setPower(0);
            robot.FR.setPower(0);
            robot.BR.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move

        }

    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newLeft1Target;
        int newRight1Target;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeft1Target = robot.BL.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH); //calculating how far the motors have to go, in terms of encoders
            newRight1Target = robot.BR.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newLeftTarget = robot.FL.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.FR.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);

            robot.BL.setTargetPosition(newLeft1Target);
            robot.FL.setTargetPosition(newRightTarget);
            robot.FR.setTargetPosition(newLeftTarget);
            robot.BR.setTargetPosition(newRight1Target);

            // Turn On RUN_TO_POSITION
            robot.BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.BR.setPower(Math.abs(speed));
            robot.FR.setPower(Math.abs(speed));
            robot.FL.setPower(Math.abs(speed));
            robot.BL.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.BL.isBusy() && robot.BR.isBusy() && robot.FL.isBusy() && robot.FR.isBusy()  )) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget
                        ,newRightTarget, newLeft1Target,newRight1Target);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.BL.getCurrentPosition(),
                        robot.BR.getCurrentPosition(),
                        robot.FL.getCurrentPosition(),
                        robot.FR.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.BL.setPower(0);
            robot.FL.setPower(0);
            robot.FR.setPower(0);
            robot.BR.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move
        }
    }
}
