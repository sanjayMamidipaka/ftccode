package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "TensorFlowCopy1")
public class finalTensorflowCopy1 extends LinearOpMode{



    /* Copyright (c) 2018 FIRST. All rights reserved.
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



    /**
     * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
     * determine the position of the gold and silver minerals.
     *
     * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
     * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
     *
     * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
     * is explained below.
     */


        private ElapsedTime runtime = new ElapsedTime();


        boolean mineralLeft;
        boolean mineralRight;
        boolean mineralCenter;

        public ElapsedTime timer = new ElapsedTime();




        private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
        private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
        private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code on the next line, between the double quotes.
         */
        private static final String VUFORIA_KEY = " AVfSIvj/////AAAAGTUcKyWgcEfQmFojiL3wVsFONL5cc42Z7eYOWsi87kgM5mtlXY6Hx9fOOW11fSORiTbcFyQq0QjvARAgIllmsmYh4T+JGxgDMAWyhN+UjViuPjGjJTeiJ5NxyQmU7beQDGnTYewwu6Eqh3aL7LLW47ESa3KZKzctmNv8BGcEf9+wYNxCQI8zVOC0ZSdnM8pJBiwD7cYmrNgmvHb9Zkm71+BpBHWnE9JlEFF+bAbiLArvZ2W/OVqT1ib5H2a5hHfuyfqCuGjuXi+lFwOW9fXFEmq2+WihPylBPEEl8qoBXRH7LcqjUifwyz4GSwwZc5IknKmVFXYZxl7I+5QL/IPmSGW7VTHRIr7nT+bcI0JtgNen";

        /**
         * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
         * localization engine.
         */
        private VuforiaLocalizer vuforia;

        /**
         * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
         * Detection engine.
         */
        private TFObjectDetector tfod;

        @Override
        public void runOpMode() {
            HardwarePushbot1 robot   = new HardwarePushbot1();
            robot.init(hardwareMap);
            timer.reset();
            telemetry.addData("timeElapsed", timer.time());

            // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
            // first.
            initVuforia();

            if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
                initTfod();
            } else {
                telemetry.addData("Sorry!", "This device is not compatible with TFOD");
            }

            /** Wait for the game to begin */
            telemetry.addData(">", "Press Play to start tracking");
            telemetry.update();
            waitForStart();
            timer.startTime();

            if (opModeIsActive()) {

                /** Activate Tensor Flow Object Detection. */
                if (tfod != null) {
                    tfod.activate();

                    //starts moving robot to position

                    while (opModeIsActive() && (runtime.milliseconds() < 5000)) {
                        telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
                        telemetry.update();
                    }

                }

                while (opModeIsActive()) {

                    if (tfod != null) {
                        // getUpdatedRecognitions() will return null if no new information is available since
                        // the last time that call was made.
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            if (updatedRecognitions.size() == 3) {
                                int goldMineralX = -1;
                                int silverMineral1X = -1;
                                int silverMineral2X = -1;
                                for (Recognition recognition : updatedRecognitions) {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                        goldMineralX = (int) recognition.getLeft();
                                    } else if (silverMineral1X == -1) {
                                        silverMineral1X = (int) recognition.getLeft();
                                    } else {
                                        silverMineral2X = (int) recognition.getLeft();
                                    }
                                }
                                if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                    if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                        telemetry.addData("Gold Mineral Position", "Left");
                                        sleep(3000);
                                        mineralLeft = true;
                                        runtime.reset();



                                        break;
                                    } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                        telemetry.addData("Gold Mineral Position", "Right");

                                        sleep(3000);
                                        mineralRight = true;
                                        runtime.reset();


                                        break;
                                    }
                                    else {
                                        telemetry.addData("Gold Mineral Position", "Center");
                                        sleep(3000);
                                        mineralCenter = true;
                                        runtime.reset();
                                        break;
                                    }


                                }
                            }
                            else if (updatedRecognitions.size() == 2)
                            {
                                int goldMineralX = -1;
                                int silverMineral1X = -1;
                                int silverMineral2X = -1;

                                // This just records values, and is unchanged

                                for (Recognition recognition : updatedRecognitions)
                                {
                                    if (recognition.getLabel().equals(LABEL_GOLD_MINERAL))
                                    {
                                        goldMineralX = (int) recognition.getLeft();
                                    }
                                    else if (silverMineral1X == -1)
                                    {
                                        silverMineral1X = (int) recognition.getLeft();
                                    }
                                    else
                                    {
                                        silverMineral2X = (int) recognition.getLeft();
                                    }
                                }

                                // If there is no gold (-1) and there two silvers (not -1) the gold
                                // is not visible, and must be on the right

                                if (goldMineralX == -1 && silverMineral1X != -1 && silverMineral2X != -1)
                                {
                                    telemetry.addData("Gold Mineral Position", "Right");

                                    sleep(3000);
                                    mineralRight = true;
                                    runtime.reset();


                                    break;

                                }

                                // If you can see one gold and one silver ...

                                else if (goldMineralX != -1 && silverMineral1X != -1) {
                                    // ... if the gold is to the right of the silver, the gold is in the center ...

                                    if (goldMineralX > silverMineral1X) {
                                        telemetry.addData("Gold Mineral Position", "Center");
                                        sleep(3000);
                                        mineralCenter = true;
                                        runtime.reset();
                                        break;
                                    }

                                    // ... otherwise it is on the left

                                    else {
                                        telemetry.addData("Gold Mineral Position", "Left");
                                        sleep(3000);
                                        mineralLeft = true;
                                        runtime.reset();

                                        break;

                                    }
                                }
                                else
                                {
                                    adjust();
                                    sleep(1000);
                                    runtime.reset();
                                    movement(1700, -0.6, -0.6, 0.6, 0.6); //moves forward to the depot and knocks out the sample
                                    movement(3300, -0.6, -0.6, -0.6, -0.6);
                                    runtime.reset();
                                    robot.markerServo.setPosition(0.3);
                                }
                            }
                        /*
                        else if (updatedRecognitions.size()<3 && timer.time()>= 10 )
                        {
                            adjust();
                            movement(1700, -0.6, -0.6, 0.6, 0.6); //moves forward to the depot and knocks out the sample
                            hangingMotor.setPower(0);
                            hangingMotor1.setPower(0);
                        }
                        */
                            telemetry.update();
                        }
                    }
                }
            }

            if (tfod != null) {
                tfod.shutdown();
            }


            if(mineralLeft == true)
            {
                adjust();
                sleep(1000);
                runtime.reset();
                telemetry.addData("strafes left to sample", "1");
                movement(1500, -0.6, 0.6, -0.6, 0.6); //strafes left to sample
                telemetry.addData("moves forward to the depot and knocks out the sample", "1");
                movement(1800, -0.6, -0.6, 0.6, 0.6); //moves forward to the depot and knocks out the sample
                runtime.reset();
                sleep(1000);
                movement(550, 0.6, 0.6, 0.6, 0.6); //rotate right
                movement(500, -0.6, -0.6, 0.6, 0.6); //move forward a bit
                robot.markerServo.setPosition(0.3); //place team marker
                sleep(200);
                robot.markerServo.setPosition(0);
                movement(4000, 0.6, 0.6, -0.6, -0.6); //move backward to the crater

            }
            else if (mineralRight == true)
            {
                adjust();
                sleep(1000);
                runtime.reset();
                movement(800, 0.6, -0.6, 0.6, -0.6); //strafes right to sample
                movement(1700, -0.6, -0.6, 0.6, 0.6); //moves forward to the depot and knocks out the sample
                runtime.reset();
                sleep(500);
                movement(600, -0.6, -0.6, -0.6, -0.6); //rotates in place a bit
                runtime.reset();
                //movement(1000, -0.6, 0.6, -0.6, 0.6); //strafes left to depot
                movement(200, -0.6, -0.6, 0.6, 0.6); //moves forward a little bit
                robot.markerServo.setPosition(0.3);

                sleep(1000);
                movement(4200, 0.6, 0.6, -0.6, -0.6); //moves backwards
                /*movement(5000, 0.6, -0.6, 0.6, -0.6); //strafes right to the crater
                sleep(200);
                movement(1100, 0.6, 0.6, 0.6, 0.6); //rotates in place
                sleep(200);
                movement(700, -0.6, -0.6, 0.6, 0.6); //moves forward*/


            }
            else
            {
                adjust();
                sleep(1000);
                runtime.reset();
                movement(250, -0.6, 0.6, -0.6, 0.6); //strafe a little to the left
                sleep(100);
                movement(2200, -0.6, -0.6, 0.6, 0.6); //moves forward to the depot and knocks out the sample
                runtime.reset();
                robot.markerServo.setPosition(0.3); //drops team marker
                movement(600, 0.6, 0.6, 0.6, 0.6); //rotates in place
                robot.markerServo.setPosition(0);
                movement(1000, 0.6, -0.6, 0.6, -0.6); //strafes right a bit
                sleep(200);
                movement(1000, 0.6, 0.6, 0.6, 0.6); //rotates in place
                sleep(200);
                movement(600, -0.6, 0.6, -0.6, 0.6); //strafes left a bit
                sleep(200);
                movement(3800, -0.6, -0.6, 0.6, 0.6); //moves forward to the crater


            }



            // movement(1300, 0.6, 0.6, 0.6, 0.6); //rotates the robot in place
            //movement(1700, -0.6, -0.6, 0.6, 0.6); //moves the robot to the crater

        }

        /**
         * Initialize the Vuforia localization engine.
         */
        private void initVuforia() {
            /*
             * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
             */
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

            parameters.vuforiaLicenseKey = VUFORIA_KEY;
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;


            //  Instantiate the Vuforia engine
            vuforia = ClassFactory.getInstance().createVuforia(parameters);

            // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
        }

        /**
         * Initialize the Tensor Flow Object Detection engine.
         */
        private void initTfod() {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);

        }

        public void movement(double mills, double RF, double RR, double LF, double LR) {
            HardwarePushbot1 robot   = new HardwarePushbot1();
            robot.init(hardwareMap);
            robot.rightFront.setPower(RF); //moving forward to remove hook
            robot.rightRear.setPower(RR);
            robot.leftFront.setPower(LF);
            robot.leftRear.setPower(LR);
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < mills)) {
                telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
                telemetry.update();
            }
            robot.rightFront.setPower(0);
            robot. rightRear.setPower(0);
            robot.leftFront.setPower(0);
            robot.leftRear.setPower(0);
            runtime.reset();
        }

        public void placeTeamMarker() {


            telemetry.addData("placing the team marker", 2);
            runtime.reset();
        }
        public void adjust()
        {
            HardwarePushbot1 robot   = new HardwarePushbot1();
            robot.init(hardwareMap);
            robot.hangingMotor.setPower(-0.5); //brings robot down
            robot.hangingMotor1.setPower(-0.5);
            runtime.reset();
            while (opModeIsActive() && (runtime.milliseconds() < 5000)) {
                telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
                telemetry.update();
            }
            robot.hangingMotor.setPower(0);
            robot.hangingMotor1.setPower(0);


            movement(400, 0.3, 0.3, -0.3, -0.3); //moving backward to remove the hook
            sleep(500);
            movement(1200, 0.6, -0.6, 0.6, -0.6); //strafes right to move forward a little
            movement(1100, 0.6, 0.6, 0.6, 0.6); //rotates the robot in place

        }



    }
