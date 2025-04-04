package frc.robot.commands.swerve;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.Swerve;

public class RotateToReef extends Command {
    
    private final Swerve swerve;
    private final Pose2d reefTarget;

    public RotateToReef (Swerve swerve, Pose2d reefTarget) {

        this.swerve = swerve;
        this.reefTarget = reefTarget;

        this.addRequirements(swerve);
    }

    @Override
    public void execute () {

        Pose2d currentPose = this.swerve.getPose();
        Rotation2d rotationVector = this.reefTarget.getRotation().minus(currentPose.getRotation());
        this.swerve.driveVector(new Translation2d(), rotationVector.getRadians());
    }

    @Override
    public boolean isFinished () {

        Pose2d currentPose = this.swerve.getPose();
        Rotation2d rotationVector = this.reefTarget.getRotation().minus(currentPose.getRotation());
        return Math.abs(rotationVector.getRadians()) < 0.05;
    }
}
