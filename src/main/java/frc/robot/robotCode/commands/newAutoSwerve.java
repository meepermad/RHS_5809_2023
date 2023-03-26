package frc.robot.robotCode.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import frc.robot.robotCode.subsystems.Swerve;


public class newAutoSwerve extends CommandBase {    
    private Swerve s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier robotCentricSup;

    public newAutoSwerve(Swerve s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, DoubleSupplier rotationSup, BooleanSupplier robotCentricSup) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);

        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.robotCentricSup = robotCentricSup;
    }

    @Override
    public void execute() {
        /* Get Values, Deadband*/
        double translationVal = MathUtil.applyDeadband(translationSup.getAsDouble(), Constants.JoysticksSensitivitys.stickDeadband);
        double strafeVal = MathUtil.applyDeadband(strafeSup.getAsDouble(), Constants.JoysticksSensitivitys.stickDeadband);
        double rotationVal = MathUtil.applyDeadband(rotationSup.getAsDouble() , Constants.JoysticksSensitivitys.stickDeadband);

        /* Drive */
        s_Swerve.drive(
            new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed), 
            rotationVal * Constants.Swerve.maxAngularVelocity, 
            !robotCentricSup.getAsBoolean(), 
            true
        );
    }

    @Override
  public void end(boolean interrupted) {
    /* Drive */
    s_Swerve.drive(
        new Translation2d(0, 0).times(Constants.Swerve.maxSpeed), 
        0 * Constants.Swerve.maxAngularVelocity, 
        !robotCentricSup.getAsBoolean(), 
        true
    );
    
  }

    
}