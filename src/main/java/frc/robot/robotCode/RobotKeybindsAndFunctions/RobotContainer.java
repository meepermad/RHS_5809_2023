// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.RobotKeybindsAndFunctions;

import java.beans.Encoder;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.robotCode.Auto.auto;
import frc.robot.robotCode.Auto.defaultAuto;
import frc.robot.robotCode.Auto.exampleAuto;
import frc.robot.robotCode.Auto.newAuto;
import frc.robot.robotCode.Auto.newNewAuto;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
//import frc.robot.robotCode.ConstantsAndConfigs.Constants.Swerve;
import frc.robot.robotCode.ConstantsAndConfigs.Constants.OperatorConstants;
import frc.robot.robotCode.commands.TeleopSwerve;
import frc.robot.robotCode.commands.autoBalance;
//import frc.robot.robotCode.subsystems.compressorSub;
import frc.robot.robotCode.subsystems.*;
import frc.robot.robotCode.commands.intakeWheelsSpinIn;
import frc.robot.robotCode.commands.intakeWheelsSpinOut;
import frc.robot.robotCode.commands.wristUp;
import frc.robot.robotCode.commands.wristDown;
import frc.robot.robotCode.commands.elbowDown;
import frc.robot.robotCode.commands.elbowUp;
import frc.robot.robotCode.commands.shoulderDown;
import frc.robot.robotCode.commands.shoulderUp;
import frc.robot.robotCode.commands.waitFor;
import frc.robot.robotCode.commands.pnuematicBrakeShoulderEngage;
import frc.robot.robotCode.commands.pnuematicBrakeShoulderDisengage;
import frc.robot.robotCode.commands.pidfElbow;
import frc.robot.robotCode.commands.pidfShoulder;
import frc.robot.robotCode.commands.pidfWrist;
import frc.robot.robotCode.commands.pnuematicIntakeClawClose;
import frc.robot.robotCode.commands.pnuematicIntakeClawOpen;
import frc.robot.robotCode.commands.reset;
import frc.robot.robotCode.commands.brakeWrist;
import frc.robot.robotCode.commands.candleRGB;
import frc.robot.robotCode.commands.changeOffset;
import frc.robot.robotCode.commands.pnuematicBrakeElbowDisengage;
import frc.robot.robotCode.commands.pnuematicBrakeElbowEngage;
//import frc.robot.robotCode.commands.compressorOFF;
import frc.robot.robotCode.commands.brakeElbow;
import frc.robot.robotCode.commands.brakeShoulder;
//import frc.robot.robotCode.commands.compressorON;
import frc.robot.robotCode.commands.autoSwerve;




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
 public static double sensitivityAxis = (XboxController.Axis.kRightTrigger.value / 2.0) + 1; 
 public static double elbowOffset = 0;

    /* 
    DutyCycleEncoder shoulderEncoder = Constants.Encoders.shoulderEncoder;
    DutyCycleEncoder elbowEncoder = Constants.Encoders.elbowEncoder;
    DutyCycleEncoder wristEncoder = Constants.Encoders.wristEncoder; */

  /* Driver Buttons */
  private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
  private final JoystickButton robotCentric = new JoystickButton(driver, 6);

  public static final DutyCycleEncoder shoulderEncoder = new DutyCycleEncoder(0);
  public static final DutyCycleEncoder elbowEncoder = new DutyCycleEncoder(1);
  public static final DutyCycleEncoder wristEncoder = new DutyCycleEncoder(2);

  /* Subsystems */
  public static final Swerve s_Swerve = new Swerve();
  public static final CANdleSubsystem m_candleSubsystem = new CANdleSubsystem(driver);
  //i'm using the the "a_" to denote arm subsystems.  *spiderman camera "neat" meme here*
  public static final ElbowSub a_elbowSub = new ElbowSub(elbowEncoder);
  public static final IntakeSub a_intakeSub = new IntakeSub();
  public static final ShoulderSub a_ShoulderSub = new ShoulderSub(shoulderEncoder);
  public static final WristSub a_WristSub = new WristSub(wristEncoder);
  public static final PnuematicsSub p_pPnuematicsSub = new PnuematicsSub();
  //private final pnuematicsSub p_pPnuematicsSub1 = new pnuematicsSub();
  //private final pnuematicsSub p_pPnuematicsSub2 = new pnuematicsSub();
  //private final PIDFElbow pidfElbow = new PIDFElbow();
  //private final PIDFShoulder pidfShoulder = new PIDFShoulder();
  //private final PIDFWrist pidfWrist = new PIDFWrist();

  //private final compressorSub p_cpCompressorSub = new compressorSub();

  private static final Command kDefaultAuto = new defaultAuto(s_Swerve, a_ShoulderSub, a_elbowSub, a_WristSub, p_pPnuematicsSub);
  private static final Command kCustomAuto = new auto(s_Swerve, a_ShoulderSub, a_elbowSub, a_WristSub, p_pPnuematicsSub);
  private Command m_autoSelected;
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  int r1,g1,b1;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Old Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
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
      //new JoystickButton(operator, 1).whileTrue(new shoulderUP(a_ShoulderSub, .2, 0).alongWith(new pbrakeSHOULDER_OUT(p_pPnuematicsSub)));
      //new JoystickButton(operator, 2).whileTrue(new shoulderDOWN(a_ShoulderSub, 0, .1).alongWith(new pbrakeSHOULDER_OUT(p_pPnuematicsSub)));

      //this is the should up/down command OFF behavoid
      //new JoystickButton(operator, 1).onFalse(new brakeShoulder(a_ShoulderSub).alongWith(new pbrakeSHOULDER_ON(p_pPnuematicsSub)));
      //new JoystickButton(operator, 2).onFalse(new brakeShoulder(a_ShoulderSub).alongWith(new pbrakeSHOULDER_ON(p_pPnuematicsSub)));
      //new JoystickButton(driver, 11).whileTrue(new pidfShoulder(a_ShoulderSub, 2.5));
      //new JoystickButton(driver, 12).whileTrue(new pidfShoulder(a_ShoulderSub, 25));
     
      //this is the elbow up/down command ON behavior
      //new JoystickButton(operator, 4).whileTrue(new elbowUP(a_elbowSub, .2, 0).alongWith(new pbrakeELBOW_OUT(p_pPnuematicsSub)));
      //new JoystickButton(operator, 3).whileTrue(new elbowDOWN(a_elbowSub, 0, .2).alongWith(new pbrakeELBOW_OUT(p_pPnuematicsSub)));

      //this is the elbow up/down command OFF behavoir
      //new JoystickButton(operator, 4).onFalse(new brakeElbow(a_elbowSub).alongWith(new pbrakeELBOW_ON(p_pPnuematicsSub)));
      //new JoystickButton(operator, 3).onFalse(new brakeElbow(a_elbowSub).alongWith(new pbrakeELBOW_ON(p_pPnuematicsSub)));
      //new JoystickButton(driver, 4).whileTrue(new pidfElbow(a_elbowSub, 5));
      //new JoystickButton(driver, 6).whileTrue(new pidfElbow(a_elbowSub, 35));



      //this is the wrist up/down command ON behavoir
      //new JoystickButton(operator, 11).whileTrue(new wristUP(a_WristSub, .3, 0));
      //new JoystickButton(operator, 12).whileTrue(new wristDOWN(a_WristSub, 0, .1)); 
      
      //this is the wrist up/down command OFF behavoir
      /*new JoystickButton(operator, 5).onFalse(new brakeWrist(a_WristSub));
      new JoystickButton(operator, 6).onFalse(new brakeWrist(a_WristSub));
      new JoystickButton(operator, 5).onFalse(new wristUP(a_WristSub, 0, 0).andThen(new TestCommand()));
      new JoystickButton(operator, 6).onFalse(new wristDOWN(a_WristSub, 0, 0).andThen(new TestCommand()));*/
      //new JoystickButton(driver, 5).whileTrue(new pidfWrist(a_WristSub, -70));



      //this is the intake IN/OUT command ON behavoir
      new JoystickButton(operator, 5).whileTrue(new intakeWheelsSpinIn(a_intakeSub, .85, 0));
      new JoystickButton(driver, 6).whileTrue(new intakeWheelsSpinOut(a_intakeSub, 0, .85));

      //this is the intake IN/OUT command OFF behavoir
      //new JoystickButton(driver, 9).onFalse(new intakeIN(a_intakeSub, .0, 0));
      //new JoystickButton(driver, 10).onFalse(new intakeOUT(a_intakeSub, .0, 0));

      new JoystickButton(operator, 11).onTrue(new changeOffset(-2));
      new JoystickButton(operator, 12).onTrue(new changeOffset(2));

      new JoystickButton(driver, 12).whileTrue(new reset(s_Swerve));

      new JoystickButton(driver, 11).whileTrue(new autoBalance(s_Swerve));

    
      //this is the intake IN/OUT command ON behavoir
      new JoystickButton(operator, 7).whileTrue(new pnuematicIntakeClawClose(p_pPnuematicsSub));
      new JoystickButton(operator, 8).onTrue(
        Commands.race(
            new pnuematicIntakeClawOpen(p_pPnuematicsSub),
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

    new JoystickButton(operator, 9).whileTrue(new pnuematicIntakeClawOpen(p_pPnuematicsSub));
    new JoystickButton(driver, 5).whileTrue(new pnuematicIntakeClawOpen(p_pPnuematicsSub));
    


    //low scoring position
    new JoystickButton(operator, 2).onTrue(
        ((new pidfShoulder(a_ShoulderSub, 40)))
        .alongWith(new pidfElbow(a_elbowSub, 30))
        .alongWith(new pidfWrist(a_WristSub, -75))
        .until(()-> new JoystickButton(operator, 3).getAsBoolean() || new JoystickButton(operator, 1).getAsBoolean() || new JoystickButton(operator, 4).getAsBoolean() || new JoystickButton(operator, 10).getAsBoolean())
    );

     // mid scoring positon
     new JoystickButton(operator, 3).onTrue(
        ((new pidfShoulder(a_ShoulderSub, 10))
        .alongWith(new pidfElbow(a_elbowSub, 75))
        .alongWith(new pidfWrist(a_WristSub, -75)))
        .until(()-> new JoystickButton(operator, 2).getAsBoolean() || new JoystickButton(operator, 1).getAsBoolean() || new JoystickButton(operator, 4).getAsBoolean() || new JoystickButton(operator, 10).getAsBoolean())
    );

    // high scoring position
    new JoystickButton(operator, 4).onTrue(
        ((new pidfShoulder(a_ShoulderSub, 25))
        .alongWith(new pidfElbow(a_elbowSub, 117.5))
        .alongWith(new pidfWrist(a_WristSub, -45)))
        .until(()-> new JoystickButton(operator, 3).getAsBoolean() || new JoystickButton(operator, 1).getAsBoolean() || new JoystickButton(operator, 2).getAsBoolean() || new JoystickButton(operator, 10).getAsBoolean())
    );

    // driving postion
    new JoystickButton(operator, 1).onTrue(
        ((new pidfShoulder(a_ShoulderSub, 0))
        .alongWith(new pidfElbow(a_elbowSub, 0))
        .alongWith(new pidfWrist(a_WristSub, -75)))
        .until(()-> new JoystickButton(operator, 3).getAsBoolean() || new JoystickButton(operator, 2).getAsBoolean() || new JoystickButton(operator, 4).getAsBoolean() || new JoystickButton(operator, 10).getAsBoolean())
    );


    //pickup position
     new JoystickButton(operator, 10).onTrue(
        ((new pidfShoulder(a_ShoulderSub, 10))
        .alongWith(new pidfElbow(a_elbowSub, 74))
        .alongWith(new pidfWrist(a_WristSub, -39)))
        .until(()-> new JoystickButton(operator, 3).getAsBoolean() || new JoystickButton(operator, 1).getAsBoolean() || new JoystickButton(operator, 4).getAsBoolean() || new JoystickButton(operator, 2).getAsBoolean())
     );


    



      
      //panic KILL IT ALL switch


    //yellow  
    new JoystickButton(driver,1).whileTrue(new candleRGB(m_candleSubsystem, 255, 255, 0, r1, g1, b1));
    //purple
    new JoystickButton(driver,2).whileTrue(new candleRGB(m_candleSubsystem, 221,160,221, r1, g1, b1));
     // new JoystickButton(driver, 9).onTrue(new pbrakeSHOULDER_ON(p_pPnuematicsSub));
     // new JoystickButton(driver, 10).onTrue(new pbrakeSHOULDER_OUT(p_pPnuematicsSub));
    
       
    }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    s_Swerve.resetBalance();
    Alliance alliance = DriverStation.getAlliance();
    if(alliance.equals(Alliance.Red)){
        r1 = 255;
        g1 = 0;
        b1 = 0;
      } else{
        r1 = 0;
        g1 = 0;
        b1 = 255;
      }
    m_candleSubsystem.setRGB(r1, b1, g1);
    return new newNewAuto(s_Swerve, a_ShoulderSub, a_elbowSub, a_WristSub, p_pPnuematicsSub);
    //return m_chooser.getSelected();
}

}