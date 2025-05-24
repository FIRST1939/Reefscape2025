package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.elevator.Elevator;

public class SetElevatorTarget extends InstantCommand {
    
    private final Elevator elevator;
    private final double target;

    public SetElevatorTarget (Elevator elevator, double target) {

        this.elevator = elevator;
        this.target = target;

        this.addRequirements(this.elevator);
    }

    @Override
    public void initialize () {

        this.elevator.setGoal(this.target);
    }
}
