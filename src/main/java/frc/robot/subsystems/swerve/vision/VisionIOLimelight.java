package frc.robot.subsystems.swerve.vision;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.util.LimelightHelpers;

public class VisionIOLimelight implements VisionIO {
    
    public VisionIOLimelight () {

        LimelightHelpers.setPipelineIndex("limelight-left", 0);
        LimelightHelpers.setPipelineIndex("limelight-right", 0);

        LimelightHelpers.setLEDMode_ForceOff("limelight-left");
        LimelightHelpers.setLEDMode_ForceOff("limelight-right");
    }

    @Override
    public void updateInputs (VisionIOInputsAutoLogged inputs, double yaw, double yawRate) {

        LimelightHelpers.SetRobotOrientation("limelight-left", yaw, yawRate, 0.0, 0.0, 0.0, 0.0);
        LimelightHelpers.SetRobotOrientation("limelight-right", yaw, yawRate, 0.0, 0.0, 0.0, 0.0);

        LimelightHelpers.PoseEstimate leftPoseEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-left");
        LimelightHelpers.PoseEstimate rightPoseEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight-right");

        if (leftPoseEstimate == null) {

            inputs.leftAvgTagArea = 0.0;
            inputs.leftAvgTagDist = 0.0;
            inputs.leftIsMegaTag2 = true;
            inputs.leftLatency = 0.0;
            inputs.leftPose = new Pose2d();
            inputs.leftTags = new double[0][];
            inputs.leftTagCount = 0;
            inputs.leftTagSpan = 0.0;
            inputs.leftTimestampSeconds = 0.0;
        } else {

            inputs.leftAvgTagArea = leftPoseEstimate.avgTagArea;
            inputs.leftAvgTagDist = leftPoseEstimate.avgTagDist;
            inputs.leftIsMegaTag2 = leftPoseEstimate.isMegaTag2;
            inputs.leftLatency = leftPoseEstimate.latency;
            inputs.leftPose = leftPoseEstimate.pose;
            inputs.leftTagCount = leftPoseEstimate.tagCount;
            inputs.leftTagSpan = leftPoseEstimate.tagSpan;
            inputs.leftTimestampSeconds = leftPoseEstimate.timestampSeconds;

            inputs.leftTags = new double[leftPoseEstimate.tagCount][];

            for (int i = 0; i < leftPoseEstimate.tagCount; i++) {

                LimelightHelpers.RawFiducial aprilTag = leftPoseEstimate.rawFiducials[i];
                AprilTagInputs tagInputs = new AprilTagInputs();

                tagInputs.ambiguity = aprilTag.ambiguity;
                tagInputs.distToCamera = aprilTag.distToCamera;
                tagInputs.distToRobot = aprilTag.distToRobot;
                tagInputs.id = aprilTag.id;
                tagInputs.ta = aprilTag.ta;
                tagInputs.txnc = aprilTag.txnc;
                tagInputs.tync = aprilTag.tync;

                inputs.leftTags[i] = tagInputs.toArray();
            }
        }

        if (rightPoseEstimate == null) {

            inputs.rightAvgTagArea = 0.0;
            inputs.rightAvgTagDist = 0.0;
            inputs.rightIsMegaTag2 = true;
            inputs.rightLatency = 0.0;
            inputs.rightPose = new Pose2d();
            inputs.rightTags = new double[0][];
            inputs.rightTagCount = 0;
            inputs.rightTagSpan = 0.0;
            inputs.rightTimestampSeconds = 0.0;
        } else {

            inputs.rightAvgTagArea = rightPoseEstimate.avgTagArea;
            inputs.rightAvgTagDist = rightPoseEstimate.avgTagDist;
            inputs.rightIsMegaTag2 = rightPoseEstimate.isMegaTag2;
            inputs.rightLatency = rightPoseEstimate.latency;
            inputs.rightPose = rightPoseEstimate.pose;
            inputs.rightTagCount = rightPoseEstimate.tagCount;
            inputs.rightTagSpan = rightPoseEstimate.tagSpan;
            inputs.rightTimestampSeconds = rightPoseEstimate.timestampSeconds;

            inputs.rightTags = new double[rightPoseEstimate.tagCount][];

            for (int i = 0; i < rightPoseEstimate.tagCount; i++) {

                LimelightHelpers.RawFiducial aprilTag = rightPoseEstimate.rawFiducials[i];
                AprilTagInputs tagInputs = new AprilTagInputs();

                tagInputs.ambiguity = aprilTag.ambiguity;
                tagInputs.distToCamera = aprilTag.distToCamera;
                tagInputs.distToRobot = aprilTag.distToRobot;
                tagInputs.id = aprilTag.id;
                tagInputs.ta = aprilTag.ta;
                tagInputs.txnc = aprilTag.txnc;
                tagInputs.tync = aprilTag.tync;

                inputs.rightTags[i] = tagInputs.toArray();
            }
        }
    }
}
