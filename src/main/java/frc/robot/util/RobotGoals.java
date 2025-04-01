package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class RobotGoals {
    
    private static Pose2d[] targetCoralPath = SetPointConstants.RED_REEF_CORAL_POSES[0];
    private static Pose2d[] targetAlgaePath = SetPointConstants.RED_REEF_ALGAE_POSES[0];

    public static void transformTargetCW () {

        Pose2d[][] reefCoralPaths;
        Pose2d[][] reefAlgaePaths;

        var alliance = DriverStation.getAlliance();

        if (alliance.isPresent() && alliance.get() == Alliance.Red) {

            reefCoralPaths = SetPointConstants.RED_REEF_CORAL_POSES;
            reefAlgaePaths = SetPointConstants.RED_REEF_ALGAE_POSES;
        } else {

            reefCoralPaths = SetPointConstants.BLUE_REEF_CORAL_POSES;
            reefAlgaePaths = SetPointConstants.BLUE_REEF_ALGAE_POSES;
        }

        int coralTargetIndex = 0;
        int algaeTargetIndex = 0;
        
        for (int i = 0; i < reefCoralPaths.length; i++) {

            if (reefCoralPaths[i].equals(targetCoralPath)) {

                coralTargetIndex = i;
                break;
            }
        }

        coralTargetIndex--;

        if (coralTargetIndex >= reefCoralPaths.length) {

            coralTargetIndex = 0;
        }

        algaeTargetIndex = coralTargetIndex / 2;

        targetCoralPath = reefCoralPaths[coralTargetIndex];
        targetAlgaePath = reefAlgaePaths[algaeTargetIndex];
    }

    public static void transformTargetCCW () {

        Pose2d[][] reefCoralPaths;
        Pose2d[][] reefAlgaePaths;

        var alliance = DriverStation.getAlliance();

        if (alliance.isPresent() && alliance.get() == Alliance.Red) {

            reefCoralPaths = SetPointConstants.RED_REEF_CORAL_POSES;
            reefAlgaePaths = SetPointConstants.RED_REEF_ALGAE_POSES;
        } else {

            reefCoralPaths = SetPointConstants.BLUE_REEF_CORAL_POSES;
            reefAlgaePaths = SetPointConstants.BLUE_REEF_ALGAE_POSES;
        }

        int coralTargetIndex = 0;
        int algaeTargetIndex = 0;
        
        for (int i = 0; i < reefCoralPaths.length; i++) {

            if (reefCoralPaths[i].equals(targetCoralPath)) {

                coralTargetIndex = i;
                break;
            }
        }

        coralTargetIndex++;

        if (coralTargetIndex < 0) {

            coralTargetIndex = reefCoralPaths.length - 1;
        }

        algaeTargetIndex = coralTargetIndex / 2;

        targetCoralPath = reefCoralPaths[coralTargetIndex];
        targetAlgaePath = reefAlgaePaths[algaeTargetIndex];
    }

    public static void setManualIndex (int postSelector) {
        var alliance = DriverStation.getAlliance();

        Pose2d[][] reefCoralPaths;
        Pose2d[][] reefAlgaePaths;

        if (alliance.isPresent() && alliance.get() == Alliance.Red) {

            reefCoralPaths = SetPointConstants.RED_REEF_CORAL_POSES;
            reefAlgaePaths = SetPointConstants.RED_REEF_ALGAE_POSES;
        } else {

            reefCoralPaths = SetPointConstants.BLUE_REEF_CORAL_POSES;
            reefAlgaePaths = SetPointConstants.BLUE_REEF_ALGAE_POSES;
        }
        
        int algaeTargetIndex = postSelector / 2;

        targetCoralPath = reefCoralPaths[postSelector];
        targetAlgaePath = reefAlgaePaths[algaeTargetIndex];
    }

    public static Pose2d[] getTargetCoralPath () {

        return targetCoralPath;
    }

    public static Pose2d[] getTargetAlgaePath () {

        return targetAlgaePath;
    }
}
