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
    
    public static final double MAX_SPEED = 4.34;

    // TODO PathPlanner Robot Configuration

    // This throws.. 
    // public static final RobotConfig ROBOT_CONFIG = new RobotConfig(
    //     0.0, 
    //     0.0, 
    //     new ModuleConfig(
    //         0.0, 
    //         0.0, 
    //         0.0, 
    //         new DCMotor(
    //             0.0, 
    //             0.0, 
    //             0.0, 
    //             0.0, 
    //             0.0, 
    //             0), 
    //         0.0, 
    //         0.0, 
    //         0
    //     ), 
    //     new Translation2d[] {}
    // );
}
