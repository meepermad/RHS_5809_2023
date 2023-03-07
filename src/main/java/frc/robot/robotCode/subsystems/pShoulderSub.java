package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;


public class pShoulderSub extends SubsystemBase {


DoubleSolenoid pShoulderBrake = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 0)
  /** Creates a new pShoulderSub. */
  public pShoulderSub() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}


