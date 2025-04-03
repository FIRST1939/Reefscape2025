package frc.robot.commands.swerve;

import java.util.List;
import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class AlignToReef extends Command {
    
    private final Supplier<Pose2d[]> pathSupplier;
    private Command pathfindingCommand;

    public AlignToReef (Supplier<Pose2d[]> pathSupplier) {

        this.pathSupplier = pathSupplier;
    }

    @Override
    public void initialize () {

        Pose2d[] targetPath = this.pathSupplier.get();
        List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(targetPath);

        PathConstraints alignmentConstraints = new PathConstraints(
            3.0,
            2.0,
            Math.PI * 2,
            Math.PI * 2,
            12.0
        );

        PathPlannerPath path = new PathPlannerPath(
            waypoints,
            alignmentConstraints,
            null,
            new GoalEndState(0.0, targetPath[1].getRotation())
        );

        path.preventFlipping = true;

        PathConstraints pathfindingConstraints = new PathConstraints(
            3.0,
            2.0,
            Math.PI * 2,
            Math.PI * 2,
            12.0
        );

        this.pathfindingCommand = AutoBuilder.pathfindThenFollowPath(path, pathfindingConstraints);
        this.pathfindingCommand.schedule();

        SmartDashboard.putBoolean("Reef Aligned", false);
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

        SmartDashboard.putBoolean("Reef Aligned", true);
    }
}
