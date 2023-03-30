//Nendick addition 3/5

//these import the key library...things The most important is the vendordep for rev, which is up to date on 3/5
package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.robotCode.ConstantsAndConfigs.*;
import frc.robot.robotCode.RobotKeybindsAndFunctions.RobotContainer;

//imported the whole of the constants because I had issues doing it the "right" way - this works fine, and it's not a huge size penalty
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.DutyCycleEncoder;


public class ElbowSub extends SubsystemBase {
  //this creates the subsystem for the elbow

  DigitalInput limitSwitch = Constants.Switches.elbowSwitch;
  Counter counter = new Counter(limitSwitch);
  DutyCycleEncoder encoder;
 
    CANSparkMax elbowA = new CANSparkMax(Constants.armConstants.kelbowmotor, MotorType.kBrushless);
    //critical step that sets the sparkMax to the brushless

    public ElbowSub(DutyCycleEncoder encoder){
      this.encoder = encoder;
      elbowA.setInverted(false);
    
  }

  public void elUP(double speed){
    //this is the up command
    elbowA.setIdleMode(CANSparkMax.IdleMode.kCoast);
   elbowA.set(speed);
  }

  public void elDOWN(double speedD){
    //this is the down command
    elbowA.setIdleMode(CANSparkMax.IdleMode.kCoast);
    elbowA.set(-1*speedD);

  }

  public void elABS(double speedD){
    //this is the down command
    elbowA.setIdleMode(CANSparkMax.IdleMode.kCoast);
    elbowA.set(speedD * -.1);

  }

  public void brakeEL(){

    elbowA.setIdleMode(CANSparkMax.IdleMode.kBrake);
    elbowA.set(0);
  }

  public boolean isSwitchSet(){
    return counter.get() > 0;
  }

  public void initializeCounter(){
    counter.reset();
  }

  public double getAngle(){
    double angle = encoder.getDistance() % 360;
    return Rotation2d.fromDegrees(angle % 360).getDegrees();
  }

  public double getOffset(){
    return RobotContainer.elbowOffset;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
