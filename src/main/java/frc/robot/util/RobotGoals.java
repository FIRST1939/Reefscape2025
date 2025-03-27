package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class RobotGoals {
    
    private static Pose2d targetCoralPose;
    private static Pose2d targetAlgaePose;

    public static void calculateTargetPoses (Pose2d currentPose) {

        Pose2d[] reefCoralPoses;
        Pose2d[] reefAlgaePoses;

        var alliance = DriverStation.getAlliance();

        if (alliance.isPresent() && alliance.get() == Alliance.Red) {

            reefCoralPoses = SetPointConstants.RED_REEF_CORAL_POSES;
            reefAlgaePoses = SetPointConstants.RED_REEF_ALGAE_POSES;
        } else {

            reefCoralPoses = SetPointConstants.BLUE_REEF_CORAL_POSES;
            reefAlgaePoses = SetPointConstants.BLUE_REEF_ALGAE_POSES;
        }

        Pose2d closestCoralPose = new Pose2d();
        double minCoralDistance = Double.MAX_VALUE;

        for (Pose2d coralPose : reefCoralPoses) {

            double distance = currentPose.getTranslation().getDistance(coralPose.getTranslation());

            if (distance < minCoralDistance) {

                minCoralDistance = distance;
                closestCoralPose = coralPose;
            }
        }

        Pose2d closestAlgaePose = new Pose2d();
        double minAlgaeDistance = Double.MAX_VALUE;

        for (Pose2d algaePose : reefAlgaePoses) {

            double distance = currentPose.getTranslation().getDistance(algaePose.getTranslation());

            if (distance < minAlgaeDistance) {

                minAlgaeDistance = distance;
                closestAlgaePose = algaePose;
            }
        }

        targetCoralPose = closestCoralPose;
        targetAlgaePose = closestAlgaePose;
    }

    public static void transformTargetCW () {

        Pose2d[] reefCoralPoses;
        Pose2d[] reefAlgaePoses;

        var alliance = DriverStation.getAlliance();

        if (alliance.isPresent() && alliance.get() == Alliance.Red) {

            reefCoralPoses = SetPointConstants.RED_REEF_CORAL_POSES;
            reefAlgaePoses = SetPointConstants.RED_REEF_ALGAE_POSES;
        } else {

            reefCoralPoses = SetPointConstants.BLUE_REEF_CORAL_POSES;
            reefAlgaePoses = SetPointConstants.BLUE_REEF_ALGAE_POSES;
        }

        int coralTargetIndex = 0;
        
        for (int i = 0; i < reefCoralPoses.length; i++) {

            if (reefCoralPoses[i].equals(targetCoralPose)) {

                coralTargetIndex = i;
                break;
            }
        }

        int algaeTargetIndex = 0;

        for (int i = 0; i < reefAlgaePoses.length; i++) {

            if (reefAlgaePoses[i].equals(targetAlgaePose)) {

                algaeTargetIndex = i;
                break;
            }
        }

        coralTargetIndex++;
        algaeTargetIndex++;

        if (coralTargetIndex >= reefCoralPoses.length) {

            coralTargetIndex = 0;
        }

        if (algaeTargetIndex >= reefAlgaePoses.length) {

            algaeTargetIndex = 0;
        }

        targetCoralPose = reefCoralPoses[coralTargetIndex];
        targetAlgaePose = reefAlgaePoses[algaeTargetIndex];
    }

    public static void transformTargetCCW () {

        Pose2d[] reefCoralPoses;
        Pose2d[] reefAlgaePoses;

        var alliance = DriverStation.getAlliance();

        if (alliance.isPresent() && alliance.get() == Alliance.Red) {

            reefCoralPoses = SetPointConstants.RED_REEF_CORAL_POSES;
            reefAlgaePoses = SetPointConstants.RED_REEF_ALGAE_POSES;
        } else {

            reefCoralPoses = SetPointConstants.BLUE_REEF_CORAL_POSES;
            reefAlgaePoses = SetPointConstants.BLUE_REEF_ALGAE_POSES;
        }

        int coralTargetIndex = 0;
        
        for (int i = 0; i < reefCoralPoses.length; i++) {

            if (reefCoralPoses[i].equals(targetCoralPose)) {

                coralTargetIndex = i;
                break;
            }
        }

        int algaeTargetIndex = 0;

        for (int i = 0; i < reefAlgaePoses.length; i++) {

            if (reefAlgaePoses[i].equals(targetAlgaePose)) {

                algaeTargetIndex = i;
                break;
            }
        }

        coralTargetIndex--;
        algaeTargetIndex--;

        if (coralTargetIndex < 0) {

            coralTargetIndex = reefCoralPoses.length - 1;
        }

        if (algaeTargetIndex < 0) {

            algaeTargetIndex = reefAlgaePoses.length - 1;
        }

        targetCoralPose = reefCoralPoses[coralTargetIndex];
        targetAlgaePose = reefAlgaePoses[algaeTargetIndex];
    }

    public static Pose2d getTargetCoralPose () {

        return targetCoralPose;
    }

    public static Pose2d getTargetAlgaePose () {

        return targetAlgaePose;
    }
}
