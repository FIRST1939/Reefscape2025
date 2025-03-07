package frc.robot.commands.funnel;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.funnel.Funnel;

public class AutomaticFunnel extends Command{
    
    private final Funnel funnel;
    private final double intakeVoltage;
    private final double stuckVoltage;

    private boolean isFinished;
      
    public AutomaticFunnel (Funnel funnel, double intakeVoltage, double stuckVoltage) {
        
        this.funnel = funnel;
        this.intakeVoltage = intakeVoltage;
        this.stuckVoltage = stuckVoltage;

        new Trigger(this.funnel::getBeambreak).onTrue(Commands.runOnce(() -> this.isFinished = true));
        this.addRequirements(this.funnel);
    }

    @Override
    public void initialize () {

        this.isFinished = false;
    }
        
    @Override
    public void execute () {

        if (!this.funnel.coralStuck()) { this.funnel.runVoltage(this.intakeVoltage); }
        else { this.funnel.runVoltage(this.stuckVoltage); }
    }

    @Override
    public boolean isFinished () {

        return this.isFinished;
    }

    @Override
    public void end (boolean interrupted) {

        this.funnel.runVoltage(0.0);
    }
}
