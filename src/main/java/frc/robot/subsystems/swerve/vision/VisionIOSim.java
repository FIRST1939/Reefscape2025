package frc.robot.subsystems.swerve.vision;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.util.LimelightHelpers;

public class VisionIOSim extends VisionIOLimelight{
    
    private final VisionSystemSim vision = new VisionSystemSim("main");
    private final Supplier<Pose2d> poseSupplier;

    private final PhotonCamera leftLimelight;
    private final PhotonCamera rightLimelight;

    private final PhotonPoseEstimator leftPoseEstimator;
    private final PhotonPoseEstimator rightPoseEstimator;

    public VisionIOSim (Supplier<Pose2d> poseSupplier) {

        this.poseSupplier = poseSupplier;

        this.leftLimelight = new PhotonCamera("limelight-left");
        this.rightLimelight = new PhotonCamera("limelight-right");

        AprilTagFieldLayout tagLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);
        this.vision.addAprilTags(tagLayout);

        SimCameraProperties cameraProperties = new SimCameraProperties();

        cameraProperties.setCalibration(1280, 960, Rotation2d.fromDegrees(79.4));
        cameraProperties.setCalibError(0.25, 0.08);

        cameraProperties.setFPS(20);
        cameraProperties.setExposureTimeMs(15);

        cameraProperties.setAvgLatencyMs(35);
        cameraProperties.setLatencyStdDevMs(5);

        PhotonCameraSim leftSim = new PhotonCameraSim(this.leftLimelight, cameraProperties);
        PhotonCameraSim rightSim = new PhotonCameraSim(this.rightLimelight, cameraProperties);

        leftSim.enableRawStream(false);
        rightSim.enableRawStream(false);

        this.vision.addCamera(leftSim, VisionConstants.LEFT_TRANSFORM);
        this.vision.addCamera(rightSim, VisionConstants.RIGHT_TRANSFORM);

        this.leftPoseEstimator = new PhotonPoseEstimator(
            tagLayout,
            PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
            VisionConstants.LEFT_TRANSFORM
        );

        this.rightPoseEstimator = new PhotonPoseEstimator(
            tagLayout,
            PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
            VisionConstants.RIGHT_TRANSFORM
        );
    }

    @Override
    public void updateInputs (VisionIOInputsAutoLogged inputs, double yaw, double yawRate) {

        this.vision.update(this.poseSupplier.get());
        double[] robotOrientation = new double[6];

        robotOrientation[0] = yaw;
        robotOrientation[1] = yawRate;
        robotOrientation[2] = 0.0;
        robotOrientation[3] = 0.0;
        robotOrientation[4] = 0.0;
        robotOrientation[5] = 0.0;

        LimelightHelpers.setLimelightNTDoubleArray("limelight-left", "robot_orientation_set", robotOrientation);
        LimelightHelpers.setLimelightNTDoubleArray("limelight-right", "robot_orientation_set", robotOrientation);

        List<PhotonPipelineResult> leftResult = this.leftLimelight.getAllUnreadResults();
        List<PhotonPipelineResult> rightResult = this.rightLimelight.getAllUnreadResults();

        if (!leftResult.isEmpty()) {

            PhotonPipelineResult result = leftResult.get(leftResult.size() - 1);
            double latency = (Timer.getFPGATimestamp() - result.getTimestampSeconds()) * 1000.0;

            List<PhotonTrackedTarget> targets = result.getTargets();
            List<AprilTagInputs> tagInputs = new ArrayList<>();

            double span = 0.0;
            double totalDistance = 0.0;
            double totalArea = 0.0;

            if (targets.size() >= 2) {

                double minX = targets.stream().mapToDouble(target -> target.getBestCameraToTarget().getX()).min().orElse(0.0);
                double maxX = targets.stream().mapToDouble(target -> target.getBestCameraToTarget().getX()).max().orElse(0.0);

                span = maxX - minX;
            }

            Optional<EstimatedRobotPose> estimatedPoseOpt = this.leftPoseEstimator.update(result);
            Pose2d estimatedPose = estimatedPoseOpt.map(estimate -> estimate.estimatedPose.toPose2d()).orElse(new Pose2d());

            for (PhotonTrackedTarget target : targets) {

                if (target.getFiducialId() == -1) { continue; }

                Transform3d cameraTransform = target.getBestCameraToTarget();
                Transform3d robotTransform = cameraTransform.plus(VisionConstants.LEFT_TRANSFORM);

                AprilTagInputs tagInput = new AprilTagInputs();
                tagInputs.add(tagInput);

                tagInput.ambiguity = target.getPoseAmbiguity();
                tagInput.distToCamera = cameraTransform.getTranslation().getNorm();
                tagInput.distToRobot = robotTransform.getTranslation().getNorm();
                tagInput.id = target.getFiducialId();
                tagInput.ta = target.getArea();
                tagInput.txnc = target.getYaw();
                tagInput.tync = target.getPitch();

                totalDistance += tagInput.distToCamera;
                totalArea += tagInput.ta;
            }

            int tagCount = tagInputs.size();
            double averageTagDistance = tagCount > 0 ? totalDistance / tagCount : 0.0;
            double averageTagArea = tagCount > 0 ? totalArea / tagCount : 0.0;

            double[] poseArray = new double[11 + (7 * tagCount)];

            poseArray[0] = estimatedPose.getX();
            poseArray[1] = estimatedPose.getY();
            poseArray[2] = 0.0;
            poseArray[3] = 0.0;
            poseArray[4] = 0.0;
            poseArray[5] = estimatedPose.getRotation().getDegrees();
            poseArray[6] = latency;
            poseArray[7] = tagCount;
            poseArray[8] = span;
            poseArray[9] = averageTagDistance;
            poseArray[10] = averageTagArea;

            for (int i = 0; i < tagCount; i++) {

                double[] tagData = tagInputs.get(i).toArray();
                System.arraycopy(tagData, 0, poseArray, 11 + (i * 7), 7);
            }

            LimelightHelpers.setLimelightNTDoubleArray("limelight-left", "botpose_orb_wpiblue", poseArray);
        }

        if (!rightResult.isEmpty()) {

            PhotonPipelineResult result = rightResult.get(rightResult.size() - 1);
            double latency = (Timer.getFPGATimestamp() - result.getTimestampSeconds()) * 1000.0;

            List<PhotonTrackedTarget> targets = result.getTargets();
            List<AprilTagInputs> tagInputs = new ArrayList<>();

            double span = 0.0;
            double totalDistance = 0.0;
            double totalArea = 0.0;

            if (targets.size() >= 2) {

                double minX = targets.stream().mapToDouble(target -> target.getBestCameraToTarget().getX()).min().orElse(0.0);
                double maxX = targets.stream().mapToDouble(target -> target.getBestCameraToTarget().getX()).max().orElse(0.0);

                span = maxX - minX;
            }

            Optional<EstimatedRobotPose> estimatedPoseOpt = this.rightPoseEstimator.update(result);
            Pose2d estimatedPose = estimatedPoseOpt.map(estimate -> estimate.estimatedPose.toPose2d()).orElse(new Pose2d());

            for (PhotonTrackedTarget target : targets) {

                if (target.getFiducialId() == -1) { continue; }

                Transform3d cameraTransform = target.getBestCameraToTarget();
                Transform3d robotTransform = cameraTransform.plus(VisionConstants.RIGHT_TRANSFORM);

                AprilTagInputs tagInput = new AprilTagInputs();
                tagInputs.add(tagInput);

                tagInput.ambiguity = target.getPoseAmbiguity();
                tagInput.distToCamera = cameraTransform.getTranslation().getNorm();
                tagInput.distToRobot = robotTransform.getTranslation().getNorm();
                tagInput.id = target.getFiducialId();
                tagInput.ta = target.getArea();
                tagInput.txnc = target.getYaw();
                tagInput.tync = target.getPitch();

                totalDistance += tagInput.distToCamera;
                totalArea += tagInput.ta;
            }

            int tagCount = tagInputs.size();
            double averageTagDistance = tagCount > 0 ? totalDistance / tagCount : 0.0;
            double averageTagArea = tagCount > 0 ? totalArea / tagCount : 0.0;

            double[] poseArray = new double[11 + (7 * tagCount)];

            poseArray[0] = estimatedPose.getX();
            poseArray[1] = estimatedPose.getY();
            poseArray[2] = 0.0;
            poseArray[3] = 0.0;
            poseArray[4] = 0.0;
            poseArray[5] = estimatedPose.getRotation().getDegrees();
            poseArray[6] = latency;
            poseArray[7] = tagCount;
            poseArray[8] = span;
            poseArray[9] = averageTagDistance;
            poseArray[10] = averageTagArea;

            for (int i = 0; i < tagCount; i++) {

                double[] tagData = tagInputs.get(i).toArray();
                System.arraycopy(tagData, 0, poseArray, 11 + (i * 7), 7);
            }

            LimelightHelpers.setLimelightNTDoubleArray("limelight-right", "botpose_orb_wpiblue", poseArray);
        }

        super.updateInputs(inputs, yaw, yawRate);
    }
}
