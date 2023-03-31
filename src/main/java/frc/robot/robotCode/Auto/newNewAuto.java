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
import frc.robot.robotCode.subsystems.ElbowSub;
import frc.robot.robotCode.subsystems.PnuematicsSub;
import frc.robot.robotCode.subsystems.ShoulderSub;
import frc.robot.robotCode.subsystems.Swerve;
import frc.robot.robotCode.subsystems.WristSub;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class newNewAuto extends SequentialCommandGroup {
  /** Creates a new auto. */
  public newNewAuto(Swerve swerve, ShoulderSub x, ElbowSub y, WristSub z, PnuematicsSub a) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    TrajectoryConfig config = new TrajectoryConfig(
        Constants.AutoConstants.kMaxSpeedMetersPerSecond,
        Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        .setKinematics(Constants.Swerve.swerveKinematics);

    var thetaController = new ProfiledPIDController(
        Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    Trajectory moveFoward = TrajectoryGenerator.generateTrajectory(
        new Pose2d(0, 0, new Rotation2d(0)),
        List.of(new Translation2d(1, 0)),
        new Pose2d(0, 0, new Rotation2d(0)),
        config);
    
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
      new InstantCommand(() -> new pnuematicIntakeClawClose(a)),
      new newAutoSwerve(swerve, ()-> 0.0, ()-> 0.5, ()-> 0.0,()-> false).withTimeout(0.5),
      new newAutoSwerve(swerve, ()-> 0.0, ()-> -0.3, ()-> 0.0,()-> false).alongWith(
        new pidfShoulder(x, 23),
        new pidfElbow(y, -101),
        new pidfWrist(z, 30),
        new waitFor(0.5)
      ).withTimeout(0.75),
      Commands.race(
        new pnuematicIntakeClawOpen(a),
        new waitFor(0.5)
      ),
      new newAutoSwerve(swerve, ()-> 0.0, ()-> 0.5, ()-> 0.0,()-> false).alongWith(
        new pidfShoulder(x, 23),
        new pidfElbow(y, -101),
        new pidfWrist(z, 30),
        new waitFor(.75)
      ).withTimeout(.75),
      new newAutoSwerve(swerve, ()-> 0.0, ()-> 0.5, ()-> 0.0,()-> false).alongWith(
        new pidfShoulder(x, -20),
        new pidfElbow(y, 0),
        new pidfWrist(z, 73),
        new waitFor(2.25)
      ).withTimeout(2.25),
      new newAutoSwerve(swerve, ()-> 0.0, ()-> -0.3, ()-> 0.0,()-> false).alongWith(
        new pidfShoulder(x, -20),
        new pidfElbow(y, 0),
        new pidfWrist(z, 73),
        new waitFor(1.25)
      ).withTimeout(1.25),
      Commands.race(
        new pidfShoulder(x, -20),
        new pidfElbow(y, 0),
        new pidfWrist(z, 73),
        new autoBalance(swerve)
      )
    );
  }
}
