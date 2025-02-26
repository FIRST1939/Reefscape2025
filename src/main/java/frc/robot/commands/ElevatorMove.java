package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;


public class ElevatorMove extends Command{
   
    private Elevator elevator;
   
    private double elevatorManualUpSpeed;

    public ElevatorMove(double elevatormanualupspeed) {
        
        this.addRequirements(this.elevator);
    }

    @Override
    public void execute () 
{
    elevator.runVoltage(elevatorManualUpSpeed);
}
}
