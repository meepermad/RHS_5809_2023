// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.Auto;

import java.util.List;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import frc.robot.robotCode.commands.*;
import frc.robot.robotCode.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class defaultNoMoveAuto extends SequentialCommandGroup {
  /** Creates a new auto. */
  public defaultNoMoveAuto(Swerve swerve, ShoulderSub x, ElbowSub y, WristSub z, PnuematicsSub a, IntakeSub b) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    
    System.out.println("Auto 1");

    addCommands(
      /*Commands.race(new reset(swerve), new waitFor(1)),
      Commands.race(
        new newAutoSwerve(swerve, ()-> 0.5, ()-> 0.0, ()-> 0.0,()-> false),
        new waitFor(1.97)
      ),
      Commands.race(
        new newAutoSwerve(swerve, ()-> 0.0, ()-> 0.3, ()-> 0.0,()-> false),
        new waitFor(0.1)
      )*/

      Commands.race(new reset(swerve), new waitFor(0.5)),
      new resetGyro(swerve),
      new waitFor(0.5),
      new newAutoSwerve(swerve, ()-> 0.0, ()-> 0.3, ()-> 0.0,()-> false).withTimeout(2),
      new pidfShoulder(x, 38).alongWith(
        new pidfElbow(y, -141),
        new pidfWrist(z, 99),
        new waitFor(1)
      ).withTimeout(1),
      new newAutoSwerve(swerve, ()-> 0.0, ()-> -0.3, ()-> 0.0,()-> false).alongWith(
        new pidfShoulder(x, 38),
        new pidfElbow(y, -141),
        new pidfWrist(z, 99),
        new waitFor(1)
      ).withTimeout(2.25),
      Commands.race(
        new intakeWheelsSpinOut(b, 0, .85),
        new pidfShoulder(x, 38),
        new pidfElbow(y, -141),
        new pidfWrist(z, 99),
        new waitFor(0.5)
      ),
      new newAutoSwerve(swerve, ()-> 0.0, ()-> 0.25, ()-> 0.0,()-> false).alongWith(
        new pidfShoulder(x, 38),
        new pidfElbow(y, -141),
        new pidfWrist(z, 99),
        new waitFor(1)
      ).withTimeout(0.5),
      new pidfShoulder(x, -7).alongWith(
        new pidfElbow(y, -1.2),
        new pidfWrist(z, -3),
        new waitFor(1)
      ).withTimeout(1),
      new newAutoSwerve(swerve, ()-> 0.0, ()-> 0.45, ()-> 0.0,()-> false).alongWith(
        new pidfShoulder(x, -7),
        new pidfElbow(y, -1.2),
        new pidfWrist(z, -3),
        new waitFor(1)
      ).withTimeout(1)
    );
  }
}
