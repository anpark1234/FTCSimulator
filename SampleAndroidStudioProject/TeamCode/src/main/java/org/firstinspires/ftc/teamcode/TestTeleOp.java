package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.ftc6448.simulator.PlatformSupport;

@TeleOp(name = "TestTeleop")
@Disabled
public class TestTeleOp extends OpMode {

    // Declare OpMode members.
    private final ElapsedTime runtime = new ElapsedTime();

    DcMotor rightFrontDrive;
    DcMotor leftFrontDrive;
    DcMotor rightBackDrive;
    DcMotor leftBackDrive;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        rightFrontDrive = hardwareMap.get(DcMotor.class, "frontRight");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        rightBackDrive = hardwareMap.get(DcMotor.class, "backRight");
        leftBackDrive = hardwareMap.get(DcMotor.class, "backLeft");

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        //use PlatformSupport method to see if you are running on the simulator
        if (PlatformSupport.isSimulator()) {
            //if your motors on the real robot were different than the simulator, this could reverse the direction
            axial=-axial;
        }

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial + lateral - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }

        // Send calculated power to wheels
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

        //add telemetry.  to see telemetry in Webots, right click on your robot and select "Show Robot Window"
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
