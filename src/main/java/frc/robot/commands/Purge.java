package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class Purge extends Command{

    private EndEffector endEffector;

    private double coralpurgespeed;

    private double algaepurgespeed;
    
    public Purge (EndEffector endEffector, double coralpurgespeed, double algaepurgespeed) {
        
        this.endEffector = endEffector;
        this.addRequirements(this.endEffector);
    
    }

    @Override
    public void execute () {

        endEffector.runVoltage(coralpurgespeed, algaepurgespeed, 0);
    }
}
