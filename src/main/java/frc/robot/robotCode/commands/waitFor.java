// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class waitFor extends CommandBase {
  private final double seconds;
  private double seconds1;
  /** Creates a new waitFor. */
  public waitFor(double seconds) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.seconds = (seconds*1000);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    seconds1 = seconds + System.currentTimeMillis();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("CT | " + System.currentTimeMillis());
    System.out.println("T  | " + seconds1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    seconds1 = seconds + System.currentTimeMillis();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(seconds1 <= System.currentTimeMillis()){
      System.out.println();
      System.out.println();
      System.out.println("Returning true");
      System.out.println("Current Time | " + System.currentTimeMillis());
      System.out.println("Time | " + seconds1);
      System.out.println();

    }
    return seconds1 <= System.currentTimeMillis();
  }
}
