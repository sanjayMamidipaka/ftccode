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
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
import java.util.Locale;
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "RoverAutonmousTensorflow")

public class tensorflow extends LinearOpMode{

    public DcMotor rightRear;
    public DcMotor leftRear;
    public DcMotor rightFront;
    public DcMotor leftFront;
    public ColorSensor sensorColor;
    public DcMotor hangMotor;

    //from TeleOp
    public DcMotor intake;
    public DcMotor intakeMechanismMotor;
    public DcMotor drawerSlideMotor;
    public Servo BasketServo;
    public DcMotor hangLift;
    public Servo colorSensorServo;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = " -- YOUR NEW VUFORIA KEY GOES HERE  --- ";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;


    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode () throws InterruptedException {
        initVuforia();


        boolean isYellow = false;
        double power = 0.2;
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        hangMotor = hardwareMap.dcMotor.get("hangMotor");
        intake = hardwareMap.get(DcMotor.class, "intake");

        intakeMechanismMotor = hardwareMap.get(DcMotor.class, "rotateIntake");
        drawerSlideMotor = hardwareMap.dcMotor.get("DI");
        hangMotor = hardwareMap.dcMotor.get("hangMotor");
        colorSensorServo = hardwareMap.servo.get("mineralServo");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        colorSensorServo.setPosition(1);
        waitForStart();
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //HANG MOTOR
        telemetry.addData("Motor declared", 100);
        telemetry.addData("Motor moving soon", 100);
        //sleep(200);
            /*hangMotor.setTargetPosition(200);
            hangMotor.setPower(0.3);
            hangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hangMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/
        //change value if the code is going the wrong way
        //sleep(2500);
        rightFront.setTargetPosition(500);
        rightRear.setTargetPosition(500);
        leftRear.setTargetPosition(500);
        leftFront.setTargetPosition(500);

            /*rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/

        rightFront.setPower(.2);
        rightRear.setPower(.2);
        leftRear.setPower(.2);
        leftFront.setPower(.2);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            /*rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/

        //movement(0.2, 150, -1,-1, -1, -1); //move forward
        //movement(0.2, 1000, 1, 1,1,1); //move up to the depot
        //drawerSlideMotor.setTargetPosition(300);
        //drawerSlideMotor.setPower(0.3); //move mechanism out
        //intakeMechanismMotor.setTargetPosition(300);
        //intakeMechanismMotor.setPower(-0.3); //drop the team marker
        //movement(0.2,500, 0,0,1,1); //move into a position


        colorSensorServo.setPosition(0.6);

        int isRight = 0;

        if (tfod != null) {
            tfod.activate();

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
                                    telemetry.addData("Gold Detected", 0);
                                    movement(0.2, 500, 1, 1, 1, 1);//moves forward
                                    break;
                                } else if (silverMineral1X == -1) { //if silver is detected
                                    silverMineral1X = (int) recognition.getLeft();
                                    telemetry.addData("Silver Mineral", 0);
                                    movement(0.2, 500, 1, -1, -1, 1); //move to the right
                                    isRight = 1;
                                } else if (silverMineral2X == -1 && isRight == 1){ //checks if the robot has already gone right
                                    silverMineral2X = (int) recognition.getLeft();
                                    telemetry.addData("Silver Mineral", 0);
                                    movement(0.2, 1000, -1, 1, 1, -1); //moves left
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                }
                            }
                        }
                        telemetry.update();
                    }
                }

            }
        }




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

     public void movement(double power, int targetPosition, double LFPower, double LRPower, double  RFPower, double RRPower) {

                rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                rightFront.setTargetPosition(targetPosition);
                rightRear.setTargetPosition(targetPosition);
                leftRear.setTargetPosition(targetPosition);
                leftFront.setTargetPosition(targetPosition);

                rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                rightFront.setPower(RFPower);
                rightRear.setPower(RRPower);
                leftRear.setPower(LFPower);
                leftFront.setPower(LFPower);



                rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);




            }

    }
