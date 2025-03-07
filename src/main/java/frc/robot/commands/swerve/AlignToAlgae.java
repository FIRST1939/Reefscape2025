package frc.robot.commands.swerve;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.swerve.Swerve;

public class AlignToAlgae extends Command {
    
    private final Swerve swerve;
    private final PIDController headingFeedback = new PIDController(0.1, 0, 0);

    private Translation2d reefTarget;

    public AlignToAlgae (Swerve swerve, DoubleSupplier omega) {

        this.swerve = swerve;

        this.addRequirements(this.swerve);
    }

    @Override
    public void initialize () {

        Translation2d currentTranslation = this.swerve.getPose().getTranslation();
        double minDistance = Double.MAX_VALUE;

        for (Translation2d algaePosition : SetPointConstants.REEF_ALGAE_POSITIONS) {

            if (currentTranslation.getDistance(algaePosition) < minDistance) {

                this.reefTarget = algaePosition;
                minDistance = currentTranslation.getDistance(algaePosition);
            }
        }

        this.headingFeedback.setSetpoint(60);
    }

    @Override
    public void execute () {

        Translation2d currentTranslation = this.swerve.getPose().getTranslation();
        Translation2d targetVector = this.reefTarget.minus(currentTranslation);

        Rotation2d currentHeading = this.swerve.getPose().getRotation();
        Rotation2d targetHeading = Rotation2d.fromDegrees(60);
        double rotation = this.headingFeedback.calculate(currentHeading.getDegrees());

        this.swerve.driveToPose(targetVector.times(2.0), rotation);
    }
}
