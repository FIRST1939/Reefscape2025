package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;


public class ElevatorMove extends Command{
    private Elevator elevator;
    private DoubleSupplier elevatorManualUpSpeed;

    public ElevatorMove(DoubleSupplier elevatormanualupspeed, Elevator elevator) {
        //TODO Auto-generated constructor stub
        //elevator.move(elevatorManualUpSpeed)
        this.elevator=elevator;
        this.addRequirements(elevator);
    }

    @Override
public void initialize () 
{
    elevator.runVoltage(elevatorManualUpSpeed.getAsDouble());
}
}
