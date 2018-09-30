package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by sanjaymamidipaka on 9/15/18.
 */

public class AAdriveTrain extends LinearOpMode{
    private int moveforwardencoder = 500;

    private DcMotor FL;
    private DcMotor FR;
    private DcMotor BL;
    private DcMotor BR;



    public void runOpMode ()throws InterruptedException {



    }

    public void moveForward() {
        FL = hardwareMap.dcMotor.get("FL"); //initialize motors
        FR = hardwareMap.dcMotor.get("FR");
        BR = hardwareMap.dcMotor.get("BR");
        BL = hardwareMap.dcMotor.get("BL");



        setTargPos(FL, FR, BR, BL); //set the positions

        runToPos(FL, FR, BR, BL); //move the motors

    }




    public void runToPos(DcMotor FL, DcMotor FR, DcMotor BR, DcMotor BL)
    {
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setTargPos (DcMotor FL, DcMotor FR, DcMotor BR, DcMotor BL)
    {
        FL.setTargetPosition(moveforwardencoder);
        FR.setTargetPosition(moveforwardencoder);
        BR.setTargetPosition(moveforwardencoder);
        BL.setTargetPosition(moveforwardencoder);
    }
}
