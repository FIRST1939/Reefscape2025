package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.end_effector.EndEffector;

public class AlgaeScore extends SequentialCommandGroup{

    public AlgaeScore(EndEffector endEffector)
    {
        this.addCommands(
            //new ElevatorMoveToHeight(SetPointConstants.ALGAE_INTAKE_HEIGHT),
            new AlgaeMove(endEffector, 0)
        );    
    }
}