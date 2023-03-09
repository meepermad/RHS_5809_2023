package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.robotCode.ConstantsAndConfigs.*;
import frc.robot.robotCode.ConstantsAndConfigs.Constants.pConstants;




public class pnuematicsSub extends SubsystemBase {

PneumaticHub m_pH = new PneumaticHub(pConstants.kPhub);
DoubleSolenoid m_shoulder_solenoid = m_pH.makeDoubleSolenoid(3, 14);
DoubleSolenoid m_elbow_solenoid = m_pH.makeDoubleSolenoid(5, 8);
DoubleSolenoid m_intake_solenoid = m_pH.makeDoubleSolenoid(10, 7);

  /** Creates a new pShoulderSub. */
  public pnuematicsSub() {}

  
  public void p_shoulderForward(){
    //this is the up commandh

    m_shoulder_solenoid.set(DoubleSolenoid.Value.kForward);

  }

  public void p_elbowForward(){
    
    m_elbow_solenoid.set(DoubleSolenoid.Value.kForward);

  }

  public void p_shoulderReverse(){

    m_shoulder_solenoid.set(DoubleSolenoid.Value.kReverse);

    //this is the up command

  }

  public void p_elbowReverse(){

    m_elbow_solenoid.set(DoubleSolenoid.Value.kReverse);

    //this is the up command

  }

  
  public void p_shoulderOFF(){

    m_shoulder_solenoid.set(DoubleSolenoid.Value.kOff);
    //this is the up command

  }

  public void p_elbowOFF(){

    m_elbow_solenoid.set(DoubleSolenoid.Value.kOff);
    //this is the up command

  }

  public void p_intakeOFF(){

    m_intake_solenoid.set(DoubleSolenoid.Value.kOff);
    //this is the up command

  }

  public void p_intakeReverse(){

    m_intake_solenoid.set(DoubleSolenoid.Value.kReverse);

    //this is the up command

  }

  public void p_intakeForward(){

    m_intake_solenoid.set(DoubleSolenoid.Value.kForward);

    //this is the up command

  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}


