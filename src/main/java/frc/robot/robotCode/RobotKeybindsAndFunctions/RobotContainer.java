// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.RobotKeybindsAndFunctions;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.robotCode.Auto.*;
import frc.robot.robotCode.commands.TeleopSwerve;
import frc.robot.robotCode.commands.autoBalance;
//import frc.robot.robotCode.subsystems.compressorSub;
import frc.robot.robotCode.subsystems.*;
import frc.robot.robotCode.commands.intakeWheelsSpinIn;
import frc.robot.robotCode.commands.intakeWheelsSpinOut;
import frc.robot.robotCode.commands.wristUp;
import frc.robot.robotCode.commands.elbowUp;
import frc.robot.robotCode.commands.shoulderUp;
import frc.robot.robotCode.commands.pidfElbow;
import frc.robot.robotCode.commands.pidfShoulder;
import frc.robot.robotCode.commands.pidfWrist;
import frc.robot.robotCode.commands.pnuematicIntakeClawClose;
import frc.robot.robotCode.commands.pnuematicIntakeClawOpen;
import frc.robot.robotCode.commands.reset;
//import frc.robot.robotCode.commands.candleRGB;
import frc.robot.robotCode.commands.changeOffsetElbow;
import frc.robot.robotCode.commands.*;




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
 public static double shoulderOffset = 0;

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

  private static final Command kDefaultAuto = new defaultBalanceAuto(s_Swerve, a_ShoulderSub, a_elbowSub, a_WristSub, p_pPnuematicsSub, a_intakeSub);
  private static final Command kOutOfBoundsAuto = new defaultOutOfBoundsAuto(s_Swerve, a_ShoulderSub, a_elbowSub, a_WristSub, p_pPnuematicsSub, a_intakeSub);
  private static final Command kNoMoveAuto = new defaultNoMoveAuto(s_Swerve, a_ShoulderSub, a_elbowSub, a_WristSub, p_pPnuematicsSub, a_intakeSub);
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  int r1,g1,b1;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Out of Bounds Auto", kOutOfBoundsAuto);
    m_chooser.addOption("No Move Auto", kNoMoveAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
      s_Swerve.setDefaultCommand(
          new TeleopSwerve(
              s_Swerve, 
              () -> -driver.getRawAxis(translationAxis), 
              () -> -driver.getRawAxis(strafeAxis), 
              () -> driver.getRawAxis(rotationAxis), 
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
      new JoystickButton(driver, 8).whileTrue(new shoulderUp(a_ShoulderSub, .2, 0));
      //new JoystickButton(operator, 2).whileTrue(new shoulderDOWN(a_ShoulderSub, 0, .1).alongWith(new pbrakeSHOULDER_OUT(p_pPnuematicsSub)));

      //this is the should up/down command OFF behavoid
      //new JoystickButton(operator, 1).onFalse(new brakeShoulder(a_ShoulderSub).alongWith(new pbrakeSHOULDER_ON(p_pPnuematicsSub)));
      //new JoystickButton(operator, 2).onFalse(new brakeShoulder(a_ShoulderSub).alongWith(new pbrakeSHOULDER_ON(p_pPnuematicsSub)));
      //new JoystickButton(driver, 11).whileTrue(new pidfShoulder(a_ShoulderSub, 2.5));
      //new JoystickButton(driver, 12).whileTrue(new pidfShoulder(a_ShoulderSub, 25));
     
      //this is the elbow up/down command ON behavior
      new JoystickButton(driver, 10).whileTrue(new elbowUp(a_elbowSub, .2, 0));
      //new JoystickButton(operator, 3).whileTrue(new elbowDOWN(a_elbowSub, 0, .2).alongWith(new pbrakeELBOW_OUT(p_pPnuematicsSub)));

      //this is the elbow up/down command OFF behavoir
      //new JoystickButton(operator, 4).onFalse(new brakeElbow(a_elbowSub).alongWith(new pbrakeELBOW_ON(p_pPnuematicsSub)));
      //new JoystickButton(operator, 3).onFalse(new brakeElbow(a_elbowSub).alongWith(new pbrakeELBOW_ON(p_pPnuematicsSub)));
      //new JoystickButton(driver, 4).whileTrue(new pidfElbow(a_elbowSub, 5));
      //new JoystickButton(driver, 6).whileTrue(new pidfElbow(a_elbowSub, 35));



      //this is the wrist up/down command ON behavoir
      new JoystickButton(driver, 9).whileTrue(new wristUp(a_WristSub, .3, 0));
      //new JoystickButton(operator, 12).whileTrue(new wristDOWN(a_WristSub, 0, .1)); 
      
      //this is the wrist up/down command OFF behavoir
      /*new JoystickButton(operator, 5).onFalse(new brakeWrist(a_WristSub));
      new JoystickButton(operator, 6).onFalse(new brakeWrist(a_WristSub));
      new JoystickButton(operator, 5).onFalse(new wristUP(a_WristSub, 0, 0).andThen(new TestCommand()));
      new JoystickButton(operator, 6).onFalse(new wristDOWN(a_WristSub, 0, 0).andThen(new TestCommand()));*/
      //new JoystickButton(driver, 5).whileTrue(new pidfWrist(a_WristSub, -70));




      //this is the intake IN/OUT command ON behavoir
      new JoystickButton(operator, 5).whileTrue(new intakeWheelsSpinIn(a_intakeSub, .85, 0));
      new JoystickButton(operator, 6).whileTrue(new intakeWheelsSpinOut(a_intakeSub, 0, .85));
      new JoystickButton(driver, 5).whileTrue(new intakeWheelsSpinOut(a_intakeSub, 0, .85));

      //this is the intake IN/OUT command OFF behavoir
      //new JoystickButton(driver, 9).onFalse(new intakeIN(a_intakeSub, .0, 0));
      //new JoystickButton(driver, 10).onFalse(new intakeOUT(a_intakeSub, .0, 0));

      new JoystickButton(operator, 11).onTrue(new changeOffsetElbow(-2));
      new JoystickButton(operator, 12).onTrue(new changeOffsetElbow(2));

      new JoystickButton(driver, 12).whileTrue(new reset(s_Swerve));

      new JoystickButton(driver, 11).whileTrue(new autoBalance(s_Swerve));

    
      //this is the intake IN/OUT command ON behavoir
      new JoystickButton(operator, 7).whileTrue(new pnuematicIntakeClawClose(p_pPnuematicsSub));
      new JoystickButton(operator, 8).onTrue(
        new changeOffsetShoulder(2)
    );

    new JoystickButton(operator, 9).whileTrue(new pnuematicIntakeClawOpen(p_pPnuematicsSub));
    new JoystickButton(driver, 5).whileTrue(new pnuematicIntakeClawOpen(p_pPnuematicsSub));
    


    //low scoring position
    new JoystickButton(operator, 2).onTrue(
      ((new pidfShoulder(a_ShoulderSub, 42))
      .alongWith(new pidfElbow(a_elbowSub, -53))
      .alongWith(new pidfWrist(a_WristSub, 48)))
        .until(()-> new JoystickButton(operator, 3).getAsBoolean() || new JoystickButton(operator, 1).getAsBoolean() || new JoystickButton(operator, 4).getAsBoolean() || new JoystickButton(operator, 10).getAsBoolean() || new JoystickButton(operator, 8).getAsBoolean())
    );

     // mid scoring positon
     new JoystickButton(operator, 3).onTrue(
        ((new pidfShoulder(a_ShoulderSub, 17))
        .alongWith(new pidfElbow(a_elbowSub, -100))
        .alongWith(new pidfWrist(a_WristSub, 98)))
        .until(()-> new JoystickButton(operator, 2).getAsBoolean() || new JoystickButton(operator, 1).getAsBoolean() || new JoystickButton(operator, 4).getAsBoolean() || new JoystickButton(operator, 10).getAsBoolean() || new JoystickButton(operator, 8).getAsBoolean())
    );

    // high scoring position
    new JoystickButton(operator, 4).onTrue(
        ((new pidfShoulder(a_ShoulderSub, 38))
        .alongWith(new pidfElbow(a_elbowSub, -157))
        .alongWith(new pidfWrist(a_WristSub, 109)))
        .until(()-> new JoystickButton(operator, 3).getAsBoolean() || new JoystickButton(operator, 1).getAsBoolean() || new JoystickButton(operator, 2).getAsBoolean() || new JoystickButton(operator, 10).getAsBoolean() || new JoystickButton(operator, 8).getAsBoolean())
    );

    // driving/home postion
    new JoystickButton(operator, 1).onTrue(
        ((new pidfShoulder(a_ShoulderSub, -7))
        .alongWith(new pidfElbow(a_elbowSub, -1.2))
        .alongWith(new pidfWrist(a_WristSub, -5)))
        .until(()-> new JoystickButton(operator, 3).getAsBoolean() || new JoystickButton(operator, 2).getAsBoolean() || new JoystickButton(operator, 4).getAsBoolean() || new JoystickButton(operator, 10).getAsBoolean() || new JoystickButton(operator, 8).getAsBoolean())
    );


    //single intake
     new JoystickButton(operator, 10).onTrue(
        ((new pidfShoulder(a_ShoulderSub, 24))
        .alongWith(new pidfElbow(a_elbowSub, -19))
        .alongWith(new pidfWrist(a_WristSub, -79)))
        .until(()-> new JoystickButton(operator, 3).getAsBoolean() || new JoystickButton(operator, 1).getAsBoolean() || new JoystickButton(operator, 4).getAsBoolean() || new JoystickButton(operator, 2).getAsBoolean() || new JoystickButton(operator, 8).getAsBoolean())
     );


     //double intake
     new JoystickButton(operator, 8).onTrue(
        ((new pidfShoulder(a_ShoulderSub, 30))
        .alongWith(new pidfElbow(a_elbowSub, -129))
        .alongWith(new pidfWrist(a_WristSub, 119)))
        .until(()-> new JoystickButton(operator, 3).getAsBoolean() || new JoystickButton(operator, 1).getAsBoolean() || new JoystickButton(operator, 4).getAsBoolean() || new JoystickButton(operator, 2).getAsBoolean() || new JoystickButton(operator, 10).getAsBoolean())
     );
    



      
      //panic KILL IT ALL switch


    //purple  
    new JoystickButton(driver,1).whileTrue(new candleRGB(m_candleSubsystem, 255, 255, 0, r1, g1, b1));
    //yellow
    new JoystickButton(driver,2).whileTrue(new candleRGB(m_candleSubsystem, 221,0 ,221, r1, g1, b1));
    //  new JoystickButton(driver, 9).onTrue(new pbrakeSHOULDER_ON(p_pPnuematicsSub));
    //  new JoystickButton(driver, 10).onTrue(new pbrakeSHOULDER_OUT(p_pPnuematicsSub));
    
       
    }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
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
    s_Swerve.resetBalance();
    return m_chooser.getSelected();
    //return new balanceAuto(s_Swerve, a_ShoulderSub, a_elbowSub, a_WristSub, p_pPnuematicsSub);
    //return m_chooser.getSelected();
}

}