package frc.robot.robotCode.commands;

import frc.robot.robotCode.RobotKeybindsAndFunctions.*;
import frc.robot.robotCode.subsystems.LimeLight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 *
 */
public class LimeVisionCommand extends CommandBase {

    public LimeVisionCommand() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.limeVisionSubsystem);
    }

    private void requires(LimeLight limevisionsubsystem) {
    }

    // Called just before this Command runs the first time
    public void initialize() {
    	if(Robot.limeVisionSubsystem.getLEDMode() == 1) {
    		Robot.limeVisionSubsystem.switchLED();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    	if(RobotContainer.driver.getRawButtonPressed(7)) {
    		if(Robot.limeVisionSubsystem.getPipeline() == 0)
    		Robot.limeVisionSubsystem.setPipeline(1);
    		else {
        	Robot.limeVisionSubsystem.setPipeline(0);
    		}
    	}
    	//System.out.println(Robot.limeVisionSubsystem.getXOffset());
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    public void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    public void interrupted() {
    	if(Robot.limeVisionSubsystem.getLEDMode() == 0) {
    		Robot.limeVisionSubsystem.switchLED();
    	}
    }

    
    public void teleopPeriodic(){
        // get the default instance of NetworkTables
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        // get a reference to the subtable called "datatable"
        NetworkTable table = inst.getTable("limelight");
         
        inst.startClient4("5809"); // Make sure you set this to your team number
         
        inst.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS
         
        // NetworkTableEntry TeamEntry = table.getEntry("tx");
        NetworkTableEntry xEntry = table.getEntry("tx");
        NetworkTableEntry yEntry = table.getEntry("ty");
        NetworkTableEntry aEntry = table.getEntry("ta");
        NetworkTableEntry lEntry = table.getEntry("tl");
        NetworkTableEntry vEntry = table.getEntry("tv");
        NetworkTableEntry sEntry = table.getEntry("ts");
         
        NetworkTableEntry tshortEntry = table.getEntry("tshort");
        NetworkTableEntry tlongEntry = table.getEntry("tlong");
        NetworkTableEntry thorEntry = table.getEntry("thor");
        NetworkTableEntry tvertEntry = table.getEntry("tvert");
        NetworkTableEntry getpipeEntry = table.getEntry("getpipe");
        NetworkTableEntry camtranEntry = table.getEntry("camtran");
        NetworkTableEntry ledModeEntry = table.getEntry("ledMode");
         
        // double tx = xEntry.getDouble(0.0);
        double tx = xEntry.getDouble(0.0); // Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
        double ty = yEntry.getDouble(0.0); // Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
        double ta = aEntry.getDouble(0.0); // Target Area (0% of image to 100% of image)
        double tl = lEntry.getDouble(0.0); // The pipeline’s latency contribution (ms) Add at least 11ms for image capture
                                           // latency.
        double tv = vEntry.getDouble(0.0); // Whether the limelight has any valid targets (0 or 1)
        double ts = sEntry.getDouble(0.0); // Skew or rotation (-90 degrees to 0 degrees)
         
        // double tshort = tshortEntry.getString(); // Sidelength of shortest side of
        // the fitted bounding box (pixels)
        // double tlong = tlong // Sidelength of longest side of the fitted bounding box
        // (pixels)
        // double thor = thor // Horizontal sidelength of the rough bounding box (0 -
        // 320 pixels)
        // double tvert = tvert // Vertical sidelength of the rough bounding box (0 -
        // 320 pixels)
        // double getpipe = getpipe // True active pipeline index of the camera (0 .. 9)
        // double camtran = camtran // Results of a 3D position solution, 6 numbers:
        // Translation (x,y,y) Rotation(pitch,yaw,roll)
         
        //ledModeEntry.setNumber(0); // use the LED Mode set in the current pipeline
        ledModeEntry.setNumber(1); // force off
        //ledModeEntry.setNumber(2); // force blink
        //ledModeEntry.setNumber(3); // force on
         
        //System.out.println("X: " + tx);
        //System.out.println("Y: " + ty);
        //System.out.println("A: " + ta);
        //System.out.println("L: " + tl);
        //System.out.println("V: " + tv);
        //System.out.println("S: " + tv);
         
        // post to smart dashboard periodically
        SmartDashboard.putNumber("Limelight X", tx);
        SmartDashboard.putNumber("Limelight Y", ty);
        SmartDashboard.putNumber("Limelight Area", ta);
        SmartDashboard.putNumber("Limelight Latency", tl);
        SmartDashboard.putNumber("Limelight Valid Target", tv);
        SmartDashboard.putNumber("Limelight Skew", ts);
         
        // Limelight Data End
    }
}