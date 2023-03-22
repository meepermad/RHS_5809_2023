// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.robotCode.subsystems.*;

public class candleRGB extends CommandBase {
  /** Creates a new candleRGB. */
  private final CANdleSubsystem sub;
  private final int r,g,b,r1,g1,b1;
  public candleRGB(CANdleSubsystem sub, int r, int b, int g, int r1, int g1, int b1) {
    this.sub = sub;
    this.r = r;
    this.b = b;
    this.g = g;
    this.r1 = r1;
    this.b1 = b1;
    this.g1 = g1;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    sub.setRGB(r, b, g);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    sub.setRGB(r1, g1, b1);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
