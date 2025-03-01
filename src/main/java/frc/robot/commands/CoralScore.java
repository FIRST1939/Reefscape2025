package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.end_effector.EndEffector;

public class CoralScore extends SequentialCommandGroup{

    public CoralScore(EndEffector endEffector)
    {
        this.addCommands(
            new ElevatorMoveToHeight(SetPointConstants.CORAL_INTAKE_HEIGHT),
            new CoralMove(endEffector, 0)
            //new FunnelIntake(endEffector, 0)
        );    
    }
}