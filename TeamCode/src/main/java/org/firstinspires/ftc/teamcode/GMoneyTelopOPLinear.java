/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Teleop", group="GMoneyBot")

public class GMoneyTelopOPLinear extends LinearOpMode {

    /* Declare OpMode members. */
    GMoneyBot robot           = new GMoneyBot();   // Use a Pushbot's hardware

                                                               // could also use HardwarePushbotMatrix class.
    double          topServoOffset      = 0;                       // Servo mid position
    double          botServoOffset      = 0;
    final double    CLAW_SPEED      = 0.01 ;                   // sets rate to move servo
//    final double  ARM_UP_POWER = 0.15;
//    final double ARM_DOWN_POWER = -0.15;

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        double side;
        double max;
        double lift;
        double extend;
        double drop;
        double flip;


        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            boolean b = false;

            drive = gamepad1.left_stick_y;
            turn  =  gamepad1.right_stick_x;
//                side  =  gamepad2.left_stick_x;
            lift = gamepad2.left_stick_y*5;
//                drop = -gamepad2.left_trigger/2;
            extend = (6*gamepad2.right_stick_y)/10;


            // Combine drive and turn for blended motion.
            left  = drive + turn;
            right = drive - turn;

            // Normalize the values so neither exceed +/- 1.0
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0)
            {
                left /= max;
                right /= max;
            }

            // Output the safe vales to the motor drives.
            robot.leftFrontDrive.setPower(left);
            robot.rightFrontDrive.setPower(right);
            robot.leftBackDrive.setPower(left);
            robot.rightBackDrive.setPower(right);
//            robot.sideDrive.setPower(side);
            robot.liftFront.setPower(lift);
            robot.liftBack.setPower(lift);
            robot.extendRelic.setPower(extend);


            if (gamepad2.a) {
                robot.pickRelic.setPosition(1);
            }
            if (gamepad2.b) {
                robot.pickRelic.setPosition(0);
            }

            if (gamepad1.a) {
                robot.knockerRight.setPosition(0.6);
            }

//            if (gamepad1.a) {
//                robot.closeBottom.setPosition(1);
//            }
//            if (gamepad1.b) {
//                robot.closeBottom.setPosition(0);
//            }

//            if (gamepad1.left_bumper) {
//                robot.closeTop.setPosition(1);
//            }
//
//            if (gamepad1.right_bumper) {
//                robot.closeTop.setPosition(0);
//            }

            // Send telemetry message to signify robot running;
//            telemetry.addData("claw",  "Offset = %.2f", clawOffset);
            telemetry.addData("left_front",  "%.2f", left);
            telemetry.addData("left_back",  "%.2f", left);
            telemetry.addData("right_front", "%.2f", right);
            telemetry.addData("right_back", "%.2f", right);
//
            telemetry.update();

            // Pace this loop so jaw action is reasonable speed.
            sleep(50);
        }
    }
}
