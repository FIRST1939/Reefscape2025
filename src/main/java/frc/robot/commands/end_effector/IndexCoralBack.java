package frc.robot.commands.end_effector;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.util.SetPointConstants;

public class IndexCoralBack extends Command {
    
    private final EndEffector endEffector;
    private double coralIntakePosition;

    public IndexCoralBack (EndEffector endEffector) {

        this.endEffector = endEffector;
        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.coralIntakePosition = this.endEffector.getCoralIntakePosition();
        this.endEffector.setCoralIntakeVelocity(SetPointConstants.CORAL_BACK_SPEED);
    }

    @Override
    public boolean isFinished () {
        
        return (this.coralIntakePosition - this.endEffector.getCoralIntakePosition()) > SetPointConstants.CORAL_BACK_DISTANCE;
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setCoralIntakeVelocity(0.0);
    }
}
