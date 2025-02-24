package frc.robot.subsystems;

import java.io.File;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class Swerve extends SubsystemBase {
    
    private final SwerveDrive swerveDrive;

    public Swerve () {

        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.POSE;

        try {

            File configuration = new File(Filesystem.getDeployDirectory(), "swerve");
            this.swerveDrive = new SwerveParser(configuration).createSwerveDrive(SwerveConstants.MAX_SPEED);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        this.swerveDrive.setHeadingCorrection(false); // TODO Swerve Heading Control
        this.swerveDrive.setCosineCompensator(false); // TODO Disable Swerve Cosine Compensation for Simulation
        this.swerveDrive.setAngularVelocityCompensation(true, true, 0.1); // TODO Swerve Angular Velocity Compensation
        this.swerveDrive.setModuleEncoderAutoSynchronize(false, 0);

        // TODO Setup PathPlanner
        // TODO Setup Vision (Override Odometry Periodic Update)
    }

    public SwerveDrive getSwerveDrive () {

        return this.swerveDrive;
    }

    private void configureFeedforwards () {

        // TODO Configure Swerve Feedforwards
    }

    public Pose2d getPose () {

        return this.swerveDrive.getPose();
    }

    public void resetOdometry (Pose2d initialPose) {

        this.swerveDrive.resetOdometry(initialPose);
    }

    private boolean isRedAlliance () {

        var alliance = DriverStation.getAlliance();
        return alliance.isPresent() ? alliance.get() == DriverStation.Alliance.Red : false;
    }

    // TODO Zero Gyro at Startup
    public void zeroGyro () {

        if (this.isRedAlliance()) {

            this.swerveDrive.zeroGyro();
            this.resetOdometry(new Pose2d(this.getPose().getTranslation(), Rotation2d.fromDegrees(180)));
        } else {

            this.swerveDrive.zeroGyro();
        }
    }

    public void driveFieldOriented (ChassisSpeeds chassisSpeeds) {

        this.swerveDrive.driveFieldOriented(chassisSpeeds);
    }
}
