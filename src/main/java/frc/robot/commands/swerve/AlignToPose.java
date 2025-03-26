package frc.robot.commands.swerve;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.Swerve;

public class AlignToPose extends Command {
    
    private final Swerve swerve;
    private final Supplier<Pose2d> poseSupplier;
    private Pose2d pose;

    private final TrapezoidProfile xController = new TrapezoidProfile(
        new TrapezoidProfile.Constraints(
            1.0, 
            1.0
        )
    );

    private final TrapezoidProfile yController = new TrapezoidProfile(
        new TrapezoidProfile.Constraints(
            1.0, 
            1.0
        )
    );

    private final TrapezoidProfile thetaController = new TrapezoidProfile(
        new TrapezoidProfile.Constraints(
            1.0, 
            1.0
        )
    );

    private final Timer timer = new Timer();
    private double lastTimestamp;

    public AlignToPose (Swerve swerve, Supplier<Pose2d> poseSupplier) {

        this.swerve = swerve;
        this.poseSupplier = poseSupplier;

        this.addRequirements(this.swerve);
    }

    @Override
    public void initialize () {

        this.pose = this.poseSupplier.get();

        this.timer.restart();
        this.lastTimestamp = 0.0;
    }

    @Override
    public void execute () {

        double dt = this.timer.get() - this.lastTimestamp;
        this.lastTimestamp = this.timer.get();

        Pose2d currentPose = this.swerve.getPose();
        ChassisSpeeds currentVelocity = this.swerve.getFieldVelocity();

        double vx = this.xController.calculate(
            dt, 
            new TrapezoidProfile.State(currentPose.getX(), currentVelocity.vxMetersPerSecond),
            new TrapezoidProfile.State(this.pose.getX(), 0.0)
        ).velocity;

        double vy = this.yController.calculate(
            dt, 
            new TrapezoidProfile.State(currentPose.getY(), currentVelocity.vyMetersPerSecond),
            new TrapezoidProfile.State(this.pose.getY(), 0.0)
        ).velocity;

        double currentHeading = currentPose.getRotation().getRadians();
        double goalHeading = this.pose.getRotation().getRadians();

        double deltaHeading = Math.atan2(Math.sin(goalHeading - currentHeading), Math.cos(goalHeading - currentHeading));
        double wrappedGoalHeading = currentHeading + deltaHeading;

        double omega = this.thetaController.calculate(
            dt, 
            new TrapezoidProfile.State(currentHeading, currentVelocity.omegaRadiansPerSecond),
            new TrapezoidProfile.State(wrappedGoalHeading, 0.0)
        ).velocity;

        this.swerve.driveFieldOriented(ChassisSpeeds.fromFieldRelativeSpeeds(vx, vy, omega, currentPose.getRotation()));
    }

    @Override
    public boolean isFinished () {

        double elapsedTime = this.timer.get();
        return this.xController.isFinished(elapsedTime) && this.yController.isFinished(elapsedTime) && this.thetaController.isFinished(elapsedTime);
    }
}
