package frc.robot.subsystems.swerve;

import static edu.wpi.first.units.Units.DegreesPerSecond;

import java.io.File;
import java.io.IOException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swerve.vision.Vision;
import frc.robot.subsystems.swerve.vision.VisionIOLimelight;
import frc.robot.subsystems.swerve.vision.VisionIOSim;
import frc.robot.util.Elastic;
import frc.robot.util.Elastic.Notification;
import frc.robot.util.Elastic.Notification.NotificationLevel;
import swervelib.SwerveDrive;
import swervelib.SwerveModule;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class Swerve extends SubsystemBase {
    
    private SwerveDrive swerveDrive;
    private Vision vision;

    public Swerve () {

        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.POSE;

        try {

            File configuration = new File(Filesystem.getDeployDirectory(), "swerve");
            this.swerveDrive = new SwerveParser(configuration).createSwerveDrive(SwerveConstants.MAX_SPEED);
        } catch (IOException exception) {

            Elastic.sendNotification(
                new Notification(
                    NotificationLevel.ERROR,
                    "Swerve Error",
                    "Configuration file not loaded."
                )
            );
        }
        
        for (SwerveModule swerveModule : this.swerveDrive.getModules()) {

            swerveModule.getAngleMotor().configurePIDWrapping(-180, 180);
        }

        this.swerveDrive.setHeadingCorrection(false);
        this.swerveDrive.setCosineCompensator(false);
        this.swerveDrive.setAngularVelocityCompensation(true, true, 0.1);
        this.swerveDrive.setModuleEncoderAutoSynchronize(false, 1);

        AutoBuilder.configure(
            this::getPose,
            this::resetOdometry,
            this::getRobotVelocity,
            (robotRelativeSpeeds, moduleFeedforwards) -> {
                this.swerveDrive.drive(
                    robotRelativeSpeeds,
                    this.swerveDrive.kinematics.toSwerveModuleStates(robotRelativeSpeeds),
                    moduleFeedforwards.linearForces()
                );
            },
            new PPHolonomicDriveController(
                SwerveConstants.PP_DRIVE_PID,
                SwerveConstants.PP_TURN_PID
            ),
            SwerveConstants.ROBOT_CONFIG,
            () -> !DriverStation.isTeleop() ? this.isRedAlliance() : false,
            this
        );

        if (RobotBase.isReal()) {

            this.vision = new Vision(this, new VisionIOLimelight());
            this.swerveDrive.stopOdometryThread();
        } else {

            this.vision = new Vision(this, new VisionIOSim(() -> this.getSimulationPose()));
        }
    }   

    @Override
    public void periodic () {

        if (RobotBase.isReal()) {

            this.swerveDrive.updateOdometry();
        }

        this.vision.updatePoseEstimation(
            this.swerveDrive.getOdometryHeading().getDegrees(), 
            this.swerveDrive.getGyro().getYawAngularVelocity().in(DegreesPerSecond)
        );
    }

    public SwerveDrive getSwerveDrive () {

        return this.swerveDrive;
    }

    public Pose2d getPose () {

        return this.swerveDrive.getPose();
    }

    public Pose2d getSimulationPose () {

        return this.swerveDrive.getSimulationDriveTrainPose().orElse(new Pose2d());
    }

    public ChassisSpeeds getRobotVelocity () {

        return this.swerveDrive.getRobotVelocity();
    }

    public ChassisSpeeds getFieldVelocity () {

        return this.swerveDrive.getFieldVelocity();
    }

    public ChassisSpeeds getSimulationFieldVelocity () {

        return this.swerveDrive.getMapleSimDrive().get().getDriveTrainSimulatedChassisSpeedsFieldRelative();
    }

    public void addVisionMeasurement (Pose2d pose, double timestamp, Matrix<N3, N1> standardDeviations) {

        this.swerveDrive.addVisionMeasurement(pose, timestamp, standardDeviations);
    }

    public void resetOdometry (Pose2d initialPose) {

        this.swerveDrive.resetOdometry(initialPose);
    }

    private boolean isRedAlliance () {

        var alliance = DriverStation.getAlliance();
        return alliance.isPresent() ? alliance.get() == DriverStation.Alliance.Red : false;
    }

    public void zeroGyro () {

        if (!this.isRedAlliance()) {

            this.swerveDrive.zeroGyro();
            this.resetOdometry(new Pose2d(this.getPose().getTranslation(), Rotation2d.fromDegrees(180)));
        } else {

            this.swerveDrive.zeroGyro();
        }
    }

    public void driveFieldOriented (ChassisSpeeds chassisSpeeds) {

        this.swerveDrive.driveFieldOriented(chassisSpeeds);
    }

    public void driveVector (Translation2d translation, double omega) {

        this.swerveDrive.drive(translation, omega, true, false);
    }
}
