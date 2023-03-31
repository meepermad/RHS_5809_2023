// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.util.*;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import frc.robot.robotCode.RobotKeybindsAndFunctions.RobotContainer;
import frc.robot.robotCode.subsystems.*;

public class pidfWrist extends CommandBase {
  /** Creates a new pidfShoulder. */
  private final WristSub wristSub;
  private final double goal;
  private boolean isInterrupted = false;
  private PIDFWrist angleController = new PIDFWrist("angle", Constants.PIDS.kP_wrist, Constants.PIDS.kI_wrist, Constants.PIDS.kD_wrist, 1);
  public pidfWrist(WristSub wristSub, double goal) {
    this.wristSub = wristSub;
    this.goal = goal;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    isInterrupted = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    wristSub.movewristABS(angleController.calculate(wristSub.getAngle(), goal));
    /*System.out.println("W | Current angle | " + wristSub.getAngle());
    System.out.println("W | PID Value | " + angleController.calculate(wristSub.getAngle(), goal));
    System.out.println("W | Position Error | " + angleController.getPositionError());
    //System.out.println(setpoint);
    System.out.println("");*/

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    isInterrupted = true;
    wristSub.movewristABS(0);
    wristSub.brakeWrist();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}