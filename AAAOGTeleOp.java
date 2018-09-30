package org.firstinspires.ftc.teamcode;

/**
 * Created by sanjaymamidipaka on 9/22/18.
 */
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class AAAOGTeleOp extends LinearOpMode{

    public void runOpMode(){
        AutoTest robot = new AutoTest();

        AATeleopClass moveClass = new AATeleopClass();

        moveClass.move(gamepad1.left_stick_x, gamepad1.left_stick_y);








    }
}
