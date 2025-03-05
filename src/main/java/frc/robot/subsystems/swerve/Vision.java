package frc.robot.subsystems.swerve;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.networktables.StructPublisher;

// TODO Vision Simulation
// TODO Vision Logging
public class Vision {
    
    private final Swerve swerve;

    public Vision (Swerve swerve) {

        this.swerve = swerve;
      try{
        LimelightHelpers.setPipelineIndex("limelight-left", 0);
        LimelightHelpers.setPipelineIndex("limelight-right", 0);

        LimelightHelpers.setLEDMode_ForceOff("limelight-left");
        LimelightHelpers.setLEDMode_ForceOff("limelight-right");

        this.leftP = NetworkTableInstance.getDefault()
  .getStructTopic("LeftPose", Pose2d.struct).publish();

        this.rightP = NetworkTableInstance.getDefault()
  .getStructTopic("RightPose", Pose2d.struct).publish();
      }
      catch(Exception e)
      {}
    }

    private StructPublisher<Pose2d> leftP;
    private StructPublisher<Pose2d> rightP;
    

    // TODO Filter Out Erroneous AprilTags
    public void updatePoseEstimation (double yaw, double yawRate) {
            try
            {
        if (yawRate > 720.0) { return; }

        LimelightHelpers.SetRobotOrientation("limelight-left", yaw + 180.0, yawRate, 0.0, 0.0, 0.0, 0.0);
        LimelightHelpers.SetRobotOrientation("limelight-right", yaw + 180.0, yawRate, 0.0, 0.0, 0.0, 0.0);

        LimelightHelpers.PoseEstimate leftPoseEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-left");
        LimelightHelpers.PoseEstimate rightPoseEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-right");

        this.leftP.set(leftPoseEstimate.pose);
        this.rightP.set(rightPoseEstimate.pose);

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
    catch(Exception e)
    {

    }
}
}
