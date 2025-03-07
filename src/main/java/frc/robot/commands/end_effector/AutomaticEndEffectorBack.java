package frc.robot.commands.end_effector;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class AutomaticEndEffectorBack extends Command {
    
    private final EndEffector endEffector;

    private final double backVelocity;
    private double coralIntakePosition;

    public AutomaticEndEffectorBack (EndEffector endEffector, double backVelocity) {

        this.endEffector = endEffector;
        this.backVelocity = backVelocity;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.coralIntakePosition = this.endEffector.getCoralIntakePosition();
        this.endEffector.setCoralIntakeVelocity(backVelocity);
        
    }

    @Override
    public boolean isFinished () {
        
        return (this.coralIntakePosition - this.endEffector.getCoralIntakePosition()) > 4.0;
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setCoralIntakeVelocity(0.0);
    }
}
