package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class ScoreAlgae extends Command {
    
    private final EndEffector endEffector;
    private final double algaeScoreVoltage;

    public ScoreAlgae (EndEffector endEffector, double algaeScoreVoltage) {

        this.endEffector = endEffector;
        this.algaeScoreVoltage = algaeScoreVoltage;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeIntakeVoltage(this.algaeScoreVoltage);
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setAlgaeIntakeVoltage(0.0);
    }
}
