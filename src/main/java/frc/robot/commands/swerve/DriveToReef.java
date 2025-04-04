package frc.robot.commands.swerve;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.Swerve;

public class DriveToReef extends Command {
    
    private final Swerve swerve;
    private final Pose2d reefTarget;

    public DriveToReef (Swerve swerve, Pose2d reefTarget) {

        this.swerve = swerve;
        this.reefTarget = reefTarget;

        this.addRequirements(swerve);
    }

    @Override
    public void execute () {

        Pose2d currentPose = this.swerve.getPose();

        Translation2d driveVector = this.reefTarget.getTranslation().minus(currentPose.getTranslation()).times(2.0);
        double omega = this.reefTarget.getRotation().minus(currentPose.getRotation()).getRadians() * 2.0;

        this.swerve.driveVector(driveVector, omega);
    }

    @Override
    public boolean isFinished () {

        Pose2d currentPose = this.swerve.getPose();

        Translation2d driveVector = this.reefTarget.getTranslation().minus(currentPose.getTranslation());
        double theta = this.reefTarget.getRotation().minus(currentPose.getRotation()).getRadians();

        return driveVector.getNorm() < 0.035 && Math.abs(theta) < 0.05;
    }
}
