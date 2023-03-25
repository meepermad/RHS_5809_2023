//Nendick addition 3/5
//these import the key library...things The most important is the vendordep for rev, which is up to date on 3/5
package frc.robot.robotCode.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import frc.robot.robotCode.ConstantsAndConfigs.*;
//imported the whole of the constants because I had issues doing it the "right" way - this works fine, and it's not a huge size penalty
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


public class IntakeSub extends SubsystemBase {
  //this creates the subsystem for the elbow
 
  //right denotes the motor on the right, looking along the arm(right from the robot perspective)
    CANSparkMax intakeLeftMotor = new CANSparkMax(Constants.armConstants.kintakemotorB, MotorType.kBrushed);
    //CANSparkMax intakeRightMotor = new CANSparkMax(Constants.armConstants.kintakemotorA, MotorType.kBrushed);
    WPI_TalonSRX intakeRightMotor = new WPI_TalonSRX(Constants.armConstants.kintakemotorA);
    //critical step that sets the sparkMax to the brushless. Naming reflects position on gearbox

    public IntakeSub(){
    //  intakeLeftMotor.setInverted(true);
    //idk why but the above command isn't working, so anyway 
  
  }

  public void intakeNOM(double intakespeed){
    //this is the up command

   //intakeRightMotor.set(intakespeed);
   //kludge, where I manually invert leftmotor
   intakeLeftMotor.set(-1*intakespeed);
   intakeRightMotor.setNeutralMode(NeutralMode.Coast);
    intakeRightMotor.set(-intakespeed);

  }

  public void intakeVOM(double intakevomspeed){
    //this is the down command
     //kludge, where I manually invert leftmotor

   intakeLeftMotor.set(intakevomspeed);
   intakeRightMotor.setNeutralMode(NeutralMode.Coast);
    intakeRightMotor.set(intakevomspeed);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
