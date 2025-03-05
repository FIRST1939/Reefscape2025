package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class ManualEndEffector extends Command {
    
    private final EndEffector endEffector;
    private final DoubleSupplier coralIntakeVoltageSupplier;
    private final DoubleSupplier algaeIntakeVoltageSupplier;
    private final DoubleSupplier algaeWristVoltageSupplier;

    public ManualEndEffector (EndEffector endEffector, DoubleSupplier coralIntakeVoltageSupplier, DoubleSupplier algaeIntakeVoltageSupplier, DoubleSupplier algaeWristVoltageSupplier) {

        this.endEffector = endEffector;
        this.coralIntakeVoltageSupplier = coralIntakeVoltageSupplier;
        this.algaeIntakeVoltageSupplier = algaeIntakeVoltageSupplier;
        this.algaeWristVoltageSupplier = algaeWristVoltageSupplier;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void execute () {

        this.endEffector.runVoltage(
            this.coralIntakeVoltageSupplier.getAsDouble(),
            this.algaeIntakeVoltageSupplier.getAsDouble(),
            this.algaeWristVoltageSupplier.getAsDouble()
        );
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.runVoltage(0.0, 0.0, 0.0);
    }
}
