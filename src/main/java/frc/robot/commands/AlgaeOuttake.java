package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class AlgaeOuttake extends Command{

    private EndEffector endEffector;

    private double algaeouttakespeed;

    public AlgaeOuttake(EndEffector endEffector, double algaeouttakespeed) {

        this.endEffector=endEffector;
        this.addRequirements(this.endEffector);
    }

    @Override
    public void execute () {
    
        endEffector.runVoltage(0, algaeouttakespeed,0);
    }

}



    

