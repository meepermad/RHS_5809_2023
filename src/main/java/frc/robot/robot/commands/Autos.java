// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.robot.subsystems.ExampleSubsystem;

public final class Autos {
  /** Example static factory for an autonomous command. */
  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }

  public static Command exampleAuto(ExampleSubsystem m_exampleSubsystem){
    System.out.print("Auto code runs here");
    return null;
  }
}
