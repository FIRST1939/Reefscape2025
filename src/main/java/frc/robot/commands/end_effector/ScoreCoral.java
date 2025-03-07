package frc.robot.commands.end_effector;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class ScoreCoral extends Command {

    private final EndEffector endEffector;
    private final double coralScoreSpeed;

    private double coralPosition;
    
    public ScoreCoral(EndEffector endEffector, double coralScoreSpeed) {
        
        this.endEffector = endEffector;
        this.coralScoreSpeed = coralScoreSpeed;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.coralPosition = this.endEffector.getCoralIntakePosition();
        this.endEffector.setCoralIntakeVelocity(coralScoreSpeed);
    }

    @Override
    public boolean isFinished () {

        return (this.endEffector.getCoralIntakePosition() - this.coralPosition) > 25.0;
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setCoralIntakeVelocity(0.0);
    }
}
