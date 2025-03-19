package frc.robot.subsystems.swerve;

import static edu.wpi.first.units.Units.DegreesPerSecond;

import java.io.File;

import com.google.flatbuffers.Constants;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.Publisher;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.SwerveModule;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class Swerve extends SubsystemBase {
    
    private final SwerveDrive swerveDrive;
    private final Vision vision;

    public Swerve () {

        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.POSE;

        try {

            // TODO Swerve Configuration
            File configuration = new File(Filesystem.getDeployDirectory(),
            "swerve");
            //this.swerveDrive = new SwerveParser(configuration).createSwerveDrive(SwerveConstants.MAX_SPEED);
          this.swerveDrive = new SwerveParser(configuration).createSwerveDrive(SwerveConstants.MAX_SPEED);
            // Units.feetToMeters(14.5),
            //                                                       new Pose2d(new Translation2d(1,
            //                                                                                    4),
            //                                                                  Rotation2d.fromDegrees(0)));
      
      
          
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        for (SwerveModule swerveModule : this.swerveDrive.getModules()) {

            swerveModule.getAngleMotor().configurePIDWrapping(-180, 180);
        }


       this.vision = new Vision(this);

        // TODO Swerve Angle PID Wrapping
        this.swerveDrive.setHeadingCorrection(false); // TODO Swerve Heading Control
        this.swerveDrive.setCosineCompensator(false); // TODO Disable Swerve Cosine Compensation for Simulation
        this.swerveDrive.setAngularVelocityCompensation(true, true, 0.1); // TODO Swerve Angular Velocity Compensation
        this.swerveDrive.setModuleEncoderAutoSynchronize(false, 1);

        this.swerveDrive.stopOdometryThread();
        //Robot SwerveConstants.ROBOT_CONFIG throws an e.
        // // TODO PathPlanner Holonomic Controller
        //     AutoBuilder.configure(
        //     this::getPose,
        //     this::resetOdometry,
        //     this::getRobotVelocity,
        //     (robotRelativeSpeeds, moduleFeedforwards) -> {

        //         this.swerveDrive.drive(
        //             robotRelativeSpeeds,
        //             this.swerveDrive.kinematics.toSwerveModuleStates(robotRelativeSpeeds),
        //             moduleFeedforwards.linearForces()
        //         );
        //     },
        //     new PPHolonomicDriveController(
        //         new PIDConstants(5.0, 0.0, 0.0),
        //         new PIDConstants(5.0, 0.0, 0.0)
        //     ),
        //     SwerveConstants.ROBOT_CONFIG,
        //     this::isRedAlliance,
        //     this
        // );

        this.publisher = NetworkTableInstance.getDefault()
  .getStructTopic("SwervePose", Pose2d.struct).publish();
    }

    private StructPublisher<Pose2d> publisher;
        

    @Override
    public void periodic () {

        this.swerveDrive.updateOdometry();
        this.publisher.set(this.swerveDrive.getPose());

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

    public ChassisSpeeds getRobotVelocity () {

        return this.swerveDrive.getRobotVelocity();
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

    public void driveToPose (Translation2d translation, double omega) {

        this.swerveDrive.drive(translation, omega, true, false);
    }
}
