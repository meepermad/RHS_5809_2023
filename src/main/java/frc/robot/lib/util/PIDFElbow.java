package frc.robot.lib.util;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Custom PID Controller
 */
public class PIDFElbow extends PIDController {

    private double kF;

    public PIDFElbow(String name, double kP, double kI, double kD, double kF, boolean enabled) {
        super(kP, kI, kD);
        this.kF = kF;
    }

    public PIDFElbow(String name, double kP, double kI, double kD, double kF) {
        this(name, kP, kI, kD, kF, true);
    }

    public double getFeedForward() {
        return kF;
    }

    public void setFeedForward(double kF) {
        this.kF = kF;
    }

    /**
     * Returns the next output of the PID controller.
     *
     * @param measurement The current measurement of the process variable.
     */
    @Override
    public double calculate(double measurement) {
        return kF + super.calculate(measurement);
    }

    /**
     * Returns the next output of the PID controller.
     *
     * @param measurement The current measurement of the process variable.
     * @param setpoint    The new setpoint of the controller.
     */
    @Override
    public double calculate(double measurement, double setpoint) {
        // Set setpoint to provided value
        super.setSetpoint(setpoint);
        return kF + super.calculate(measurement);
    }

    public void debug() {
        SmartDashboard.putData(this);

    }

}