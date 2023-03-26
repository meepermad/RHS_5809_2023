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

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class defaultAuto extends SequentialCommandGroup {
  /** Creates a new simpleAuto. */
  public defaultAuto(Swerve s_Swerve,ShoulderSub x, ElbowSub y, WristSub z, PnuematicsSub a) {
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

    Trajectory moveBackward = TrajectoryGenerator.generateTrajectory(
          new Pose2d(0, 0, new Rotation2d(0)),
          List.of(new Translation2d(-0.5, 0)),
          new Pose2d(0, 0, new Rotation2d(0)),
          config);
    Trajectory moveBackwardFar = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(new Translation2d(-2.25, 0)),
            new Pose2d(0, 0, new Rotation2d(180)),
            config);

          SwerveControllerCommand moveFowardCommand =
            new SwerveControllerCommand(
              moveFoward,
              s_Swerve::getPose,
              Constants.Swerve.swerveKinematics,
              new PIDController(Constants.AutoConstants.kPXController, 0, 0),
              new PIDController(Constants.AutoConstants.kPYController, 0, 0),
              thetaController,
              s_Swerve::setModuleStates,
              s_Swerve);

              SwerveControllerCommand moveBackwardCommand =
            new SwerveControllerCommand(
              moveBackward,
              s_Swerve::getPose,
              Constants.Swerve.swerveKinematics,
              new PIDController(Constants.AutoConstants.kPXController, 0, 0),
              new PIDController(Constants.AutoConstants.kPYController, 0, 0),
              thetaController,
              s_Swerve::setModuleStates,
              s_Swerve);

              SwerveControllerCommand moveBackwardFarCommand =
            new SwerveControllerCommand(
              moveBackwardFar,
              s_Swerve::getPose,
              Constants.Swerve.swerveKinematics,
              new PIDController(Constants.AutoConstants.kPXController, 0, 0),
              new PIDController(Constants.AutoConstants.kPYController, 0, 0),
              thetaController,
              s_Swerve::setModuleStates,
              s_Swerve);

    addCommands(
      Commands.race(new reset(s_Swerve), new waitFor(0.5)),
      Commands.race(new pnuematicIntakeClawClose(a), new waitFor(0.5)),
      Commands.race(
        new pidfShoulder(x, 20),
        new pidfElbow(y, 117.5),
        new pidfWrist(z, -45),
        new waitFor(2)
      ),
      new InstantCommand(() -> s_Swerve.resetOdometry(moveFoward.getInitialPose())),
      Commands.race(
        new pidfShoulder(x, 20),
        new pidfElbow(y, 117.5),
        new pidfWrist(z, -45),
        moveFowardCommand
      ),
      Commands.race(
        new pidfShoulder(x, 25),
        new pidfElbow(y, 117.5),
        new pidfWrist(z, -45),
        new waitFor(2)
      ),
      Commands.race(
        new pidfShoulder(x, 25),
        new pidfElbow(y, 117.5),
        new pidfWrist(z, -45),
        new pnuematicIntakeClawOpen(a),
        new waitFor(1.5)
      ),
      new InstantCommand(() -> s_Swerve.resetOdometry(moveBackward.getInitialPose())),
      
      Commands.race(
        new pidfShoulder(x, 25),
        new pidfElbow(y, 117.5),
        new pidfWrist(z, -45),
        moveBackwardCommand
      ),
      new InstantCommand(() -> s_Swerve.resetOdometry(moveBackwardFar.getInitialPose())),
      Commands.race(
        new pidfShoulder(x, 0),
        new pidfElbow(y, 0),
        new pidfWrist(z, -75),
        moveBackwardFarCommand
      )
    );
  }
}
