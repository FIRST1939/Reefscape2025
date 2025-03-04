package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.funnel.Funnel;

public class ManualFunnel extends Command{
    
    private final Funnel funnel;
    private final double voltage;
      
    public ManualFunnel (Funnel funnel, double voltage) {
        
        this.funnel = funnel;
        this.voltage = voltage;

        this.addRequirements(this.funnel);
    }
        
    @Override
    public void initialize () {

        this.funnel.runVoltage(this.voltage);  
    }

    @Override
    public void end (boolean interrupted) {

        this.funnel.runVoltage(0.0);
    }
}
