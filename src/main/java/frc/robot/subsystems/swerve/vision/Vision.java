package frc.robot.subsystems.swerve.vision;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.subsystems.swerve.Swerve;

public class Vision {
    
    private final Swerve swerve;

    private final VisionIO io;
    private final VisionIOInputsAutoLogged inputs = new VisionIOInputsAutoLogged();

    public Vision (Swerve swerve, VisionIO io) {

        this.swerve = swerve;
        this.io = io;
    }
    
    public void updatePoseEstimation (double yaw, double yawRate) {

        if (yawRate > 720.0) { return; }
        this.io.updateInputs(this.inputs, yaw, yawRate);
        Logger.processInputs("Vision", this.inputs);

        // TODO Limelight Standard Deviations
        if (!this.inputs.leftPose.equals(new Pose2d()) && this.inputs.leftTagCount != 0) {

            this.swerve.addVisionMeasurement(
                this.inputs.leftPose, 
                this.inputs.leftTimestampSeconds, 
                VecBuilder.fill(0.7, 0.7, 9999999)
            );
        }

        if (!this.inputs.rightPose.equals(new Pose2d()) && this.inputs.rightTagCount != 0) {

            this.swerve.addVisionMeasurement(
                this.inputs.rightPose, 
                this.inputs.rightTimestampSeconds, 
                VecBuilder.fill(0.7, 0.7, 9999999)
            );
        }
    }
}
