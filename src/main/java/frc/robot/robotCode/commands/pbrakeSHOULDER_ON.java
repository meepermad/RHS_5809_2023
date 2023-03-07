
//import libraries; the key thin is we need all the commands
package frc.robot.robotCode.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.robotCode.subsystems.pShoulderSub;


//this tells the rio to make the commmand name "pbrakeSHOULDER"
public class pbrakeSHOULDER_ON extends CommandBase {

  private final pShoulderSub pShoulderSub;

  public pbrakeSHOULDER_ON(pShoulderSub pShoulderSub) {
    this.pShoulderSub = pShoulderSub;
    addRequirements(pShoulderSub);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

      pShoulderSub.p_shoulderForward();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    pShoulderSub.p_shoulderOFF();
     }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  } 
}
  

