package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.end_effector.EndEffector;

public class ScoreAlgae extends Command {
    
    private final EndEffector endEffector;

    public ScoreAlgae (EndEffector endEffector) {

        this.endEffector = endEffector;
        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeIntakeVoltage(SetPointConstants.ALGAE_SCORE_VOLTAGE);
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setAlgaeIntakeVoltage(0.0);
    }
}
