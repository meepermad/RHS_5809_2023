package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;


public class pShoulderSub extends SubsystemBase {


DoubleSolenoid pShoulderBrake = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 15);
  /** Creates a new pShoulderSub. */
  public pShoulderSub() {}


  public void p_shoulderForward(){
    //this is the up command

   pShoulderBrake.set(Value.kForward);
  }

  
  public void p_shoulderReverse(){
    //this is the up command

   pShoulderBrake.set(Value.kReverse);
  }

  
  public void p_shoulderOFF(){
    //this is the up command

   pShoulderBrake.set(Value.kOff);
  }




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}


