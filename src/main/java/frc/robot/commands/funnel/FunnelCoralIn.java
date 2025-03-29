package frc.robot.commands.funnel;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.funnel.Funnel;

public class FunnelCoralIn extends Command {
    
    private final Funnel funnel;

    public FunnelCoralIn (Funnel funnel) {

        this.funnel = funnel;
        this.addRequirements(this.funnel);
    }

    @Override
    public void initialize () {

        this.funnel.runVoltage(SetPointConstants.CORAL_FUNNEL_IN_VOLTAGE);
    }

    @Override
    public void end (boolean interrupted) {

        this.funnel.runVoltage(0.0);
    }
}
