package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SetPointConstants;

public class CoralLoad extends SequentialCommandGroup{
    public CoralLoad()
    {
        this.addCommands(
            (new ElevatorMoveToHeight(SetPointConstants.coralIntakeHeight)
            .andThen(new CoralIntake()).alongWith(new FunnelIntake())));    
    }
}