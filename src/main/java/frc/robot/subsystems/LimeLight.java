package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.LimelightHelpers;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class LimeLight extends SubsystemBase {
  /** Creates a new LimeLight. */

  public LimeLight() {
    LimelightHelpers.setLEDMode_ForceOn("limelight");
  }

  public double getLimelightTX() {
    return LimelightHelpers.getTX("limelight");
  }

  public boolean getLimelightTV() {
    return LimelightHelpers.getTV("limelight");
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("LimelightX", getLimelightTX());
    SmartDashboard.putBoolean("LimelightY", getLimelightTV());
  }
}
