//Atem addition 3/5
//these import the key library...things The most important is the vendordep for rev, which is up to date on 3/5
package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.robotCode.ConstantsAndConfigs.*;
//imported the whole of the constants because I had issues doing it the "right" way - this works fine, and it's not a huge size penalty
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class pShoulderBrakeSub extends SubsystemBase {
    CANSparkMax shoulderTOP = new CANSparkMax(Constants.armConstants.kshouldermotorTOP, MotorType.kBrushless);
    CANSparkMax shoulderBOT = new CANSparkMax(Constants.armConstants.kshouldermotorBOT, MotorType.kBrushless);

    public pShoulderBrakeSub(){}

    public void brakeSH(){
    shoulderBOT.setIdleMode(CANSparkMax.IdleMode.kBrake);
    shoulderTOP.setIdleMode(CANSparkMax.IdleMode.kBrake);
  }
}