package frc.robot.commands.funnel;

import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.funnel.Funnel;

public class FunnelCoral extends RepeatCommand {
    
    public FunnelCoral (Funnel funnel) {

        super(
            new SequentialCommandGroup(
                new FunnelCoralIn(funnel).withTimeout(SetPointConstants.CORAL_FUNNEL_IN_TIMEOUT),
                new FunnelCoralOut(funnel).withTimeout(SetPointConstants.CORAL_FUNNEL_OUT_TIMEOUT)
            )
        );
    }
}
