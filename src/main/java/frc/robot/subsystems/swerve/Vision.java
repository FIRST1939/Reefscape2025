package frc.robot.subsystems.swerve;

import edu.wpi.first.math.VecBuilder;

// TODO Vision Simulation
// TODO Vision Logging
public class Vision {
    
    private final Swerve swerve;

    public Vision (Swerve swerve) {

        this.swerve = swerve;
      try{
        LimelightHelpers.setPipelineIndex("left-limelight", 0);
        LimelightHelpers.setPipelineIndex("right-limelight", 0);

        LimelightHelpers.setCameraPose_RobotSpace(
            "left-limelight", 
            SwerveConstants.LEFT_LIMELIGHT_POSITION.getX(), 
            SwerveConstants.LEFT_LIMELIGHT_POSITION.getY(), 
            SwerveConstants.LEFT_LIMELIGHT_POSITION.getZ(), 
            SwerveConstants.LEFT_LIMELIGHT_POSITION.getRotation().getX(), 
            SwerveConstants.LEFT_LIMELIGHT_POSITION.getRotation().getY(), 
            SwerveConstants.LEFT_LIMELIGHT_POSITION.getRotation().getZ()
        );

        LimelightHelpers.setCameraPose_RobotSpace(
            "right-limelight", 
            SwerveConstants.RIGHT_LIMELIGHT_POSITION.getX(), 
            SwerveConstants.RIGHT_LIMELIGHT_POSITION.getY(), 
            SwerveConstants.RIGHT_LIMELIGHT_POSITION.getZ(), 
            SwerveConstants.RIGHT_LIMELIGHT_POSITION.getRotation().getX(), 
            SwerveConstants.RIGHT_LIMELIGHT_POSITION.getRotation().getY(), 
            SwerveConstants.RIGHT_LIMELIGHT_POSITION.getRotation().getZ()
        );

        LimelightHelpers.setLEDMode_ForceOff("left-limelight");
        LimelightHelpers.setLEDMode_ForceOff("right-limelight");
      }
      catch(Exception e)
      {}
    }
    

    // TODO Filter Out Erroneous AprilTags
    public void updatePoseEstimation (double yaw, double yawRate) {
            try
            {
        if (yawRate > 720.0) { return; }

        LimelightHelpers.SetRobotOrientation("left-limelight", yaw, yawRate, 0.0, 0.0, 0.0, 0.0);
        LimelightHelpers.SetRobotOrientation("right-limelight", yaw, yawRate, 0.0, 0.0, 0.0, 0.0);

        LimelightHelpers.PoseEstimate leftPoseEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("left-limelight");
        LimelightHelpers.PoseEstimate rightPoseEstimate = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("right-limelight");

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
