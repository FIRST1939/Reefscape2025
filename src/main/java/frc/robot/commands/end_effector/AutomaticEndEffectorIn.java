package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.end_effector.EndEffector;

public class AutomaticEndEffectorIn extends Command {
    
    private final EndEffector endEffector;
    private final double inVelocity;

    private boolean isFinished;

    public AutomaticEndEffectorIn (EndEffector endEffector, double inVelocity) {

        this.endEffector = endEffector;
        this.inVelocity = inVelocity;

        new Trigger(this.endEffector::getCoralIntakeBeambreak).onTrue(Commands.runOnce(() -> this.isFinished = true));
        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.isFinished = false;
        this.endEffector.setCoralIntakeVelocity(inVelocity);
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
