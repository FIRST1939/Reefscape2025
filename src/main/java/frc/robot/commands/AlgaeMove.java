package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class AlgaeMove extends Command{

    private EndEffector endEffector;

    private double algaeouttakespeed;

    public AlgaeMove(EndEffector endEffector, double algaeouttakespeed) {

        this.endEffector=endEffector;
        this.algaeouttakespeed = algaeouttakespeed;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeIntakeVoltage(algaeouttakespeed);
    }
    @Override
    public void end (boolean interrupted) {
        
        this.endEffector.setAlgaeIntakeVoltage(0.0);
    }
}
