package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.funnel.Funnel;

public class LoadCoral extends ParallelCommandGroup{

    public LoadCoral (Funnel funnel, EndEffector endEffector, double coralInSpeed, double coralBackSpeed) {
        this.addCommands(
            new AutomaticFunnel(funnel, SetPointConstants.FUNNEL_INTAKE_SPEED, SetPointConstants.FUNNEL_STUCK_SPEED),
            Commands.sequence(
                new AutomaticEndEffectorIn(endEffector, coralInSpeed),
                new AutomaticEndEffectorBack(endEffector, coralBackSpeed)
            )
        );
    }
}
