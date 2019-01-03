package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

public class Robot extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    public tensorFlow vision = new tensorFlow();

    //MOTORS
    public DcMotor rightRear;
    public DcMotor leftRear;
    public DcMotor rightFront;
    public DcMotor leftFront;
    //public ColorSensor sensorColor;
    public DcMotor hangingMotor;
    public DcMotor hangingMotor1;

    //SERVOS
    public Servo markerServo;

    public void movement(double mills, double RF, double RR, double LF, double LR) {
        rightFront.setPower(RF); //moving forward to remove hook
        rightRear.setPower(RR);
        leftFront.setPower(LF);
        leftRear.setPower(LR);
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < mills)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
            telemetry.update();
        }
        rightFront.setPower(0);
        rightRear.setPower(0);
        leftFront.setPower(0);
        leftRear.setPower(0);
        runtime.reset();
    }

    public void placeTeamMarker() {


        markerServo.setPosition(0.1);
        sleep(4000);
        markerServo.setPosition(0.7);
        markerServo.setPosition(0.1);
        runtime.reset();
    }
    public void adjust()
    {
        hangingMotor.setPower(-0.5); //brings robot down
        hangingMotor1.setPower(-0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.milliseconds() < 5000)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.milliseconds());
            telemetry.update();
        }
        hangingMotor.setPower(0);
        hangingMotor1.setPower(0);


        movement(400, 0.3, 0.3, -0.3, -0.3); //moving backward to remove the hook
        sleep(1000);
        movement(1000, 0.6, -0.6, 0.6, -0.6); //strafes right to move forward a little
        movement(1200, 0.6, 0.6, 0.6, 0.6); //rotates the robot in place
        sleep(1000);

    }

    public void sampleMove(){
        if(vision.sendResult() == 1)
        {
            adjust();
            sleep(1000);
            runtime.reset();
            movement(1500, -0.6, 0.6, -0.6, 0.6); //strafes left to sample
            movement(1700, -0.6, -0.6, 0.6, 0.6); //moves forward to the depot and knocks out the sample
            runtime.reset();
            sleep(1000);
            movement(1700, -0.6, -0.6, -0.6, -0.6);
            runtime.reset();
            placeTeamMarker();
            runtime.reset();
        }
        else if (vision.sendResult() == 2)
        {
            adjust();
            sleep(1000);
            runtime.reset();
            movement(1500, 0.6, -0.6, 0.6, -0.6); //strafes right to sample
            movement(1700, -0.6, -0.6, 0.6, 0.6); //moves forward to the depot and knocks out the sample
            runtime.reset();
            sleep(1000);
            movement(1700, -0.6, -0.6, -0.6, -0.6);
            runtime.reset();
            placeTeamMarker();
        }
        else
        {
            adjust();
            sleep(1000);
            runtime.reset();
            movement(1500, -0.6, -0.6, 0.6, 0.6); //moves forward to the depot and knocks out the sample
            sleep(1000);
            movement(2000, -0.6, -0.6, -0.6, -0.6);
            runtime.reset();
            placeTeamMarker();
        }
    }


    @Override
    public void runOpMode() {

    }
}
