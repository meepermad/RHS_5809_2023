// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.RobotKeybindsAndFunctions;

import java.beans.Encoder;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.robotCode.Auto.exampleAuto;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
//import frc.robot.robotCode.ConstantsAndConfigs.Constants.Swerve;
import frc.robot.robotCode.ConstantsAndConfigs.Constants.OperatorConstants;
import frc.robot.robotCode.commands.Autos;
import frc.robot.robotCode.commands.ExampleCommand;
import frc.robot.robotCode.commands.TeleopSwerve;
//import frc.robot.robotCode.subsystems.compressorSub;
import frc.robot.robotCode.subsystems.*;
import frc.robot.robotCode.commands.intakeIN;
import frc.robot.robotCode.commands.intakeOUT;
import frc.robot.robotCode.commands.wristUP;
import frc.robot.robotCode.commands.wristDOWN;
import frc.robot.robotCode.commands.elbowDOWN;
import frc.robot.robotCode.commands.elbowUP;
import frc.robot.robotCode.commands.shoulderDOWN;
import frc.robot.robotCode.commands.shoulderUP;
import frc.robot.robotCode.commands.pbrakeSHOULDER_ON;
import frc.robot.robotCode.commands.pbrakeSHOULDER_OFF;
import frc.robot.robotCode.commands.pbrakeSHOULDER_OUT;
import frc.robot.robotCode.commands.pidfElbow;
import frc.robot.robotCode.commands.pidfShoulder;
import frc.robot.robotCode.commands.pidfWrist;
import frc.robot.robotCode.commands.p_intake_GRAB;
import frc.robot.robotCode.commands.p_intake_OFF;
import frc.robot.robotCode.commands.p_intake_RELEASE;

import frc.robot.robotCode.commands.brakeWrist;
import frc.robot.robotCode.commands.pbrakeELBOW_OFF;
import frc.robot.robotCode.commands.pbrakeELBOW_ON;
import frc.robot.robotCode.commands.pbrakeELBOW_OUT;
//import frc.robot.robotCode.commands.compressorOFF;
import frc.robot.robotCode.commands.brakeElbow;
import frc.robot.robotCode.commands.brakeShoulder;
//import frc.robot.robotCode.commands.compressorON;
import frc.robot.robotCode.commands.TestCommand;




/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  /* Controllers */
  public final static Joystick driver = new Joystick(0);
  public final static Joystick operator = new Joystick(1);

  /* Drive Controls */

 /* */ private final int translationAxis = XboxController.Axis.kLeftY.value;
private final int strafeAxis = XboxController.Axis.kLeftX.value;
 private final int rotationAxis = XboxController.Axis.kLeftTrigger.value;

    /* 
    DutyCycleEncoder shoulderEncoder = Constants.Encoders.shoulderEncoder;
    DutyCycleEncoder elbowEncoder = Constants.Encoders.elbowEncoder;
    DutyCycleEncoder wristEncoder = Constants.Encoders.wristEncoder; */

  /* Driver Buttons */
  private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

  public static final DutyCycleEncoder shoulderEncoder = new DutyCycleEncoder(0);
  public static final DutyCycleEncoder elbowEncoder = new DutyCycleEncoder(1);
  public static final DutyCycleEncoder wristEncoder = new DutyCycleEncoder(2);

  /* Subsystems */
  private final Swerve s_Swerve = new Swerve();
  private final CANdleSubsystem m_candleSubsystem = new CANdleSubsystem(driver);
  //i'm using the the "a_" to denote arm subsystems.  *spiderman camera "neat" meme here*
  private final elbowSub a_elbowSub = new elbowSub(elbowEncoder);
  private final intakeSub a_intakeSub = new intakeSub();
  private final shoulderSub a_ShoulderSub = new shoulderSub(shoulderEncoder);
  private final wristSub a_WristSub = new wristSub(wristEncoder);
  private final pnuematicsSub p_pPnuematicsSub = new pnuematicsSub();
  //private final pnuematicsSub p_pPnuematicsSub1 = new pnuematicsSub();
  //private final pnuematicsSub p_pPnuematicsSub2 = new pnuematicsSub();
  //private final PIDFElbow pidfElbow = new PIDFElbow();
  //private final PIDFShoulder pidfShoulder = new PIDFShoulder();
  //private final PIDFWrist pidfWrist = new PIDFWrist();

  //private final compressorSub p_cpCompressorSub = new compressorSub();

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
      new JoystickButton(operator, 1).whileTrue(new shoulderUP(a_ShoulderSub, .2, 0).alongWith(new pbrakeSHOULDER_OUT(p_pPnuematicsSub)));
      new JoystickButton(operator, 2).whileTrue(new shoulderDOWN(a_ShoulderSub, 0, .1).alongWith(new pbrakeSHOULDER_OUT(p_pPnuematicsSub)));

      //this is the should up/down command OFF behavoid
      //new JoystickButton(operator, 1).onFalse(new brakeShoulder(a_ShoulderSub).alongWith(new pbrakeSHOULDER_ON(p_pPnuematicsSub)));
      //new JoystickButton(operator, 2).onFalse(new brakeShoulder(a_ShoulderSub).alongWith(new pbrakeSHOULDER_ON(p_pPnuematicsSub)));
      new JoystickButton(driver, 11).whileTrue(new pidfShoulder(a_ShoulderSub, 2.5));
      new JoystickButton(driver, 12).whileTrue(new pidfShoulder(a_ShoulderSub, 25));
     
      //this is the elbow up/down command ON behavior
      new JoystickButton(operator, 4).whileTrue(new elbowUP(a_elbowSub, .2, 0).alongWith(new pbrakeELBOW_OUT(p_pPnuematicsSub)));
      new JoystickButton(operator, 3).whileTrue(new elbowDOWN(a_elbowSub, 0, .2).alongWith(new pbrakeELBOW_OUT(p_pPnuematicsSub)));

      //this is the elbow up/down command OFF behavoir
      //new JoystickButton(operator, 4).onFalse(new brakeElbow(a_elbowSub).alongWith(new pbrakeELBOW_ON(p_pPnuematicsSub)));
      //new JoystickButton(operator, 3).onFalse(new brakeElbow(a_elbowSub).alongWith(new pbrakeELBOW_ON(p_pPnuematicsSub)));
      new JoystickButton(driver, 4).whileTrue(new pidfElbow(a_elbowSub, 5));
      new JoystickButton(driver, 6).whileTrue(new pidfElbow(a_elbowSub, 35));



      //this is the wrist up/down command ON behavoir
      new JoystickButton(operator, 5).whileTrue(new wristUP(a_WristSub, .3, 0));
      new JoystickButton(operator, 6).whileTrue(new wristDOWN(a_WristSub, 0, .1)); 
      
      //this is the wrist up/down command OFF behavoir
      /*new JoystickButton(operator, 5).onFalse(new brakeWrist(a_WristSub));
      new JoystickButton(operator, 6).onFalse(new brakeWrist(a_WristSub));
      new JoystickButton(operator, 5).onFalse(new wristUP(a_WristSub, 0, 0).andThen(new TestCommand()));
      new JoystickButton(operator, 6).onFalse(new wristDOWN(a_WristSub, 0, 0).andThen(new TestCommand()));*/
      new JoystickButton(driver, 5).whileTrue(new pidfWrist(a_WristSub, -70));



      //this is the intake IN/OUT command ON behavoir
      new JoystickButton(driver, 9).whileTrue(new intakeIN(a_intakeSub, .85, 0));
      new JoystickButton(driver, 10).whileTrue(new intakeOUT(a_intakeSub, 0, .85));

      //this is the intake IN/OUT command OFF behavoir
      //new JoystickButton(driver, 9).onFalse(new intakeIN(a_intakeSub, .0, 0));
      //new JoystickButton(driver, 10).onFalse(new intakeOUT(a_intakeSub, .0, 0));

    
      //this is the intake IN/OUT command ON behavoir
      new JoystickButton(operator, 7).whileTrue(new p_intake_GRAB(p_pPnuematicsSub));
      new JoystickButton(operator, 8).onTrue(
        Commands.race(
            new p_intake_GRAB(p_pPnuematicsSub),
            new pidfShoulder(a_ShoulderSub, 5),
            new pidfElbow(a_elbowSub, 0),
            new pidfWrist(a_WristSub, -15),
            new waitFor(.01))
        .andThen(
            Commands.race(
                new autoSwerve(0, -500, 0, s_Swerve),
                new pidfShoulder(a_ShoulderSub, 5),
                new pidfElbow(a_elbowSub, 0),
                new pidfWrist(a_WristSub, -15),
                new waitFor(.3)))
        .andThen(Commands.race(
            new pidfShoulder(a_ShoulderSub, 5),
            new pidfElbow(a_elbowSub, 0),
            new pidfWrist(a_WristSub, -15),
            new waitFor(0.1)))
        .andThen(Commands.race(
            new autoSwerve(0, 0, 45, s_Swerve),
            new pidfShoulder(a_ShoulderSub, 5),
            new pidfElbow(a_elbowSub, 0),
            new pidfWrist(a_WristSub, -15),
            new waitFor(0.5)))
        .andThen(Commands.race(
            new pidfShoulder(a_ShoulderSub, 5),
            new pidfElbow(a_elbowSub, 0),
            new pidfWrist(a_WristSub, -15),
            new waitFor(1))
        )

    );

    new JoystickButton(operator, 9).whileTrue(new p_intake_GRAB(p_pPnuematicsSub));



    //low scoring position
    new JoystickButton(operator, 2).whileTrue(
        Commands.race(new pidfWrist(a_WristSub, -75), new waitFor(1))
        .andThen(new pidfShoulder(a_ShoulderSub, 35))
        .alongWith(new pidfElbow(a_elbowSub, 20))
        .alongWith(new pidfWrist(a_WristSub, -80))
    );

     // mid scoring positon
     new JoystickButton(operator, 3).whileTrue(
        Commands.race(new pidfWrist(a_WristSub, -75), new waitFor(1))
        .andThen(new pidfShoulder(a_ShoulderSub, 15))
        .alongWith(new pidfElbow(a_elbowSub, 75))
        .alongWith(new pidfWrist(a_WristSub, -75))
    );

    // high scoring position
    new JoystickButton(operator, 4).whileTrue(
        Commands.race(new pidfWrist(a_WristSub, -75), new waitFor(1))
        .andThen(new pidfShoulder(a_ShoulderSub, 28))
        .alongWith(new pidfElbow(a_elbowSub, 115))
        .alongWith(new pidfWrist(a_WristSub, -75))
    );

    // driving postion
    new JoystickButton(operator, 1).whileTrue(
        Commands.race(new pidfWrist(a_WristSub, -75), new waitFor(1))
        .andThen(new pidfShoulder(a_ShoulderSub, 5))
        .alongWith(new pidfElbow(a_elbowSub, 0))
        .alongWith(new pidfWrist(a_WristSub, -75))
    );


    //pickup position
     new JoystickButton(operator, 10).whileTrue(
        Commands.race(new pidfWrist(a_WristSub, -60), new waitFor(1))
        .andThen(new pidfShoulder(a_ShoulderSub, 15))
        .alongWith(new pidfElbow(a_elbowSub, 70))
        .alongWith(new pidfWrist(a_WristSub, -60))
     );


    



      
      //panic KILL IT ALL switch



      //new JoystickButton(driver,2).whenPressed(m_candleSubsystem::incrementAnimation, m_candleSubsystem);
     // new JoystickButton(driver, 9).onTrue(new pbrakeSHOULDER_ON(p_pPnuematicsSub));
     // new JoystickButton(driver, 10).onTrue(new pbrakeSHOULDER_OUT(p_pPnuematicsSub));
    
       

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