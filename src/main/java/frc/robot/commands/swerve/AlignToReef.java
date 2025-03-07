package frc.robot.commands.swerve;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.swerve.Swerve;

public class AlignToReef extends Command {
    
    private final Swerve swerve;
    private final PIDController headingFeedback = new PIDController(0.1, 0, 0);

    private Translation2d reefTarget;

    public AlignToReef (Swerve swerve) {

        this.swerve = swerve;

        this.addRequirements(this.swerve);
    }

    @Override
    public void initialize () {

        Translation2d currentTranslation = this.swerve.getPose().getTranslation();
        double minDistance = Double.MAX_VALUE;

        for (Translation2d coralPosition : SetPointConstants.REEF_CORAL_POSITIONS) {

            if (currentTranslation.getDistance(coralPosition) < minDistance) {

                this.reefTarget = coralPosition;
                minDistance = currentTranslation.getDistance(coralPosition);
            }
        }

        Logger.recordOutput("Target", new Pose2d(this.reefTarget, new Rotation2d()));

        this.headingFeedback.setSetpoint(0);
    }

    @Override
    public void execute () {

        Translation2d currentTranslation = this.swerve.getPose().getTranslation();
        Translation2d targetVector = this.reefTarget.minus(currentTranslation);

        Rotation2d currentHeading = this.swerve.getPose().getRotation();
        double rotation = this.headingFeedback.calculate(currentHeading.getDegrees());

        this.swerve.driveToPose(targetVector.times(2.0), rotation);
    }

    @Override
    public boolean isFinished () {

        Translation2d currentTranslation = this.swerve.getPose().getTranslation();
        Translation2d targetVector = this.reefTarget.minus(currentTranslation);

        double translationError = targetVector.getNorm();
        double headingError = this.headingFeedback.getError();

        return (translationError < 0.025 && headingError < 1.5);
    }
}
