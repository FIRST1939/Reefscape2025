package frc.robot.commands.funnel;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.funnel.Funnel;
import frc.robot.util.SetPointConstants;

public class OuttakeCoral extends Command {
    
    private final Funnel funnel;

    public OuttakeCoral (Funnel funnel) {

        this.funnel = funnel;
        this.addRequirements(this.funnel);
    }

    @Override
    public void initialize () {

        this.funnel.setMotorVoltage(SetPointConstants.FUNNEL_OUTTAKE_VOLTAGE);
    }

    @Override
    public void end (boolean interrupted) {

        this.funnel.setMotorVoltage(0);
    }
}