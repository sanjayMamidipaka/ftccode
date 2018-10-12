package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;

@TeleOp(name = "BallCollection")
public class BallCollection extends LinearOpMode{

    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {

    }

    public void lowerCollection(float degrees)
    {
        robot.liftCollection.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.targetSet(robot.liftCollection,degrees);

    }
    /*
    public void extendOut(float power)
    {
        robot.targetSet(robot.extendMotor,power);
    }

    public void extendIn(float power)
    {
        robot.targetSet(robot.extendMotor, -power);

    }
    */

    public void intake(float power)
    {
        robot.collectionMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.collectionMotor.setPower(power);

    }


    public void outake(float power)
    {
        robot.collectionMotor.setPower(power);
    }

}
