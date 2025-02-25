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
  public double rotPIDValue = 0;
  public double rotPIDSetpoint = 0;
  public double SnapPIDValue = 0;

  public double tx = LimelightHelpers.getTX("limelight");
  public double error = LimelightHelpers.getTY("limelight");
  public boolean tv = LimelightHelpers.getTV("limelight");

  public LimeLight() {
    LimelightHelpers.setLEDMode_ForceOn("limelight");
  }

  public double getLimelightTX() {
    return LimelightHelpers.getTX("limelight");
  }

  public boolean getLimelightTV() {
    return LimelightHelpers.getTV("limelight");
  }

  public void limelightPid(){
    double translationSup = 0;
    double strafeSup = 0;
    double rotationSup = 0;
    double translationValue = MathUtil.applyDeadband(translationSup * 1, Constants.stickDeadband);
    double strafeValue = MathUtil.applyDeadband(strafeSup * 1, Constants.stickDeadband);
    double rotationValue = -MathUtil.applyDeadband(rotationSup * 1, Constants.stickDeadband) + rotPIDValue + SnapPIDValue;

    RobotContainer.swerve.drive(new Translation2d(translationValue, strafeValue).times(Constants.Swerve.maxSpeed), rotationValue * Constants.Swerve.maxAngularVelocity - error, true, true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("LimelightX", getLimelightTX());
    SmartDashboard.putBoolean("LimelightY", getLimelightTV());
  }
}

// F) Joystickten 1 tuşuna basıldığında bu pid çalışacak setpoint 0 olana kadar
