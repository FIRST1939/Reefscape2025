package frc.robot.subsystems.swerve.vision;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class VisionConstants {
    
    public static final Transform3d LEFT_TRANSFORM = new Transform3d(
        new Translation3d(0.202, -0.278, 0.494),
        new Rotation3d(0.0, Units.degreesToRadians(-27.0), Units.degreesToRadians(20.0))
    );

    public static final Transform3d RIGHT_TRANSFORM = new Transform3d(
        new Translation3d(0.202, 0.278, 0.494), 
        new Rotation3d(0.0, Units.degreesToRadians(-27.0), Units.degreesToRadians(-20.0))
    );
}
