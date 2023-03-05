//Nendick addition 3/5
//these import the key library...things The most important is the vendordep for rev, which is up to date on 3/5
package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.robotCode.ConstantsAndConfigs.*;
//imported the whole of the constants because I had issues doing it the "right" way - this works fine, and it's not a huge size penalty
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class shoulderSub extends SubsystemBase {
  //this creates the subsystem for the elbow
 
    CANSparkMax shoulderTOP = new CANSparkMax(Constants.armConstants.kshouldermotorTOP, MotorType.kBrushless);
    CANSparkMax shoulderBOT = new CANSparkMax(Constants.armConstants.kshouldermotorBOT, MotorType.kBrushless);
    //critical step that sets the sparkMax to the brushless. Naming reflects position on gearbox

    public shoulderSub(){
    
  }

  public void shoulderUP(double speed){
    //this is the up command
   shoulderTOP.set(speed);
   shoulderBOT.set(speed);
  }

  public void shoulderDOWN(double speedD){
    //this is the down command
    shoulderTOP.set(-1*speedD);
    shoulderBOT.set(-1*speedD);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
