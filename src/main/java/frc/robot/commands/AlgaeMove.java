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
    //Start
    public void execute () {
        endEffector.runVoltage(0, algaeouttakespeed,0);
    }
    @Override
    public void end (boolean interrupted) 
    {
        endEffector.runVoltage(0, 0,0);
    }

}



    

