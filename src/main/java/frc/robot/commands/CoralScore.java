package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SetPointConstants;

public class CoralScore extends SequentialCommandGroup{
    public CoralScore()
    {
        this.addCommands(new ElevatorMoveToHeight(SetPointConstants.coralIntakeHeight), andThen(new CoralIntake(0)),
        alongWith(new FunnelIntake()));    
    }
}