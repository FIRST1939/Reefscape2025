package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class SetPointConstants {

    public static final double CORAL_INTAKE_HEIGHT = -0.015;
    public static final double CORAL_OUTTAKE_HEIGHT_L2 = 0.55;
    public static final double CORAL_OUTTAKE_HEIGHT_L3 = 0.97;
    public static final double CORAL_OUTTAKE_HEIGHT_L4 = 1.59;

    public static final double ALGAE_INTAKE_GROUND_HEIGHT = -0.15;
    public static final double ALGAE_INTAKE_LOW_HEIGHT = 0.37;
    public static final double ALGAE_INTAKE_HIGH_HEIGHT = 0.76;
    public static final double ALGAE_OUTTAKE_PROCESSOR_HEIGHT = 0.0;
    public static final double ALGAE_OUTTAKE_NET_HEIGHT = 1.68;

    public static final double ELEVATOR_MAXIMUM_MANUAL_SPEED = 3.0;

    public static final double CORAL_IN_SPEED = 10.0;
    public static final double CORAL_BACK_SPEED = -15.0; 
    public static final double CORAL_BACK_DISTANCE = 4.0;

    public static final double CORAL_SCORE_SPEED = 25.0;
    public static final double CORAL_SCORE_DISTANCE = 25.0;

    public static final double CORAL_FUNNEL_IN_VOLTAGE = 1.5;
    public static final double CORAL_FUNNEL_OUT_VOLTAGE = -1.5;
    public static final double CORAL_FUNNEL_IN_TIMEOUT = 3.0;
    public static final double CORAL_FUNNEL_OUT_TIMEOUT = 3.0;

    public static final double ALGAE_INTAKE_REEF_WRIST_POSITION = 150.0;
    public static final double ALGAE_INTAKE_GROUND_WRIST_POSITION = 215.0;

    public static final double ALGAE_INTAKE_VOLTAGE = 4.0;
    public static final double ALGAE_SCORE_VOLTAGE = -3.0;

    public static final Pose2d[][] RED_REEF_CORAL_POSES = {
        {new Pose2d(new Translation2d(15.021, 4.197), Rotation2d.fromDegrees(180)), new Pose2d(new Translation2d(14.321, 4.197), Rotation2d.fromDegrees(180))},
        {new Pose2d(new Translation2d(15.021, 3.868), Rotation2d.fromDegrees(180)), new Pose2d(new Translation2d(14.321, 3.868), Rotation2d.fromDegrees(180))},
        {new Pose2d(new Translation2d(14.335, 2.160), Rotation2d.fromDegrees(120)), new Pose2d(new Translation2d(13.835, 3.026), Rotation2d.fromDegrees(120))},
        {new Pose2d(new Translation2d(14.050, 1.995), Rotation2d.fromDegrees(120)), new Pose2d(new Translation2d(13.550, 2.861), Rotation2d.fromDegrees(120))},
        {new Pose2d(new Translation2d(12.070, 1.995), Rotation2d.fromDegrees(60)), new Pose2d(new Translation2d(12.578, 2.861), Rotation2d.fromDegrees(60))},
        {new Pose2d(new Translation2d(11.793, 2.160), Rotation2d.fromDegrees(60)), new Pose2d(new Translation2d(12.293, 3.026), Rotation2d.fromDegrees(60))},
        {new Pose2d(new Translation2d(11.007, 3.868), Rotation2d.fromDegrees(0)), new Pose2d(new Translation2d(11.807, 3.868), Rotation2d.fromDegrees(0))},
        {new Pose2d(new Translation2d(11.007, 4.197), Rotation2d.fromDegrees(0)), new Pose2d(new Translation2d(11.807, 4.197), Rotation2d.fromDegrees(0))},
        {new Pose2d(new Translation2d(11.793, 5.905), Rotation2d.fromDegrees(-60)), new Pose2d(new Translation2d(12.293, 5.039), Rotation2d.fromDegrees(-60))},
        {new Pose2d(new Translation2d(12.078, 6.069), Rotation2d.fromDegrees(-60)), new Pose2d(new Translation2d(12.578, 5.203), Rotation2d.fromDegrees(-60))},
        {new Pose2d(new Translation2d(14.050, 6.069), Rotation2d.fromDegrees(-120)), new Pose2d(new Translation2d(13.550, 5.203), Rotation2d.fromDegrees(-120))},
        {new Pose2d(new Translation2d(14.335, 5.905), Rotation2d.fromDegrees(-120)), new Pose2d(new Translation2d(13.835, 5.039), Rotation2d.fromDegrees(-120))}
    };

    public static final Pose2d[][] BLUE_REEF_CORAL_POSES = {
        {new Pose2d(new Translation2d(2.516, 3.857), Rotation2d.fromDegrees(0)), new Pose2d(new Translation2d(3.216, 3.857), Rotation2d.fromDegrees(0))},
        {new Pose2d(new Translation2d(2.516, 4.186), Rotation2d.fromDegrees(0)), new Pose2d(new Translation2d(3.216, 4.186), Rotation2d.fromDegrees(0))},
        {new Pose2d(new Translation2d(3.202, 5.905), Rotation2d.fromDegrees(-60)), new Pose2d(new Translation2d(3.702, 5.050), Rotation2d.fromDegrees(-60))},
        {new Pose2d(new Translation2d(3.487, 6.069), Rotation2d.fromDegrees(-60)), new Pose2d(new Translation2d(3.987, 5.203), Rotation2d.fromDegrees(-60))},
        {new Pose2d(new Translation2d(5.459, 6.069), Rotation2d.fromDegrees(-120)), new Pose2d(new Translation2d(4.959, 5.203), Rotation2d.fromDegrees(-120))},
        {new Pose2d(new Translation2d(5.744, 5.878), Rotation2d.fromDegrees(-120)), new Pose2d(new Translation2d(5.244, 5.012), Rotation2d.fromDegrees(-120))},
        {new Pose2d(new Translation2d(6.572, 4.186), Rotation2d.fromDegrees(180)), new Pose2d(new Translation2d(5.772, 4.186), Rotation2d.fromDegrees(180))},
        {new Pose2d(new Translation2d(6.572, 3.857), Rotation2d.fromDegrees(180)), new Pose2d(new Translation2d(5.772, 3.857), Rotation2d.fromDegrees(180))},
        {new Pose2d(new Translation2d(5.799, 2.091), Rotation2d.fromDegrees(120)), new Pose2d(new Translation2d(5.299, 2.957), Rotation2d.fromDegrees(120))},
        {new Pose2d(new Translation2d(5.459, 1.974), Rotation2d.fromDegrees(120)), new Pose2d(new Translation2d(4.959, 2.840), Rotation2d.fromDegrees(120))},
        {new Pose2d(new Translation2d(3.487, 1.974), Rotation2d.fromDegrees(60)), new Pose2d(new Translation2d(3.987, 2.840), Rotation2d.fromDegrees(60))},
        {new Pose2d(new Translation2d(3.202, 2.138), Rotation2d.fromDegrees(60)), new Pose2d(new Translation2d(3.702, 3.004), Rotation2d.fromDegrees(60))}
    };

    public static final Pose2d[][] RED_REEF_ALGAE_POSES = {
        {new Pose2d(new Translation2d(14.021, 4.032), Rotation2d.fromDegrees(-180)), new Pose2d(new Translation2d(14.321, 4.032), Rotation2d.fromDegrees(-180))},
        {new Pose2d(new Translation2d(14.058, 2.077), Rotation2d.fromDegrees(120)), new Pose2d(new Translation2d(13.692, 2.943), Rotation2d.fromDegrees(120))},
        {new Pose2d(new Translation2d(11.935, 2.077), Rotation2d.fromDegrees(60)), new Pose2d(new Translation2d(12.435, 2.943), Rotation2d.fromDegrees(60))},
        {new Pose2d(new Translation2d(11.007, 4.032), Rotation2d.fromDegrees(0)), new Pose2d(new Translation2d(11.807, 4.032), Rotation2d.fromDegrees(0))},
        {new Pose2d(new Translation2d(11.935, 5.987), Rotation2d.fromDegrees(-60)), new Pose2d(new Translation2d(12.435, 5.121), Rotation2d.fromDegrees(-60))},
        {new Pose2d(new Translation2d(14.193, 5.987), Rotation2d.fromDegrees(-120)), new Pose2d(new Translation2d(13.693, 5.121), Rotation2d.fromDegrees(-120))}
    };

    public static final Pose2d[][] BLUE_REEF_ALGAE_POSES = {
        {new Pose2d(new Translation2d(3.515, 4.010), Rotation2d.fromDegrees(0)), new Pose2d(new Translation2d(3.215, 4.010), Rotation2d.fromDegrees(0))},
        {new Pose2d(new Translation2d(3.344, 5.965), Rotation2d.fromDegrees(-60)), new Pose2d(new Translation2d(3.844, 5.099), Rotation2d.fromDegrees(-60))},
        {new Pose2d(new Translation2d(5.601, 5.965), Rotation2d.fromDegrees(-120)), new Pose2d(new Translation2d(5.101, 5.099), Rotation2d.fromDegrees(-120))},
        {new Pose2d(new Translation2d(6.529, 4.010), Rotation2d.fromDegrees(180)), new Pose2d(new Translation2d(5.729, 4.010), Rotation2d.fromDegrees(180))},
        {new Pose2d(new Translation2d(5.601, 2.056), Rotation2d.fromDegrees(120)), new Pose2d(new Translation2d(5.101, 2.922), Rotation2d.fromDegrees(120))},
        {new Pose2d(new Translation2d(3.344, 2.056), Rotation2d.fromDegrees(60)), new Pose2d(new Translation2d(3.844, 2.922), Rotation2d.fromDegrees(60))}
    };
}
