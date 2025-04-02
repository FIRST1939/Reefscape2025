package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.leds.LEDs;
import frc.robot.util.SetPointConstants;

public class ScoreAlgae extends Command {
    
    private final EndEffector endEffector;
    private final LEDs leds;

    public ScoreAlgae (EndEffector endEffector,LEDs leds) {

        this.endEffector = endEffector;
        this.leds = leds;
        this.addRequirements(this.endEffector, this.leds);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeIntakeVoltage(SetPointConstants.ALGAE_SCORE_VOLTAGE);
        this.leds.setAlgaeProcessing();
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setAlgaeIntakeVoltage(0.0);
        this.leds.setAlliance();
    }
}
