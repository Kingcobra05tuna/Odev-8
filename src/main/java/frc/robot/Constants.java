package frc.robot;

import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

import frc.lib.util.COTSTalonFXSwerveConstants;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {
  public static final String CANivoresCanBus = "CANivores";
  public static final double stickDeadband = 0.06;

  public static final class Limelight {
    public static final String kLimeName = "limelight-colar";
  }

  public static final class Swerve {
    public static final int pigeonID = 30;

    public static final COTSTalonFXSwerveConstants chosenModule = COTSTalonFXSwerveConstants.SDS.MK4n
        .KrakenX60(COTSTalonFXSwerveConstants.SDS.MK4n.driveRatios.L2);

    /* Drivetrain Constants */
    public static final double trackWidth = 0.517;
    public static final double wheelBase = 0.517;
    public static final double wheelCircumference = chosenModule.wheelCircumference;

    public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
        new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
        new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
        new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
        new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

    /* Module Gear Ratios */
    public static final double driveGearRatio = chosenModule.driveGearRatio;
    public static final double angleGearRatio = chosenModule.angleGearRatio;

    /* Motor Inverts */
    public static final InvertedValue angleMotorInvert = chosenModule.angleMotorInvert;
    public static final InvertedValue driveMotorInvert = chosenModule.driveMotorInvert;

    /* Angle Encoder Invert */
    public static final SensorDirectionValue cancoderInvert = chosenModule.cancoderInvert;

    /* Swerve Current Limiting */
    public static final int angleCurrentLimit = 25;
    public static final int angleCurrentLowerLimit = 40;
    public static final double angleCurrentLowerTime = 0.1;
    public static final boolean angleEnableCurrentLimit = true;

    public static final int driveCurrentLimit = 35;
    public static final int driveCurrentLowerLimit = 60;
    public static final double driveCurrentLowerTime = 0.1;
    public static final boolean driveEnableCurrentLimit = true;

    /*
     * These values are used by the drive falcon to ramp in open loop and closed
     * loop driving.
     * We found a small open loop ramp (0.25) helps with tread wear, tipping, etc
     */
    public static final double openLoopRamp = 0.15;
    public static final double closedLoopRamp = 0.0;

    /* Angle Motor PID Values */
    public static final double angleKP = chosenModule.angleKP;
    public static final double angleKI = chosenModule.angleKI;
    public static final double angleKD = chosenModule.angleKD;

    /* Drive Motor PID Values */
    public static final double driveKP = 0.12;
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.0;
    public static final double driveKF = 0.0;

    /* Drive Motor Characterization Values From SYSID */
    public static final double driveKS = 0.32;
    public static final double driveKV = 1.51;
    public static final double driveKA = 0.27;

    /* Swerve Profiling Values */
    /** Meters per Second */
    public static final double maxSpeed = 5.4;
    /** Radians per Second */
    public static final double maxAngularVelocity = 53.0;
    /* Neutral Modes */
    public static final NeutralModeValue angleNeutralMode = NeutralModeValue.Coast;
    public static final NeutralModeValue driveNeutralMode = NeutralModeValue.Brake;

    /* Module Specific Constants */
    /* Front Left Module - Module 0 */
    public static final class Mod0 {
      public static final int driveMotorID = 12;
      public static final int angleMotorID = 13;
      public static final int canCoderID = 43;
      public static final Rotation2d angleOffset = Rotation2d.fromDegrees(155.30 - 180);
      public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
          canCoderID, angleOffset);
    }

    /* Front Right Module - Module 1 */
    public static final class Mod1 {
      public static final int driveMotorID = 16;
      public static final int angleMotorID = 17;
      public static final int canCoderID = 41;
      public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-107.92 - 180);
      public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
          canCoderID, angleOffset);
    }

    /* Back Left Module - Module 2 */
    public static final class Mod2 {
      public static final int driveMotorID = 14;
      public static final int angleMotorID = 15;
      public static final int canCoderID = 42;
      public static final Rotation2d angleOffset = Rotation2d.fromDegrees(150.20 - 180);
      public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
          canCoderID, angleOffset);
    }

    /* Back Right Module - Module 3 */
    public static final class Mod3 {
      public static final int driveMotorID = 10;
      public static final int angleMotorID = 11;
      public static final int canCoderID = 40;
      public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-52.20 - 180);
      public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
          canCoderID, angleOffset);
    }
  }
}