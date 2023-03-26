package frc.robot.robotCode.subsystems;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.robotCode.ConstantsAndConfigs.Constants;
import frc.robot.robotCode.ConstantsAndConfigs.SwerveModule;
import frc.robot.robotCode.RobotKeybindsAndFunctions.RobotContainer;

public class Swerve extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    public Pigeon2 gyro;
    private PIDController balancePID = new PIDController(0.014, 0, .008);
	private double oldPitch;
    private double time;
    private SwerveModuleState[] m_desiredStates;

    public Swerve() {
        //Assigning variables
        gyro = new Pigeon2(Constants.Swerve.pigeonID);
        gyro.configFactoryDefault();
        zeroGyro();

        //Getting each wheel
        mSwerveMods = new SwerveModule[] {
            new SwerveModule(0, Constants.Swerve.Mod0.constants),
            new SwerveModule(1, Constants.Swerve.Mod1.constants),
            new SwerveModule(2, Constants.Swerve.Mod2.constants),
            new SwerveModule(3, Constants.Swerve.Mod3.constants)
        };

        Timer.delay(1.0);
        resetModulesToAbsolute();

        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getYaw(), getModulePositions());
    }

    public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates =
            Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
                                    -(Math.pow(1.5,translation.getX())-1)* Constants.JoysticksSensitivitys.moveSensitivity * RobotContainer.sensitivityAxis, 
                                    -(Math.pow(1.5,translation.getY())-1)* Constants.JoysticksSensitivitys.moveSensitivity * RobotContainer.sensitivityAxis, 
                                    rotation* Constants.JoysticksSensitivitys.rotationSensitivity, 
                                    getYaw()
                                )
                                : new ChassisSpeeds(
                                    (Math.pow(1.5,translation.getX())-1)* Constants.JoysticksSensitivitys.moveSensitivity * RobotContainer.sensitivityAxis, 
                                    (Math.pow(1.5,translation.getY())-1)* Constants.JoysticksSensitivitys.moveSensitivity * RobotContainer.sensitivityAxis, 
                                    rotation* Constants.JoysticksSensitivitys.rotationSensitivity)
                                );
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }    

    public void drive(ChassisSpeeds chassisSpeeds) {
		if (m_desiredStates != null && chassisSpeeds.vxMetersPerSecond == 0 && chassisSpeeds.vyMetersPerSecond == 0
				&& chassisSpeeds.omegaRadiansPerSecond == 0) {
			m_desiredStates[0].speedMetersPerSecond = 0;
			m_desiredStates[1].speedMetersPerSecond = 0;
			m_desiredStates[2].speedMetersPerSecond = 0;
			m_desiredStates[3].speedMetersPerSecond = 0;
		} else {
			m_desiredStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(chassisSpeeds);
		}
		setModuleStates(m_desiredStates,true);
	}



    /* Used by SwerveControllerCommand in Auto */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], false);
        }
    }    

    public void setModuleStates(SwerveModuleState[] desiredStates, boolean isOpenLoop) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(desiredStates[mod.moduleNumber], isOpenLoop);
        }
    }   

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }

    public void resetOdometry(Pose2d pose) {
        swerveOdometry.resetPosition(getYaw(), getModulePositions(), pose);
    }

    public SwerveModuleState[] getModuleStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : mSwerveMods){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    public SwerveModulePosition[] getModulePositions(){
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for(SwerveModule mod : mSwerveMods){
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public void zeroGyro(){
        gyro.setYaw(0);
    }

    public Rotation2d getYaw() {
        return (Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(360 - gyro.getYaw()) : Rotation2d.fromDegrees(gyro.getYaw());
    }

    public void resetModulesToAbsolute(){
        for(SwerveModule mod : mSwerveMods){
            mod.resetToAbsolute();
        }
    }

    public Rotation2d getPitchR2d() {
        return Rotation2d.fromDegrees(gyro.getPitch());
    }

    public void balance() {
		double pitch = getPitchR2d().getDegrees();
		boolean better =  (Math.abs(pitch) < Math.abs(oldPitch)  && Math.abs(pitch) < 9) || (Math.signum(pitch) != Math.signum(oldPitch));
		boolean waiting = time != 00 && time+0.1 > Timer.getFPGATimestamp();
		if (waiting ){
			drive(0,0,0);
		} else if (better) {
			//it is getting better so wait.
			time = Timer.getFPGATimestamp();
			drive(0,0,0);
		} else {
			//drive 
			double xPower = MathUtil.clamp(balancePID.calculate(pitch), -0.15, 0.15);
			drive(-xPower, 0, 0);
		}
		oldPitch = pitch;
	}

	public void resetBalance(){
		balancePID.setSetpoint(0);
		balancePID.setTolerance(2);
		balancePID.reset();
	}

    public Rotation2d getYawR2d() {
		return getPose().getRotation();
		//return Rotation2d.fromDegrees(m_pigeon.getYaw());
	}

	public void drive(double x, double y, double rotation) {
		SwerveModuleState[] swerveModuleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(
				x * Constants.Swerve.maxSpeed,
				y * Constants.Swerve.maxSpeed,
				rotation * Constants.Swerve.maxAngularVelocity,
				getYawR2d()));

        setModuleStates(swerveModuleStates);
	}

public void setX(){
    mSwerveMods[0].setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)), true);
    mSwerveMods[1].setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)), true);
    mSwerveMods[2].setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)), true);
    mSwerveMods[3].setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)), true);

}

    @Override
    public void periodic(){
        if (DriverStation.isDisabled()){
            resetModulesToAbsolute();
        }

        swerveOdometry.update(getYaw(), getModulePositions());
    }
}