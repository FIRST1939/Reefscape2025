package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class SetPointConstants {
    public static final double CORAL_INTAKE_HEIGHT = 0.0;
    public static final double CORAL_OUTTAKE_HEIGHT_L1 = 0.15;
    public static final double CORAL_OUTTAKE_HEIGHT_L2 = 0.75;
    public static final double CORAL_OUTTAKE_HEIGHT_L3 = 1.25;
    public static final double CORAL_OUTTAKE_HEIGHT_L4 = 1.5;
    public static final double ELEVATOR_MAXIMUM_MANUAL_SPEED = 0.0;
    public static final double ALGAE_INTAKE_SPEED = 2.0;
    public static final double ALGAE_OUTTAKE_SPEED = -2.0;
    public static final double CORAL_INTAKE_SPEED = 0.0;
    public static final double FUNNEL_INTAKE_SPEED = 1.5;
    public static final double FUNNEL_OUTTAKE_SPEED = -1.5;
    public static final double FUNNEL_STUCK_SPEED = -1.65;
    public static final double CORAL_OUTTAKE_SPEED = 25.0;

    public static final Pose2d[] REEF_CORAL_POSITIONS = {
        new Pose2d(new Translation2d(3.216, 4.186), Rotation2d.fromDegrees(0)),
        new Pose2d(new Translation2d(3.216, 3.857), Rotation2d.fromDegrees(0)),
        new Pose2d(new Translation2d(3.702, 3.004), Rotation2d.fromDegrees(60)),
        new Pose2d(new Translation2d(3.987, 2.840), Rotation2d.fromDegrees(60)),
        new Pose2d(new Translation2d(4.959, 2.840), Rotation2d.fromDegrees(120)),
        new Pose2d(new Translation2d(5.299, 2.957), Rotation2d.fromDegrees(120)),
        new Pose2d(new Translation2d(5.772, 3.857), Rotation2d.fromDegrees(180)),
        new Pose2d(new Translation2d(5.772, 4.186), Rotation2d.fromDegrees(180)),
        new Pose2d(new Translation2d(5.244, 5.012), Rotation2d.fromDegrees(-120)),
        new Pose2d(new Translation2d(4.959, 5.203), Rotation2d.fromDegrees(-120)),
        new Pose2d(new Translation2d(3.987, 5.203), Rotation2d.fromDegrees(-60)),
        new Pose2d(new Translation2d(3.702, 5.039), Rotation2d.fromDegrees(-60)),
        new Pose2d(new Translation2d(14.321, 3.868), Rotation2d.fromDegrees(180)),
        new Pose2d(new Translation2d(14.321, 4.197), Rotation2d.fromDegrees(180)),
        new Pose2d(new Translation2d(13.835, 5.039), Rotation2d.fromDegrees(-120)),
        new Pose2d(new Translation2d(13.550, 5.203), Rotation2d.fromDegrees(-120)),
        new Pose2d(new Translation2d(12.578, 5.203), Rotation2d.fromDegrees(-60)),
        new Pose2d(new Translation2d(12.293, 5.039), Rotation2d.fromDegrees(-60)),
        new Pose2d(new Translation2d(11.807, 4.197), Rotation2d.fromDegrees(0)),
        new Pose2d(new Translation2d(11.807, 3.868), Rotation2d.fromDegrees(0)),
        new Pose2d(new Translation2d(12.293, 3.026), Rotation2d.fromDegrees(60)),
        new Pose2d(new Translation2d(12.578, 2.861), Rotation2d.fromDegrees(60)),
        new Pose2d(new Translation2d(13.550, 2.861), Rotation2d.fromDegrees(120)),
        new Pose2d(new Translation2d(13.835, 3.026), Rotation2d.fromDegrees(120))
    };

    public static final Pose2d[] REEF_ALGAE_POSITIONS = {
        new Pose2d(new Translation2d(3.215, 4.010), Rotation2d.fromDegrees(0)),
        new Pose2d(new Translation2d(3.844, 2.922), Rotation2d.fromDegrees(60)),
        new Pose2d(new Translation2d(5.101, 2.922), Rotation2d.fromDegrees(120)),
        new Pose2d(new Translation2d(5.729, 4.010), Rotation2d.fromDegrees(180)),
        new Pose2d(new Translation2d(5.101, 5.099), Rotation2d.fromDegrees(-120)),
        new Pose2d(new Translation2d(3.844, 5.099), Rotation2d.fromDegrees(-60)),
        new Pose2d(new Translation2d(14.321, 4.032), Rotation2d.fromDegrees(-180)),
        new Pose2d(new Translation2d(13.693, 5.121), Rotation2d.fromDegrees(-120)),
        new Pose2d(new Translation2d(12.435, 5.121), Rotation2d.fromDegrees(-60)),
        new Pose2d(new Translation2d(11.807, 4.032), Rotation2d.fromDegrees(0)),
        new Pose2d(new Translation2d(12.435, 2.943), Rotation2d.fromDegrees(60)),
        new Pose2d(new Translation2d(13.692, 2.943), Rotation2d.fromDegrees(120))
        
    };
}
