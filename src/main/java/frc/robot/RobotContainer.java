package frc.robot;

import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {
  PS5Controller kumanda = new PS5Controller(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {}

    public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}