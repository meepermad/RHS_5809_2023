
//import libraries; the key thin is we need all the commands
package frc.robot.robotCode.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.robotCode.subsystems.PnuematicsSub;


//this tells the rio to make the commmand name "pbrakeSHOULDER"
public class pnuematicBrakeElbowDisengage extends CommandBase {

  private final PnuematicsSub pnuematicsSub;
  private boolean x;

  public pnuematicBrakeElbowDisengage(PnuematicsSub pnuematicsSub) {
    this.pnuematicsSub = pnuematicsSub;
    addRequirements(pnuematicsSub);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    x = true;
    pnuematicsSub.p_elbowReverse();
    x = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    //pnuematicsSub.p_elbowOFF();
     }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return x;
  } 
}
  

