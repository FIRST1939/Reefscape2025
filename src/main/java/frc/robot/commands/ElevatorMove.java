package frc.robot.commands;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;


public class ElevatorMove extends Command{
   
    private final Elevator elevator;
    public final DoubleSupplier voltageInput;

    public ElevatorMove(Elevator elevator, DoubleSupplier voltageInput) {

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
