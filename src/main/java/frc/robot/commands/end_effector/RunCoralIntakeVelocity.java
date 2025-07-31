package frc.robot.commands.end_effector;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class SetCoralIntakeVelocity extends Command {
    private final EndEffector endEffector;
    private final double targetVelocity;

    public SetCoralIntakeVelocity(EndEffector endEffector, double coralIntakeVelocity) {

        this.endEffector = endEffector;
        this.targetVelocity = coralIntakeVelocity;

        addRequirements(endEffector);
    }

    @Override
    public void initialize() {
        Logger.recordOutput("CoralIntake Running", true);
        this.endEffector.setCoralIntakeVelocity(this.targetVelocity);
    }

    @Override
    public void end(boolean interrupted) {
        Logger.recordOutput("CoralIntake Running", false);
        this.endEffector.setCoralIntakeVelocity(0);
    }
}
