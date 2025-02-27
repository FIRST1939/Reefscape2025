package frc.robot.commands;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;


public class ElevatorMove extends Command{
   
    private final Elevator elevator;
   
    public final DoubleSupplier elevatorManualUpSpeed;

    public ElevatorMove(Elevator elevator, DoubleSupplier elevatorManualUpSpeed) {

        this.elevator = elevator;
        this.elevatorManualUpSpeed = elevatorManualUpSpeed;
        this.addRequirements(this.elevator);
    }

    @Override
    public void execute () 
{
    elevator.runVoltage(this.elevatorManualUpSpeed.getAsDouble());
}
}
