package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class ManualEndEffector extends Command {
    
    private final EndEffector endEffector;
    private final DoubleSupplier coralIntakeVelocitySupplier;
    private final DoubleSupplier algaeIntakeVoltageSupplier;
    private final DoubleSupplier algaeWristVoltageSupplier;

    public ManualEndEffector (EndEffector endEffector, DoubleSupplier coralIntakeVelocitySupplier, DoubleSupplier algaeIntakeVoltageSupplier, DoubleSupplier algaeWristVoltageSupplier) {

        this.endEffector = endEffector;
        this.coralIntakeVelocitySupplier = coralIntakeVelocitySupplier;
        this.algaeIntakeVoltageSupplier = algaeIntakeVoltageSupplier;
        this.algaeWristVoltageSupplier = algaeWristVoltageSupplier;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void execute () {

        this.endEffector.setCoralIntakeVelocity(this.coralIntakeVelocitySupplier.getAsDouble());
        this.endEffector.setAlgaeIntakeVoltage(this.algaeIntakeVoltageSupplier.getAsDouble());
        this.endEffector.setAlgaeWristVoltage(this.algaeWristVoltageSupplier.getAsDouble());
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setCoralIntakeVelocity(0.0);
        this.endEffector.setAlgaeIntakeVoltage(0.0);
        this.endEffector.setAlgaeWristVoltage(0.0);
    }
}
