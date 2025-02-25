// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Swerve;

public class RobotContainer {
  public static Swerve swerve = new Swerve();

  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`,
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
