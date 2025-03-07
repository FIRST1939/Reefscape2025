package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.SetPointConstants;
import frc.robot.commands.end_effector.AutomaticEndEffectorBack;
import frc.robot.commands.end_effector.AutomaticEndEffectorIn;
import frc.robot.commands.funnel.AutomaticFunnel;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.funnel.Funnel;

public class LoadCoral extends ParallelDeadlineGroup{

    public LoadCoral (Funnel funnel, EndEffector endEffector, double coralInSpeed, double coralBackSpeed, CommandXboxController controller) {

        super(
            Commands.sequence(
                new AutomaticEndEffectorIn(endEffector, coralInSpeed),
                new AutomaticEndEffectorBack(endEffector, coralBackSpeed),
                new RumbleController(controller).withTimeout(0.5)
            ),
            new AutomaticFunnel(funnel, SetPointConstants.FUNNEL_INTAKE_SPEED, SetPointConstants.FUNNEL_STUCK_SPEED)
        );
    }
}
