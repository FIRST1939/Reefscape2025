package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;
public class AlgaeOuttake extends Command{
    private EndEffector endEffector;
    private Double algaeouttakespeedSupplier;
    public AlgaeOuttake(EndEffector endEffector, DoubleSupplier algaeouttakespeed) {
        this.endEffector=endEffector;
    }
        //TODO Auto-generated constructor stub

@Override
public void initialize ()
{
    execute();
    endEffector.runVoltage(0,algaeouttakespeedSupplier,0);
}

    
}
