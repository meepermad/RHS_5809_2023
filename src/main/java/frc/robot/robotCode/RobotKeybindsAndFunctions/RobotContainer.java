// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.RobotKeybindsAndFunctions;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.robotCode.Auto.exampleAuto;
//import frc.robot.robotCode.ConstantsAndConfigs.Constants.Swerve;
import frc.robot.robotCode.ConstantsAndConfigs.Constants.OperatorConstants;
import frc.robot.robotCode.commands.Autos;
import frc.robot.robotCode.commands.ExampleCommand;
import frc.robot.robotCode.commands.TeleopSwerve;
import frc.robot.robotCode.subsystems.*;
import frc.robot.robotCode.commands.intakeIN;
import frc.robot.robotCode.commands.intakeOUT;
import frc.robot.robotCode.commands.wristUP;
import frc.robot.robotCode.commands.wristDOWN;
import frc.robot.robotCode.commands.elbowDOWN;
import frc.robot.robotCode.commands.elbowUP;
import frc.robot.robotCode.commands.shoulderDOWN;
import frc.robot.robotCode.commands.shoulderUP;
import frc.robot.robotCode.commands.brakeWrist;
import frc.robot.robotCode.commands.brakeElbow;
import frc.robot.robotCode.commands.brakeShoulder;
import frc.robot.robotCode.subsystems.elbowSub;
import frc.robot.robotCode.subsystems.shoulderSub;
import frc.robot.robotCode.subsystems.wristSub;
import frc.robot.robotCode.subsystems.intakeSub;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  /* Controllers */
  private final Joystick driver = new Joystick(0);

  /* Drive Controls */

 /* */ private final int translationAxis = XboxController.Axis.kLeftY.value;
private final int strafeAxis = XboxController.Axis.kLeftX.value;
 private final int rotationAxis = XboxController.Axis.kLeftTrigger.value;



  /* Driver Buttons */
  private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

  /* Subsystems */
  private final Swerve s_Swerve = new Swerve();
  private final CANdleSubsystem m_candleSubsystem = new CANdleSubsystem(driver);
  //i'm using the the "a_" to denote arm subsystems.  *spiderman camera "neat" meme here*
  private final elbowSub a_elbowSub = new elbowSub();
  private final intakeSub a_intakeSub = new intakeSub();
  private final shoulderSub a_ShoulderSub = new shoulderSub();
  private final wristSub a_WristSub = new wristSub();


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
      s_Swerve.setDefaultCommand(
          new TeleopSwerve(
              s_Swerve, 
              () -> -driver.getRawAxis(translationAxis), 
              () -> -driver.getRawAxis(strafeAxis), 
              () -> -driver.getRawAxis(rotationAxis), 
              () -> robotCentric.getAsBoolean()
          )
      );

      // Configure the button bindings
      configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
      /* Driver Buttons */
      zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));




      
      //this is the shoulder up/down command ON behavior
      new JoystickButton(driver, 5).onTrue(new shoulderUP(a_ShoulderSub, .4, 0));
      new JoystickButton(driver, 3).onTrue(new shoulderDOWN(a_ShoulderSub, 0, .1));
      //this is the should up/down command OFF behavoid
      new JoystickButton(driver, 5).onFalse(new shoulderUP(a_ShoulderSub, 0, 0));
      new JoystickButton(driver, 3).onFalse(new shoulderDOWN(a_ShoulderSub, 0, 0));
      new JoystickButton(driver, 3).onFalse(new brakeShoulder(a_ShoulderSub));
      new JoystickButton(driver, 5).onFalse(new brakeShoulder(a_ShoulderSub));

      //this is the elbow up/down command ON behavior
      new JoystickButton(driver, 6).onTrue(new elbowUP(a_elbowSub, .8, 0));
      new JoystickButton(driver, 4).onTrue(new elbowDOWN(a_elbowSub, 0, .05));
      //this is the elbow up/down command OFF behavoir
      new JoystickButton(driver, 6).onFalse(new elbowUP(a_elbowSub, 0, 0));
      new JoystickButton(driver, 6).onFalse(new brakeElbow(a_elbowSub));
      new JoystickButton(driver, 4).onFalse(new elbowDOWN(a_elbowSub, 0, 0));
      new JoystickButton(driver, 4).onFalse(new brakeElbow(a_elbowSub));

      //this is the wrist up/down command ON behavoir
      new JoystickButton(driver, 11).onTrue(new wristUP(a_WristSub, .6, 0));
      new JoystickButton(driver, 12).onTrue(new wristDOWN(a_WristSub, 0, .1));
      //this is the wrist up/down command OFF behavoir
      new JoystickButton(driver, 11).onFalse(new wristUP(a_WristSub, 0, 0));
      new JoystickButton(driver, 12).onFalse(new wristDOWN(a_WristSub, 0, 0));
      new JoystickButton(driver, 11).onFalse(new brakeWrist(a_WristSub));
      new JoystickButton(driver, 12).onFalse(new brakeWrist(a_WristSub));

      //this is the intake IN/OUT command ON behavoir
      new JoystickButton(driver, 9).onTrue(new intakeIN(a_intakeSub, .85, 0));
      new JoystickButton(driver, 10).onTrue(new intakeOUT(a_intakeSub, 0, .85));
      //this is the intake IN/OUT command OFF behavoir
      new JoystickButton(driver, 9).onFalse(new intakeIN(a_intakeSub, .0, 0));
      new JoystickButton(driver, 10).onFalse(new intakeOUT(a_intakeSub, .0, 0));







    



      
      //panic KILL IT ALL switch

      new JoystickButton(driver, 1).onTrue(new elbowUP(a_elbowSub, 0, 0));
      new JoystickButton(driver, 1).onTrue(new elbowDOWN(a_elbowSub, 0, 0));
      new JoystickButton(driver, 1).onTrue(new brakeElbow(a_elbowSub));
      new JoystickButton(driver, 1).onTrue(new brakeWrist(a_WristSub));
      new JoystickButton(driver, 1).onTrue(new brakeShoulder(a_ShoulderSub));
      new JoystickButton(driver, 1).onTrue(new shoulderDOWN(a_ShoulderSub,0,0));
      new JoystickButton(driver, 1).onTrue(new shoulderUP(a_ShoulderSub,0,0));
      new JoystickButton(driver, 1).onTrue(new wristUP(a_WristSub,0,0));




      new JoystickButton(driver,2).whenPressed(m_candleSubsystem::incrementAnimation, m_candleSubsystem);
  

    }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
      // An ExampleCommand will run in autonomous
      return new exampleAuto(s_Swerve);
  }

}