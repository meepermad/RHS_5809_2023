// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.Auto;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
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
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import frc.robot.robotCode.commands.*;
import frc.robot.robotCode.subsystems.*;
import frc.robot.lib.math.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class theoryAuto extends SequentialCommandGroup {
  /** Creates a new simpleAuto. */
  public theoryAuto(Swerve s_Swerve,ShoulderSub x, ElbowSub y, WristSub z, PnuematicsSub a) {
    TrajectoryConfig config = new TrajectoryConfig(
        Constants.AutoConstants.kMaxSpeedMetersPerSecond,
        Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        .setKinematics(Constants.Swerve.swerveKinematics);

    var thetaController = new ProfiledPIDController(
        Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
    thetaController.enableContinuousInput(-Math.PI, Math.PI);

    Trajectory step1 = TrajectoryGenerator.generateTrajectory(
        new Pose2d(0, 0, new Rotation2d(0)),
        List.of(new Translation2d(0.2, 0)),
        new Pose2d(.4, 0, new Rotation2d(0)),
        config);
      
    Trajectory step2 = TrajectoryGenerator.generateTrajectory(
        new Pose2d(0, 0, new Rotation2d(0)),
        List.of(new Translation2d(-0.2, 0)),
        new Pose2d(-.4, 0, new Rotation2d(0)),
        config);

    Trajectory step3 = TrajectoryGenerator.generateTrajectory(
          new Pose2d(0, 0, new Rotation2d(0)),
          List.of(new Translation2d(2.5, 0)),
          new Pose2d(5, 0, new Rotation2d(0)),
          config);
    Trajectory step4 = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(new Translation2d(-0.95, 0)),
            new Pose2d(-1.9, 0, new Rotation2d(180)),
            config);

          SwerveControllerCommand step1Command =
            new SwerveControllerCommand(
              step1,
              s_Swerve::getPose,
              Constants.Swerve.swerveKinematics,
              new PIDController(Constants.AutoConstants.kPXController, 0, 0),
              new PIDController(Constants.AutoConstants.kPYController, 0, 0),
              thetaController,
              s_Swerve::setModuleStates,
              s_Swerve);

              SwerveControllerCommand step2Command =
            new SwerveControllerCommand(
              step2,
              s_Swerve::getPose,
              Constants.Swerve.swerveKinematics,
              new PIDController(Constants.AutoConstants.kPXController, 0, 0),
              new PIDController(Constants.AutoConstants.kPYController, 0, 0),
              thetaController,
              s_Swerve::setModuleStates,
              s_Swerve);

              SwerveControllerCommand step3Command =
            new SwerveControllerCommand(
              step3,
              s_Swerve::getPose,
              Constants.Swerve.swerveKinematics,
              new PIDController(Constants.AutoConstants.kPXController, 0, 0),
              new PIDController(Constants.AutoConstants.kPYController, 0, 0),
              thetaController,
              s_Swerve::setModuleStates,
              s_Swerve);

              SwerveControllerCommand step4Command =
            new SwerveControllerCommand(
              step4,
              s_Swerve::getPose,
              Constants.Swerve.swerveKinematics,
              new PIDController(Constants.AutoConstants.kPXController, 0, 0),
              new PIDController(Constants.AutoConstants.kPYController, 0, 0),
              thetaController,
              s_Swerve::setModuleStates,
              s_Swerve);

    addCommands(
      Commands.race(new reset(s_Swerve), new waitFor(0.5)),
      new InstantCommand(() -> new pnuematicIntakeClawClose(a)),
      new InstantCommand(() -> s_Swerve.resetOdometry(step1.getInitialPose())),
      step1Command.alongWith(
        new pidfShoulder(x, 23),
        new pidfElbow(y, -101),
        new pidfWrist(z, 30)
      ),
      new InstantCommand(() -> s_Swerve.resetOdometry(step2.getInitialPose())),
      step2Command.alongWith(
        new pidfShoulder(x, 23),
        new pidfElbow(y, -101),
        new pidfWrist(z, 30)
      ),
      Commands.race(
        new pidfShoulder(x, 23),
        new pidfElbow(y, -101),
        new pidfWrist(z, 30),
        new waitFor(1)
      ),
      Commands.race(
        new pidfShoulder(x, 23),
        new pidfElbow(y, -101),
        new pidfWrist(z, 30),
        new pnuematicIntakeClawOpen(a),
        new waitFor(1)
      ),
      new InstantCommand(() -> s_Swerve.resetOdometry(step3.getInitialPose())),
      
      step3Command.alongWith(
        new pidfShoulder(x, -20),
        new pidfElbow(y, 20),
        new pidfWrist(z, 73)
      ),
      new InstantCommand(() -> s_Swerve.resetOdometry(step4.getInitialPose())),
      step4Command.alongWith(
        new pidfShoulder(x, -20),
        new pidfElbow(y, 20),
        new pidfWrist(z, 73),
        step4Command
      ),
      Commands.race(
        new pidfShoulder(x, -20),
        new pidfElbow(y, 20),
        new pidfWrist(z, 73),
        new autoBalance(s_Swerve)
      )
    );
  }
}
