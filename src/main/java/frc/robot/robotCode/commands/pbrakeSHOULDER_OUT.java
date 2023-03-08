
//import libraries; the key thin is we need all the commands
package frc.robot.robotCode.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.robotCode.subsystems.pnuematicsSub;


//this tells the rio to make the commmand name "pbrakeSHOULDER"
public class pbrakeSHOULDER_OUT extends CommandBase {

  private final pnuematicsSub pnuematicsSub;

  public pbrakeSHOULDER_OUT(pnuematicsSub pnuematicsSub) {
    this.pnuematicsSub = pnuematicsSub;
    addRequirements(pnuematicsSub);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      pnuematicsSub.p_shoulderReverse();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    //pnuematicsSub.p_shoulderOFF();
     }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  } 
}
  

