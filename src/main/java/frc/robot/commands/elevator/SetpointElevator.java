package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;

public class SetpointElevator extends Command {

    private final Elevator elevator;
    private final double setpoint;

    public SetpointElevator (Elevator elevator, double setpoint) {

        this.elevator = elevator;
        this.setpoint = setpoint;

        this.addRequirements(this.elevator);
    }

    @Override
    public void initialize () {

        this.elevator.setGoal(this.setpoint);
    }

    @Override
    public boolean isFinished () {

        return this.elevator.atGoal();
    }
}
