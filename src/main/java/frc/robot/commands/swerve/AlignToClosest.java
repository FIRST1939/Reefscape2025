package frc.robot.commands.swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.Swerve;

public class AlignToClosest extends Command {
    
    private final Swerve swerve;
    private final Pose2d[] poses;

    private final PIDController headingFeedback = new PIDController(0.1, 0, 0);

    private Pose2d targetPose;

    public AlignToClosest (Swerve swerve, Pose2d[] poses) {

        this.swerve = swerve;
        this.poses = poses;

        this.headingFeedback.enableContinuousInput(-180, 180);

        this.addRequirements(this.swerve);
    }

    @Override
    public void initialize () {

        Pose2d currentPose = this.swerve.getPose();
        double minDistance = Double.MAX_VALUE;

        for (Pose2d pose : this.poses) {

            double distance = currentPose.getTranslation().getDistance(pose.getTranslation());

            if (distance < minDistance) {

                this.targetPose = pose;
                minDistance = distance;
            }
        }

        this.headingFeedback.setSetpoint(this.targetPose.getRotation().getDegrees());
    }

    @Override
    public void execute () {
       
        Pose2d currentPose = this.swerve.getPose();

        Translation2d translationVector = this.targetPose.getTranslation().minus(currentPose.getTranslation()).times(2.0);
        double rotation = this.headingFeedback.calculate(currentPose.getRotation().getDegrees());

        this.swerve.driveToPose(translationVector, rotation);
        SmartDashboard.putNumber("Alignmnent_Error", translationVector.div(2.0).getNorm());

    }
}
