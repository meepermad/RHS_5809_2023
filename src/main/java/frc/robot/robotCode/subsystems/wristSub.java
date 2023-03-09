// 3/5 nendick addition

//remember this system is a talon, so it has different code.
package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.robotCode.ConstantsAndConfigs.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class wristSub extends SubsystemBase {

  public WPI_TalonSRX WristMot = new WPI_TalonSRX(Constants.armConstants.kwristmotor);
  


//this allows us to go up, down
  public wristSub() {
    WristMot.setInverted(true);
  }

  public void movewristUP(double upspeed){
    WristMot.setNeutralMode(NeutralMode.Coast);
    WristMot.set(upspeed);
   
  }

  public void movewristDOWN(double downspeed){
    WristMot.setNeutralMode(NeutralMode.Coast);
    WristMot.set(-1*downspeed);

  }

  public void brakeWrist(){
    WristMot.set(0);
    WristMot.setNeutralMode(NeutralMode.Brake);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
