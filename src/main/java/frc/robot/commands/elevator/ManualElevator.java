package frc.robot.commands.elevator;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;

public class ManualElevator extends Command{
   
    private final Elevator elevator;
    public final DoubleSupplier voltageInput;

    public ManualElevator(Elevator elevator, DoubleSupplier voltageInput) {

        this.elevator = elevator;
        this.voltageInput = voltageInput;

        this.addRequirements(this.elevator);
    }

    @Override
    public void execute () {

        elevator.runVoltage(this.voltageInput.getAsDouble());
    }

    @Override
    public void end (boolean interrupted) {

        elevator.runVoltage(0.0);
    }
}
