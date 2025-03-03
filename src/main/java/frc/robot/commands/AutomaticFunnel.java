package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.funnel.Funnel;

public class AutomaticFunnel extends Command{
    
    private final Funnel funnel;
    private final double voltage;

    private boolean isFinished = false;
      
    public AutomaticFunnel (Funnel funnel, double voltage) {
        
        this.funnel = funnel;
        this.voltage = voltage;

        new Trigger(this.funnel::getBeambreak).onTrue(Commands.run(() -> this.isFinished = true));
    }
        
    @Override
    public void initialize () {

        this.funnel.runVoltage(this.voltage);  
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
