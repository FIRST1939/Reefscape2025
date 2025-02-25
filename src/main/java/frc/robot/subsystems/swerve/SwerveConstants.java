package frc.robot.subsystems.swerve;

import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.RobotConfig;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.system.plant.DCMotor;

public class SwerveConstants {
    
    public static final double MAX_SPEED = 0.0; // TODO Swerve Max Speed

    // TODO Swerve Module Feedforwards
    public static final SimpleMotorFeedforward[] MODULE_FEEDFORWARDS = {
        new SimpleMotorFeedforward(0.0, 0.0),
        new SimpleMotorFeedforward(0.0, 0.0),
        new SimpleMotorFeedforward(0.0, 0.0),
        new SimpleMotorFeedforward(0.0, 0.0)
    };

    // TODO Limelight Positions
    public static final Pose3d LEFT_LIMELIGHT_POSITION = new Pose3d(
        new Translation3d(0.199548, -0.278274, 0.495475), 
        new Rotation3d(0.0, 25.584974, 9.885544)
    );

    public static final Pose3d RIGHT_LIMELIGHT_POSITION = new Pose3d(
        new Translation3d(0.200643, 0.276554, 0.492158), 
        new Rotation3d(0.0, 25.584974, 9.885544)
    );

    // TODO PathPlanner Robot Configuration
    public static final RobotConfig ROBOT_CONFIG = new RobotConfig(
        0.0, 
        0.0, 
        new ModuleConfig(
            0.0, 
            0.0, 
            0.0, 
            new DCMotor(
                0.0, 
                0.0, 
                0.0, 
                0.0, 
                0.0, 
                0), 
            0.0, 
            0.0, 
            0
        ), 
        new Translation2d[] {}
    );
}
