package frc.robot.commands.funnel;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.funnel.Funnel;
import frc.robot.util.SetPointConstants;

public class FunnelCoralOut extends Command {
    
    private final Funnel funnel;

    public FunnelCoralOut (Funnel funnel) {

        this.funnel = funnel;
        this.addRequirements(this.funnel);
    }

    @Override
    public void initialize () {

        this.funnel.runVoltage(SetPointConstants.CORAL_FUNNEL_OUT_VOLTAGE);
    }

    @Override
    public void end (boolean interrupted) {

        this.funnel.runVoltage(0.0);
    }
}
