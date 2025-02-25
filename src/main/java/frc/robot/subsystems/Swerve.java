package frc.robot.subsystems;

import frc.robot.SwerveModule;
import frc.robot.Constants;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;

import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase {
  public SwerveDriveOdometry swerveOdometry;
  // public PoseEstimator poseEstimator;
  public SwerveModule[] mSwerveMods;
  public Pigeon2 gyro;
  public Field2d field = new Field2d();
  public PIDController anglePidController = new PIDController(0.0015, 0, 0);

  public Swerve() {

    try {
      RobotConfig config = RobotConfig.fromGUISettings();

      // Configure AutoBuilder
      AutoBuilder.configure(
          this::getPose,
          this::resetPose,
          this::getSpeeds,
          this::driveRobotRelative,
          new PPHolonomicDriveController(
              new PIDConstants(6.5, 0.0, 0.0),
              new PIDConstants(6.5, 0.0, 0.0)),
          config,
          () -> {
            var alliance = DriverStation.getAlliance();
            if (alliance.isPresent()) {
              return alliance.get() == DriverStation.Alliance.Red;
            }
            return false;
          },
          this);
    } catch (Exception e) {
      DriverStation.reportError("Failed to load PathPlanner config and configure AutoBuilder", e.getStackTrace());
    }

    gyro = new Pigeon2(Constants.Swerve.pigeonID, Constants.CANivoresCanBus);
    gyro.getConfigurator().apply(new Pigeon2Configuration());
    gyro.setYaw(0);
    SmartDashboard.putData("Field", field);

    mSwerveMods = new SwerveModule[] {
        new SwerveModule(0, Constants.Swerve.Mod0.constants),
        new SwerveModule(1, Constants.Swerve.Mod1.constants),
        new SwerveModule(2, Constants.Swerve.Mod2.constants),
        new SwerveModule(3, Constants.Swerve.Mod3.constants)
    };

    swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, gyro.getRotation2d(),
        getModulePositions());
    // poseEstimator = new PoseEstimator<>(Constants.Swerve.swerveKinematics,
    // swerveOdometry, null, null);
  }

  public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
    SwerveModuleState[] swerveModuleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(
        fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(
            translation.getX(),
            translation.getY(),
            rotation,
            getHeading())
            : new ChassisSpeeds(
                translation.getX(),
                translation.getY(),
                rotation));
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

    for (SwerveModule mod : mSwerveMods) {
      mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
    }
  }

  /* Used by SwerveControllerCommand in Auto */
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);

    for (SwerveModule mod : mSwerveMods) {
      mod.setDesiredState(desiredStates[mod.moduleNumber], false);
    }
  }

  public SwerveModuleState[] getModuleStates() {
    SwerveModuleState[] states = new SwerveModuleState[4];
    for (SwerveModule mod : mSwerveMods) {
      states[mod.moduleNumber] = mod.getState();
    }
    return states;
  }

  public SwerveModulePosition[] getModulePositions() {
    SwerveModulePosition[] positions = new SwerveModulePosition[4];
    for (SwerveModule mod : mSwerveMods) {
      positions[mod.moduleNumber] = mod.getPosition();
    }
    return positions;
  }

  public Pose2d getPose() {
    return swerveOdometry.getPoseMeters();
  }

  public void resetPose(Pose2d pose) {
    swerveOdometry.resetPosition(gyro.getRotation2d(), getModulePositions(), pose);
  }

  public ChassisSpeeds getSpeeds() {
    return Constants.Swerve.swerveKinematics.toChassisSpeeds(getModuleStates());
  }

  public void driveRobotRelative(ChassisSpeeds robotRelativeSpeeds) {
    ChassisSpeeds targetSpeeds = ChassisSpeeds.discretize(robotRelativeSpeeds, 0.02);

    SwerveModuleState[] targetStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(targetSpeeds);
    setModuleStates(targetStates);
  }

  public Rotation2d getHeading() {
    return getPose().getRotation();
  }

  public void setHeading(Rotation2d heading) {
    swerveOdometry.resetPosition(gyro.getRotation2d(), getModulePositions(),
        new Pose2d(getPose().getTranslation(), heading));
  }

  public void zeroHeading() {
    swerveOdometry.resetPosition(gyro.getRotation2d(), getModulePositions(),
        new Pose2d(getPose().getTranslation(), new Rotation2d()));
  }

  public void resetModulesToAbsolute() {
    for (SwerveModule mod : mSwerveMods) {
      mod.resetToAbsolute();
    }
  }

  public void resetOdometry(Pose2d pose) {
    swerveOdometry.resetPosition(gyro.getRotation2d(), getModulePositions(), pose);
  }

  @Override
  public void periodic() {
    swerveOdometry.update(gyro.getRotation2d(), getModulePositions());
    field.setRobotPose(getPose());

    SmartDashboard.putNumber("heading", getHeading().getDegrees());
    for (SwerveModule mod : mSwerveMods) {
      SmartDashboard.putNumber("Mod " + mod.moduleNumber + " CANcoder", mod.getCANcoder().getDegrees());
    }
  }
}