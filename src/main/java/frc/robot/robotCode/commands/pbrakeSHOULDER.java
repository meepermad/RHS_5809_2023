
//import libraries; the key thin is we need all the commands
package frc.robot.robotCode.commands;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import 

//this tells the rio to make the commmand name "pbrakeSHOULDER"
public class pbrakeSHOULDER extends CommandBase {
  /** Creates a new armmove. */

  //this declares variables for the solenoid and the time it actuates 
  double startTime;
  DoubleSolenoid p_shoulder_piston;

  //this makes a command we can call later
  public void p_S_BRAKE (DoubleSolenoid p_shoulder_piston) {
    startTime = System.currentTimeMillis(); 
    this.p_shoulder_piston = p_shoulder_piston;


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Once pressed, pull arms in
    this.m_shoulder_piston
      this.m_S2.set(DoubleSolenoid.Value.kReverse);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //Once pressed, leave arms the way they are
    this.m_S1.set(DoubleSolenoid.Value.kOff);
    this.m_S2.set(DoubleSolenoid.Value.kOff);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
