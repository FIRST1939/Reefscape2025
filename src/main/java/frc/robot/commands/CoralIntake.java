package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.end_effector.EndEffector;

public class CoralIntake extends Command{
    private EndEffector endEffector;
    private double coralintakespeed;
    public CoralIntake(EndEffector endEffector, double coralintakespeed) {
        this.endEffector=endEffector;
        this.addRequirements(endEffector);
    }

    public CoralIntake(double coralintakespeed) {
        
    }
    @Override
    public void initialize () {

        endEffector.runVoltage(coralintakespeed, 0, 0);
    }   
}
