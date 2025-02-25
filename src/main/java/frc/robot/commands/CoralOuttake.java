package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SetPointConstants;
import frc.robot.end_effector.EndEffector;

public class CoralOuttake extends Command{
    private EndEffector endEffector;
    double coralIntakeSpeed;
    public CoralOuttake(EndEffector endEffector, double coralIntakeSpeed){
        this.endEffector=endEffector;
        this.addRequirements(endEffector);
this.coralIntakeSpeed = coralIntakeSpeed;
    }
    @Override
    public void initialize () 
    {
        endEffector.runVoltage(-coralIntakeSpeed,0,0);
    } 
}
