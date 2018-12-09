package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "hangingMechanism")
public class hangingMotor extends LinearOpMode{
    DcMotor hangingMotor;
    DcMotor hangingMotor1;
    DcMotor leftFront;
    DcMotor rightFront;
    private ElapsedTime     runtime = new ElapsedTime();
    public void runOpMode() throws InterruptedException
    {
        hangingMotor = hardwareMap.dcMotor.get("hangingMotor");
        hangingMotor1 = hardwareMap.dcMotor.get("hangingMotor1");
       
        waitForStart();
        hangingMotor.setPower(-0.5);
        hangingMotor1.setPower(0.5);

        while (opModeIsActive() && (runtime.seconds() < 4)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }


    }

}
