package org.firstinspires.ftc.teamcode;
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
    DcMotor liftCollection;
    DcMotor collectionMotor;

    //testHardwareMap robot = new testHardwareMap();

    @Override
    public void runOpMode() {
        liftCollection = hardwareMap.dcMotor.get("liftCollection");
        collectionMotor = hardwareMap.dcMotor.get("collectionMotor");

    }

    public void lowerCollection(float degrees)
    {
        liftCollection.setDirection(DcMotorSimple.Direction.REVERSE);
        targetSet(liftCollection, degrees, 2);

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
        collectionMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        collectionMotor.setPower(power);

    }


    public void outake(float power)
    {
        collectionMotor.setPower(power);
    }

    public void targetSet(DcMotor motor, float degree, float power)
    {
        motor.setTargetPosition(convertTicks(degree));
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public int convertTicks(float degrees)
    {
        int ticks = (int)degrees * 12;
        return ticks;
    }

}
