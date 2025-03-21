package frc.robot.subsystems.swerve;

import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.system.plant.DCMotor;

public class SwerveConstants {
    
    public static final double MAX_SPEED = 4.34;

    // TODO PathPlanner Robot Configuration
    public static final RobotConfig ROBOT_CONFIG = new RobotConfig(
        0.0,
        4.0, 
        new ModuleConfig(
            0.1016,
            4.64, 
            1.542, 
            new DCMotor(
                12.0,
                2.6,
                105.0, 
                1.8, 
                594.389, 
                1), 
            7.125, 
            40.0, 
            1
        ), 
        new Translation2d[] {
            new Translation2d(0.2762, 0.2762),
            new Translation2d(0.2762, -0.2762),
            new Translation2d(-0.2762, 0.2762),
            new Translation2d(-0.2762, -0.2762)
        }
    );

    public static final PIDConstants PP_DRIVE_PID = new PIDConstants(5.0, 0.0, 0.0);
    public static final PIDConstants PP_TURN_PID = new PIDConstants(5.0, 0.0, 0.0);
}
