package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;

public class ElevatorMoveToHeight extends Command {

    private final Elevator elevator;
    private final double height;

    public ElevatorMoveToHeight (Elevator elevator, double height) {

        this.elevator = elevator;
        this.height = height;

        this.addRequirements(this.elevator);
    }

    @Override
    public void initialize () {

        this.elevator.setPosition(this.height);
    }

    @Override
    public boolean isFinished () {

        return this.elevator.atHeight();
    }
}
