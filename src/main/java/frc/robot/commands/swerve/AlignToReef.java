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

    private Pose2d reefTarget;

    public AlignToReef (Swerve swerve) {

        this.swerve = swerve;
        this.headingFeedback.enableContinuousInput(-180, 180);

        this.addRequirements(this.swerve);
    }

    @Override
    public void initialize () {

        Translation2d currentTranslation = this.swerve.getPose().getTranslation();
        double minDistance = Double.MAX_VALUE;

        for (Pose2d coralPosition : SetPointConstants.REEF_CORAL_POSITIONS) {

            if (currentTranslation.getDistance(coralPosition.getTranslation()) < minDistance) {

                this.reefTarget = coralPosition;
                minDistance = currentTranslation.getDistance(coralPosition.getTranslation());
            }
        }

        this.headingFeedback.setSetpoint(this.reefTarget.getRotation().getDegrees());
    }

    @Override
    public void execute () {

        Translation2d currentTranslation = this.swerve.getPose().getTranslation();
        Translation2d targetVector = this.reefTarget.getTranslation().minus(currentTranslation);

        Rotation2d currentHeading = this.swerve.getPose().getRotation();
        double rotation = this.headingFeedback.calculate(currentHeading.getDegrees());

        this.swerve.driveToPose(targetVector.times(2.0), rotation);
    }
}
