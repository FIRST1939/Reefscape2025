package frc.robot.subsystems.swerve;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// TODO Vision Simulation
// TODO Vision Logging
public class Vision {
    
    private final Swerve swerve;

    public Vision (Swerve swerve) {

        this.swerve = swerve;

        LimelightHelpers.setPipelineIndex("limelight-left", 0);
        LimelightHelpers.setPipelineIndex("limelight-right", 0);

        LimelightHelpers.setLEDMode_ForceOff("limelight-left");
        LimelightHelpers.setLEDMode_ForceOff("limelight-right");
    }
    

    // TODO Filter Out Erroneous AprilTags
    public void updatePoseEstimation (double yaw, double yawRate) {

        if (yawRate > 720.0) { return; }

        LimelightHelpers.SetRobotOrientation("limelight-left", yaw + SmartDashboard.getNumber("Limelight_Offset", 0.0), yawRate, 0.0, 0.0, 0.0, 0.0);
        LimelightHelpers.SetRobotOrientation("limelight-right", yaw + SmartDashboard.getNumber("Limelight_Offset", 0.0), yawRate, 0.0, 0.0, 0.0, 0.0);

        LimelightHelpers.PoseEstimate leftPoseEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-left");
        LimelightHelpers.PoseEstimate rightPoseEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-right");

        // TODO Limelight Standard Deviations
        if (leftPoseEstimate.tagCount != 0) {

            this.swerve.addVisionMeasurement(
                leftPoseEstimate.pose, 
                leftPoseEstimate.timestampSeconds, 
                VecBuilder.fill(0.7, 0.7, 9999999)
            );
        }

        if (rightPoseEstimate.tagCount != 0) {

            this.swerve.addVisionMeasurement(
                rightPoseEstimate.pose, 
                rightPoseEstimate.timestampSeconds, 
                VecBuilder.fill(0.7, 0.7, 9999999)
            );
        }
    }
}
