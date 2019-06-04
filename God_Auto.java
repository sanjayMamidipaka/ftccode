package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="God_Auto")
public class God_Auto extends LinearOpMode {
    //declares other class
    components_for_god_auto robot = new components_for_god_auto();

    BNO055IMU imu;

    private int globalAngle;
    //sets it for angles
    Orientation lastAngles = new Orientation();

    @Override
    public void runOpMode() throws InterruptedException {
        //inits items in hardwareMap
        robot.init(hardwareMap);
        //adds params
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);
        telemetry.addData("Calibrate Required to rotate","Calibrate Please");
        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }

        waitForStart();
        if (opModeIsActive()){
            //run code here runs only once

        }

    }
    //sets angle back to 0
    private void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }
    //gets current angle
    private double getAngle() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;
        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;
        globalAngle += deltaAngle;
        lastAngles = angles;
        return globalAngle;
    }
    public void rotate(int degrees, int speed){
        double frs,fls,bls,brs;

        frs = speed;

        fls = speed;

        bls = speed;

        brs = speed;

        resetAngle();

        if(degrees < 0){ //right
            robot.bl.setPower(bls);
            robot.br.setPower(-brs);
            robot.fl.setPower(fls);
            robot.fr.setPower(-frs);
        }
        if(degrees > 0){ //right
            robot.bl.setPower(-bls);
            robot.br.setPower(brs);
            robot.fl.setPower(-fls);
            robot.fr.setPower(frs);
        }
        else return;
        //rotates until certain degrees
        robot.fl.setPower(fls);
        robot.fr.setPower(frs);
        robot.bl.setPower(bls);
        robot.br.setPower(brs);
        //keeps on checking
        if (degrees < 0) {//right
            //0 because right has to pass 0
            while (opModeIsActive() && getAngle() == 0) {}
            while (opModeIsActive() && getAngle() > degrees) {}
        } else {//left
            while (opModeIsActive() && getAngle() < degrees) {}
        }

        robot.fl.setPower(0);
        robot.fr.setPower(0);
        robot.bl.setPower(0);
        robot.br.setPower(0);

        sleep(1000);

        resetAngle();


    }

}
