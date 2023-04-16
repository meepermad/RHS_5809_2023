// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.robotCode.Auto;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.robotCode.commands.*;
import frc.robot.robotCode.subsystems.ElbowSub;
import frc.robot.robotCode.subsystems.PnuematicsSub;
import frc.robot.robotCode.subsystems.ShoulderSub;
import frc.robot.robotCode.subsystems.Swerve;
import frc.robot.robotCode.subsystems.WristSub;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class oldAuto extends SequentialCommandGroup {
  /** Creates a new auto. */
  public oldAuto(Swerve swerve, ShoulderSub x, ElbowSub y, WristSub z, PnuematicsSub a) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    addCommands(
      Commands.race(new reset(swerve), new waitFor(1)),
      Commands.race(new pnuematicIntakeClawClose(a), new waitFor(0.5)),
      Commands.race(
        new pidfShoulder(x, 20),
        new pidfElbow(y, 110),
        new pidfWrist(z, -45),
        new waitFor(2)
      ),
      Commands.race(
        new newAutoSwerve(swerve, ()-> -0.4, ()-> 0.0, ()-> 0.0,()-> false).withTimeout(1),
        new pidfShoulder(x, 20),
        new pidfElbow(y, 110),
        new pidfWrist(z, -45),
        new waitFor(1.2)
      ),
      Commands.race(
        new pidfShoulder(x, 25),
        new pidfElbow(y, 105),
        new pidfWrist(z, -45),
        new waitFor(2)
      ),
      Commands.race(
        new pidfShoulder(x, 25),
        new pidfElbow(y, 105),
        new pidfWrist(z, -45),
        new pnuematicIntakeClawOpen(a),
        new waitFor(1.5)
      ),
      Commands.race(
        new pidfShoulder(x, 25),
        new pidfElbow(y, 105),
        new pidfWrist(z, -45),
        new newAutoSwerve(swerve, ()-> 0.35, ()-> 0.0, ()-> 0.0,()-> false).withTimeout(1),
        new waitFor(1.25)
      ),
      Commands.race(
        new pidfShoulder(x, 0),
        new pidfElbow(y, 0),
        new pidfWrist(z, -75),
        new waitFor(3)
      )
    );
  }
}
