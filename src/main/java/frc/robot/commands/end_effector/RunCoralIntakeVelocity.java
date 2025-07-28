package frc.robot.commands.end_effector;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class RunCoralIntakeVelocity extends Command {
    private final EndEffector endEffector;
    private final double targetVelocity;

    public RunCoralIntakeVelocity(EndEffector endEffector, double velocity) {

        this.endEffector = endEffector;
        this.targetVelocity = velocity;

        addRequirements(endEffector);
    }

    @Override
    public void execute() {
        Logger.recordOutput("CoralIntake Running", true);
        endEffector.setCoralIntakeVelocity(this.targetVelocity);
    }

    @Override
    public void end(boolean interrupted) {
        Logger.recordOutput("CoralIntake Running", false);
        endEffector.setCoralIntakeVelocity(0);
    }
}
