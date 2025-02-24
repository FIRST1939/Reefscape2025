package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.end_effector.EndEffector;
public class AlgaeOuttake extends Command{
    private EndEffector endEffector;
    private DoubleSupplier algaeouttakespeedSupplier;
    public AlgaeOuttake(EndEffector endEffector, DoubleSupplier algaeouttakespeed) {
        this.endEffector=endEffector;
        this.addRequirements(endEffector);
    }

public AlgaeOuttake(double algaeouttakespeed) {
        //TODO Auto-generated constructor stub
    }

@Override
public void initialize () 
{
    endEffector.runVoltage(0,algaeouttakespeedSupplier.getAsDouble(),0);
}

    
}
