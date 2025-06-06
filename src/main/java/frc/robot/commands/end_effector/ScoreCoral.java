package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.leds.LEDs;
import frc.robot.util.SetPointConstants;

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
        this.leds.setAlliancePattern();

        SmartDashboard.putBoolean("Reef Aligned", false);
    }
}
