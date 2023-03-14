//Nendick addition 3/5
//these import the key library...things The most important is the vendordep for rev, which is up to date on 3/5
package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.robotCode.ConstantsAndConfigs.*;
//imported the whole of the constants because I had issues doing it the "right" way - this works fine, and it's not a huge size penalty
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;


public class shoulderSub extends SubsystemBase {
  //this creates the subsystem for the elbow
 
    CANSparkMax shoulderTOP = new CANSparkMax(Constants.armConstants.kshouldermotorTOP, MotorType.kBrushless);
    CANSparkMax shoulderBOT = new CANSparkMax(Constants.armConstants.kshouldermotorBOT, MotorType.kBrushless);
    //critical step that sets the sparkMax to the brushless. Naming reflects position on gearbox
    DigitalInput limitSwitch = Constants.Switches.shoulderSwitch;
    Counter counter = new Counter(limitSwitch);
    DutyCycleEncoder encoder;

    public shoulderSub(DutyCycleEncoder encoder){
      this.encoder = encoder;
    }

  public void shoulderUP(double speed){
    //this is the up command
    shoulderTOP.setIdleMode(CANSparkMax.IdleMode.kCoast);
    shoulderBOT.setIdleMode(CANSparkMax.IdleMode.kCoast);
   shoulderTOP.set(speed);
   shoulderBOT.set(speed);
  }

  public void shoulderDOWN(double speedD){
    //this is the down command
    shoulderTOP.setIdleMode(CANSparkMax.IdleMode.kCoast);
    shoulderBOT.setIdleMode(CANSparkMax.IdleMode.kCoast);
    shoulderTOP.set(-1*speedD);
    shoulderBOT.set(-1*speedD);

  }

  public void shoulderABS(double speedD){
    //this is the down command
    shoulderTOP.setIdleMode(CANSparkMax.IdleMode.kCoast);
    shoulderBOT.setIdleMode(CANSparkMax.IdleMode.kCoast);
    shoulderTOP.set(speedD * -.1);
    shoulderBOT.set(speedD * -.1);

  }

  
  public void brakeSH0(){

      shoulderBOT.set(0);
      shoulderTOP.set(0);
    shoulderBOT.setIdleMode(CANSparkMax.IdleMode.kBrake);
    shoulderTOP.setIdleMode(CANSparkMax.IdleMode.kBrake);


  }

  public boolean isSwitchSet(){
    return counter.get() > 0;
  }

  public void initializeCounter(){
    counter.reset();
  }

  public double getAngle(){
    double angle = encoder.getDistance() % 360;
    return Rotation2d.fromDegrees(angle % 360).getDegrees() / 5.0;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
