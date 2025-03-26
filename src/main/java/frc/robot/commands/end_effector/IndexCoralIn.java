package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.util.SetPointConstants;

public class IndexCoralIn extends Command {
    
    private final EndEffector endEffector;
    private boolean isFinished;

    public IndexCoralIn (EndEffector endEffector) {

        this.endEffector = endEffector;
        this.addRequirements(this.endEffector);

        new Trigger(this.endEffector::getCoralIntakeBeambreak).onTrue(Commands.runOnce(() -> this.isFinished = true));
    }

    @Override
    public void initialize () {

        this.isFinished = false;
        this.endEffector.setCoralIntakeVelocity(SetPointConstants.CORAL_IN_SPEED);
    }

    @Override
    public boolean isFinished () {

        return this.isFinished;
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setCoralIntakeVelocity(0.0);
    }
}
