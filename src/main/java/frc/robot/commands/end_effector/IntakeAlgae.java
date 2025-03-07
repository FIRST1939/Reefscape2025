package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class IntakeAlgae extends Command {
    
    private final EndEffector endEffector;
    private final double algaeIntakeVoltage;

    public IntakeAlgae (EndEffector endEffector, double algaeIntakeVoltage) {

        this.endEffector = endEffector;
        this.algaeIntakeVoltage = algaeIntakeVoltage;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeIntakeVoltage(this.algaeIntakeVoltage);
    }

    @Override
    public boolean isFinished () {

        return this.endEffector.getAlgaeIntakeCurrent() > 40.0 && this.endEffector.getAlgaeIntakeVelocity() < 1.0;
    }
}
