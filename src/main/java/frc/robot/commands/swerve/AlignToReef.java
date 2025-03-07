package frc.robot.commands.swerve;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.swerve.Swerve;

public class AlignToReef extends Command {
    
    private final Swerve swerve;
    private final DoubleSupplier omega;

    private Translation2d reefTarget;

    public AlignToReef (Swerve swerve, DoubleSupplier omega) {

        this.swerve = swerve;
        this.omega = omega;

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
    }

    @Override
    public void execute () {

        Translation2d currentTranslation = this.swerve.getPose().getTranslation();
        Translation2d targetVector = this.reefTarget.minus(currentTranslation);
        this.swerve.driveToPose(targetVector.times(2.0), this.omega.getAsDouble());
    }
}
