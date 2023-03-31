// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.robotCode.subsystems.*;
import frc.robot.robotCode.ConstantsAndConfigs.*;

public class autoSwerve extends CommandBase {
  /** Creates a new autoSwerve. */
  private final double x;
  private final double y;
  private final double rotation;
  private final Swerve swerve;
  public SwerveModule[] mSwerveMods;
  public autoSwerve(double x, double y, double rotation, Swerve swerve) {
    this.x = x;
    this.y = y;
    this.rotation = rotation;
    this.swerve = swerve;

    mSwerveMods = new SwerveModule[] {
      new SwerveModule(0, Constants.Swerve.Mod0.constants),
      new SwerveModule(1, Constants.Swerve.Mod1.constants),
      new SwerveModule(2, Constants.Swerve.Mod2.constants),
      new SwerveModule(3, Constants.Swerve.Mod3.constants)
  };
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    SwerveModuleState[] swerveModuleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(
      new ChassisSpeeds(
        -(Math.pow(1.5,x)-1), 
        -(Math.pow(1.5,y)-1), 
        -rotation)
     );

    swerve.setModuleStates(swerveModuleStates);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}




/*
 



Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    (Math.pow(1.5,translation.getX())-1), 
                                    (Math.pow(1.5,translation.getY())-1), 
                                    rotation* Constants.JoysticksSensitivitys.rotationSensitivity, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    (Math.pow(1.5,translation.getX())-1), 
                                    (Math.pow(1.5,translation.getY())-1), 
                                    rotation* Constants.JoysticksSensitivitys.rotationSensitivity)
                                );

        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }



 */