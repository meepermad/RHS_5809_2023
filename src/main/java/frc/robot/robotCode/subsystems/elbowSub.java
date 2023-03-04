// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class elbowSub extends SubsystemBase {
  /** Creates a new elbowSub. */
 
    CANSparkMax elbow2 = new CANSparkMax(9, MotorType.kBrushless);
    CANSparkMax elbow1 = new CANSparkMax(24, MotorType.kBrushless);


    // yo create a path to the correct constant declaration but like for now..
    public elbowSub(){
      elbow1.setInverted(true);
    
  }

  public void movedatelho(double speed){

   elbow2.set(speed);
   elbow1.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
