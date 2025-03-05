package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class CoralMove extends Command{

    private EndEffector endEffector;
    
    private double coralintakespeed;
    
    public CoralMove(EndEffector endEffector, double coralintakespeed) {
     
        this.endEffector=endEffector;
        this.addRequirements(this.endEffector);
    }

    @Override
    public void execute () {

        endEffector.runVoltage(coralintakespeed, 0, 0);
    }   
}
