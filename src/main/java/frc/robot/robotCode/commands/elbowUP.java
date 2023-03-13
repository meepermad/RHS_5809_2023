//nedick addition 3/5

//import supporting packages. Note you need to import the correct subsystem
package frc.robot.robotCode.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.robotCode.subsystems.elbowSub;
import frc.robot.robotCode.commands.pbrakeELBOW_OFF;
import frc.robot.robotCode.commands.pbrakeELBOW_ON;
import frc.robot.robotCode.commands.pbrakeELBOW_OUT;
import frc.robot.robotCode.subsystems.pnuematicsSub;

public class elbowUP extends CommandBase {

    //any called variables in your subsytem go in here
  private final elbowSub elbowSub;
  private final double speed;
  private final double speedD;
  //private pnuematicsSub pnuematicsSub;
  
//create the command to move
//i'm setting it up to allow for a speed up and down to be passed no matter what.
// that's a bit kludge-y but I also don't particularlly mind it. Note that one of the
// two values (speed, speedD) needs to be zero (0) ANY time you send this command. 
  public elbowUP(elbowSub elbowSub, double speed, double speedD) {
    this.elbowSub = elbowSub;
    this.speed = speed;
    this.speedD = speedD;
    addRequirements(elbowSub);
 

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    elbowSub.initializeCounter();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    elbowSub.elUP(speed);
    //pnuematicsSub.p_elbowForward();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    elbowSub.elUP(0);
    elbowSub.brakeEL();
    //pnuematicsSub.p_elbowReverse();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return elbowSub.isSwitchSet();
  }
}
