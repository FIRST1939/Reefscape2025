package frc.robot.commands.swerve;

import java.util.List;
import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.Swerve;

public class AlignToPose extends Command {
    
    private final Swerve swerve;
    private final Supplier<Pose2d> poseSupplier;

    private Command pathfindingCommand;

    public AlignToPose (Swerve swerve, Supplier<Pose2d> poseSupplier) {

        this.swerve = swerve;
        this.poseSupplier = poseSupplier;
    }

    @Override
    public void initialize () {

        Pose2d currentPose = this.swerve.getPose();
        Pose2d targetPose = this.poseSupplier.get();

        List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(
            new Pose2d(currentPose.getTranslation(), targetPose.minus(currentPose).getTranslation().getAngle()),
            targetPose
        );

        PathConstraints constraints = new PathConstraints(
            4.0,
            3.0,
            Math.PI * 2,
            Math.PI * 2,
            12.0
        );

        PathPlannerPath path = new PathPlannerPath(
            waypoints,
            constraints,
            null,
            new GoalEndState(0.0, targetPose.getRotation())
        );

        path.preventFlipping = true;

        this.pathfindingCommand = AutoBuilder.followPath(path);
        this.pathfindingCommand.schedule();
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
