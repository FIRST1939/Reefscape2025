package frc.robot.subsystems.swerve.vision;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Pose2d;

public interface VisionIO {

    @AutoLog
    public static class VisionIOInputs {

        public double leftAvgTagArea = 0.0;
        public double leftAvgTagDist = 0.0;
        public boolean leftIsMegaTag2 = true;
        public double leftLatency = 0.0;
        public Pose2d leftPose = new Pose2d();
        public double[][] leftTags = {};
        public int leftTagCount = 0;
        public double leftTagSpan = 0.0;
        public double leftTimestampSeconds = 0.0;

        public double rightAvgTagArea = 0.0;
        public double rightAvgTagDist = 0.0;
        public boolean rightIsMegaTag2 = true;
        public double rightLatency = 0.0;
        public Pose2d rightPose = new Pose2d();
        public double[][] rightTags = {};
        public int rightTagCount = 0;
        public double rightTagSpan = 0.0;
        public double rightTimestampSeconds = 0.0;
    }

    public static class AprilTagInputs {

        public double ambiguity = 0.0;
        public double distToCamera = 0.0;
        public double distToRobot = 0.0;
        public int id = 0;
        public double ta = 0.0;
        public double txnc = 0.0;
        public double tync = 0.0;

        public double[] toArray () {

            return new double[] {
                this.ambiguity,
                this.distToCamera,
                this.distToRobot,
                this.id,
                this.ta,
                this.txnc,
                this.tync
            };
        }
    }

    public default void updateInputs (VisionIOInputsAutoLogged inputs, double yaw, double yawRate) {}
}
