// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.util.*;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import frc.robot.robotCode.subsystems.*;

public class pidfElbow extends CommandBase {
  /** Creates a new pidfShoulder. */
  private elbowSub elbowSub;
  private double goal;
  private PIDFElbow angleController = new PIDFElbow("angle", Constants.PIDS.kP_elbow, Constants.PIDS.kI_elbow, Constants.PIDS.kD_elbow, 1);
  public pidfElbow(elbowSub elbowSub, double goal) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.elbowSub = elbowSub;
    this.goal = goal;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   elbowSub.elABS(angleController.calculate(elbowSub.getAngle(), goal));
   System.out.println("Current angle | " + elbowSub.getAngle());
   System.out.println("PID Value | " + angleController.calculate(elbowSub.getAngle(), goal));
   System.out.println("Position Error | " + angleController.getPositionError());
   //System.out.println(setpoint);
   System.out.println("");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elbowSub.elABS(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}