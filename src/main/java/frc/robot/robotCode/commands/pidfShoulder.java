// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.util.*;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import frc.robot.robotCode.RobotKeybindsAndFunctions.RobotContainer;
import frc.robot.robotCode.subsystems.*;

public class pidfShoulder extends CommandBase {
  /** Creates a new pidfShoulder. */
  private final ShoulderSub shoulderSub;
  private PIDFShoulder angleController = new PIDFShoulder("angle", Constants.PIDS.kP_shoulder, Constants.PIDS.kI_shoulder, Constants.PIDS.kD_shoulder, 0);
  private double goal;
  public pidfShoulder(ShoulderSub shoulderSub, double goal) {
    this.shoulderSub = shoulderSub;
    this.goal = goal;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shoulderSub.initializeCounter();
    RobotContainer.shoulderOffset = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Math.abs(angleController.getPositionError()) > 0.5)
      shoulderSub.shoulderABS(angleController.calculate(shoulderSub.getAngle(), (goal + RobotContainer.shoulderOffset)));
    else
      shoulderSub.brakeSH0();
    System.out.println("S | Current angle | " + shoulderSub.getAngle());
    System.out.println("S | PID Value | " + angleController.calculate(shoulderSub.getAngle(), (goal + RobotContainer.shoulderOffset)));
    System.out.println("S | Position Error | " + angleController.getPositionError());
    //System.out.println(setpoint);
    System.out.println("");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shoulderSub.shoulderABS(0);
    //shoulderSub.brakeSH0();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return shoulderSub.isSwitchSet();
    return false;
  }
}