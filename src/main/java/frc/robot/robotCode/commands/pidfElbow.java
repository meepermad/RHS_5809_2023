// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.util.*;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import frc.robot.robotCode.subsystems.*;

public class pidfElbow extends CommandBase {
  /** Creates a new pidfShoulder. */
  private ElbowSub elbowSub;
  private double goal;
  private PIDFElbow angleController = new PIDFElbow("angle", Constants.PIDS.kP_elbow, Constants.PIDS.kI_elbow, Constants.PIDS.kD_elbow, 1);
  public pidfElbow(ElbowSub elbowSub, double goal) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.elbowSub = elbowSub;
    this.goal = goal;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Math.abs(angleController.getPositionError()) > 0.5)
      elbowSub.elABS(MathUtil.clamp(angleController.calculate(elbowSub.getAngle(), (goal + elbowSub.getOffset())), -20, 15));
    else
      elbowSub.brakeEL();

   //running functions to fix arm
   elbowSub.getAngle();
   angleController.calculate(elbowSub.getAngle(), (goal + elbowSub.getOffset()));
   angleController.getPositionError();


   //System.out.println("Elbow Offset | " + elbowSub.getOffset());
   //System.out.println(setpoint);
   //System.out.println("");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elbowSub.elABS(0);
    elbowSub.brakeEL();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return (Math.abs(angleController.getPositionError()) < 3);
    return false;
  }
}