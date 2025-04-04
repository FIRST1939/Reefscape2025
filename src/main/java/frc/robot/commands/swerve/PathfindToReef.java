package frc.robot.commands.swerve;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;

public class PathfindToReef extends Command {
    
    private final Pose2d[] reefPath;
    private Command pathfindingCommand;

    public PathfindToReef (Pose2d[] reefPath) {

        this.reefPath = reefPath;
    }

    @Override
    public void initialize () {

        PathConstraints pathfindingConstraints = new PathConstraints(
            3.0,
            2.0,
            Math.PI * 2,
            Math.PI * 2,
            12.0
        );

        double goalEndVelocity = this.reefPath[1].getTranslation().minus(this.reefPath[0].getTranslation()).getNorm();

        this.pathfindingCommand = AutoBuilder.pathfindToPose(this.reefPath[0], pathfindingConstraints, goalEndVelocity);
        this.pathfindingCommand.schedule();
    }

    @Override
    public void execute () {

        System.out.println(this.pathfindingCommand.isFinished());
    }

    @Override
    public boolean isFinished () {

        return this.pathfindingCommand.isFinished();
    }

    @Override
    public void end (boolean interrupted) {

        if (this.pathfindingCommand != null) {

            this.pathfindingCommand.cancel();
        }
    }
}
