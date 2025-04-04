package frc.robot.commands.swerve;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.swerve.Swerve;

public class AlignToReef extends SequentialCommandGroup {
    
    public AlignToReef (Swerve swerve, Pose2d[] reefPath) {

        super(
            Commands.runOnce(() -> SmartDashboard.putBoolean("Reef Aligned", false)),
            AutoBuilder.pathfindToPose(
                reefPath[0], 
                new PathConstraints(
                    3.0,
                    2.0,
                    Math.PI * 2,
                    Math.PI * 2,
                    12.0
                ), 
                reefPath[1].getTranslation().minus(reefPath[0].getTranslation()).getNorm() * 2.0
            ),
            new RotateToReef(swerve, reefPath[1]),
            new DriveToReef(swerve, reefPath[1]),
            Commands.runOnce(() -> SmartDashboard.putBoolean("Reef Aligned", true))
        );
    }
}
