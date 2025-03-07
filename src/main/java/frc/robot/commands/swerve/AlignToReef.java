package frc.robot.commands.swerve;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.swerve.Swerve;

public class AlignToReef extends Command {
    
    private final Swerve swerve;
    private Pose2d reefTarget;

    public AlignToReef (Swerve swerve) {

        this.swerve = swerve;
        this.addRequirements(this.swerve);
    }

    @Override
    public void initialize () {

        Pose2d currentPose = this.swerve.getPose();
        double minDistance = Double.MAX_VALUE;

        for (Pose2d coralPosition : SetPointConstants.REEF_CORAL_POSITIONS) {

            if (currentPose.getTranslation().getDistance(coralPosition.getTranslation()) < minDistance) {

                this.reefTarget = coralPosition;
            }
        }
    }

    @Override
    public void execute () {

        Translation2d currentTranslation = this.swerve.getPose().getTranslation();
        Translation2d targetVector = this.reefTarget.getTranslation().minus(currentTranslation);
        this.swerve.driveToPose(targetVector.times(2.0));
    }
}
