// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.util.*;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import frc.robot.robotCode.subsystems.*;

public class pidfShoulder extends CommandBase {
  /** Creates a new pidfShoulder. */
  private final shoulderSub shoulderSub;
  private PIDFElbow angleController = new PIDFElbow("angle", Constants.PIDS.kP_shoulder, Constants.PIDS.kI_shoulder, Constants.PIDS.kD_shoulder, 0);
  public pidfShoulder(shoulderSub shoulderSub) {
    this.shoulderSub = shoulderSub;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shoulderSub.shoulderABS(angleController.calculate(shoulderSub.getAngle()));
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
