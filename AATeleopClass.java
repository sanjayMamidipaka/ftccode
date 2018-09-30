package org.firstinspires.ftc.teamcode;

/**
 * Created by sanjaymamidipaka on 9/22/18.
 */
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareConfig;

public class AATeleopClass extends LinearOpMode{

    HardwareConfig robot   = new HardwareConfig();

    public void runOpMode() throws InterruptedException
    {
        robot.init(hardwareMap);

    }

    public void move(float gamePadX,float gamePadY ){
        double r = Math.hypot(gamePadX, gamePadY);
        double robotAngle = Math.atan2(gamePadY, gamePadX) - Math.PI / 4;
        double rightX = gamePadX;
        final double v1 = r * Math.cos(robotAngle) + rightX;
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;

        robot.FL.setPower(v1);
        robot.FR.setPower(v2);
        robot.BL.setPower(v3);
        robot.BR.setPower(v4);

    }
}
