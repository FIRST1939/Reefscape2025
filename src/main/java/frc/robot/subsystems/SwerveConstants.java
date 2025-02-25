package frc.robot.subsystems;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;

public class SwerveConstants {
    
    public static final double MAX_SPEED = 0.0; // TODO Swerve Max Speed

    // TODO Swerve Module Feedforwards
    public static final SimpleMotorFeedforward[] MODULE_FEEDFORWARDS = {
        new SimpleMotorFeedforward(0.0, 0.0),
        new SimpleMotorFeedforward(0.0, 0.0),
        new SimpleMotorFeedforward(0.0, 0.0),
        new SimpleMotorFeedforward(0.0, 0.0)
    };
}
