package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.leds.LEDs;

public class ScoreCoral extends Command {

    private final EndEffector endEffector;
    private final LEDs leds;
    private double coralPosition;
    
    public ScoreCoral(EndEffector endEffector, LEDs leds) {
        
        this.endEffector = endEffector;
        this.leds = leds;
        this.addRequirements(this.endEffector, this.leds);
    }

    @Override
    public void initialize () {

        this.coralPosition = this.endEffector.getCoralIntakePosition();
        this.endEffector.setCoralIntakeVelocity(SetPointConstants.CORAL_SCORE_SPEED);
        this.leds.setCoralProcessing();
    }

    @Override
    public boolean isFinished () {

        return (this.endEffector.getCoralIntakePosition() - this.coralPosition) > SetPointConstants.CORAL_SCORE_DISTANCE;
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setCoralIntakeVelocity(0.0);
    }
}
