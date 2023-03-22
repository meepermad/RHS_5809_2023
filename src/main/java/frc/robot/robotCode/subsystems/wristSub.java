// 3/5 nendick addition

//remember this system is a talon, so it has different code.
package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.robotCode.ConstantsAndConfigs.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.math.geometry.Rotation2d;

public class WristSub extends SubsystemBase {
  //Initilizing variable
  public WPI_TalonSRX WristMot = new WPI_TalonSRX(Constants.armConstants.kwristmotor);
  DigitalInput limitSwitch = Constants.Switches.wristSwitch;
  Counter counter = new Counter(limitSwitch);
  DutyCycleEncoder encoder;
  

  public WristSub(DutyCycleEncoder encoder) {
    this.encoder = encoder;
    WristMot.setInverted(true);
  }


  //This allows us to go up
  public void movewristUP(double upspeed){
    WristMot.setNeutralMode(NeutralMode.Coast);
    WristMot.set(upspeed);
  }

  //this allows us to go down
  public void movewristDOWN(double downspeed){
    WristMot.setNeutralMode(NeutralMode.Coast);
    WristMot.set(-1*downspeed);
  }

  //this allows us to go in any direction
  public void movewristABS(double downspeed){
    WristMot.setNeutralMode(NeutralMode.Coast);
    WristMot.set(downspeed * -.02);

  }

  public void brakeWrist(){
    WristMot.set(0);
    WristMot.setNeutralMode(NeutralMode.Brake);
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

  @Override
  public void periodic() {
  }
}
