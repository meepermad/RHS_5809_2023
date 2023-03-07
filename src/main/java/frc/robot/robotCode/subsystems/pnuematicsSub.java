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
DoubleSolenoid m_shoulder_solenoid = m_pH.makeDoubleSolenoid(4, 12);

  /** Creates a new pShoulderSub. */
  public pnuematicsSub() {}


  public void p_shoulderForward(){
    //this is the up command

    m_shoulder_solenoid.set(DoubleSolenoid.Value.kForward);

  }

  
  public void p_shoulderReverse(){

    m_shoulder_solenoid.set(DoubleSolenoid.Value.kReverse);

    //this is the up command

  }

  
  public void p_shoulderOFF(){

    m_shoulder_solenoid.set(DoubleSolenoid.Value.kOff);
    //this is the up command

  }




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}


