package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class AlgaeIntake extends Command {
    private double algaeintakespeed;
    private EndEffector endEffector;
        public AlgaeIntake(EndEffector endEffector, double algaeintakespeed) {
            this.endEffector=endEffector;
            this.algaeintakespeed = algaeintakespeed;
            this.addRequirements(this.endEffector);
    }

    @Override
    public void end(boolean interrupted) {
        
        endEffector.runVoltage(0, algaeintakespeed, 0); 
    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}


    


