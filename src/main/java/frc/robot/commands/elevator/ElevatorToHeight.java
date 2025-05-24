package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.leds.LEDs;

public class ElevatorToHeight extends Command {

    private final Elevator elevator;
    private final LEDs leds;

    private final double setpoint;

    public ElevatorToHeight (Elevator elevator, LEDs leds, double setpoint) {

        this.elevator = elevator;
        this.leds = leds;

        this.setpoint = setpoint;
        this.addRequirements(this.elevator, this.leds);
    }

    @Override
    public void initialize () {

        this.elevator.setGoal(this.setpoint);
        this.leds.setElevatorProgress(() -> this.elevator.getHeight(), this.setpoint);
    }

    @Override
    public boolean isFinished () {

        return this.elevator.atGoal();
    }
}
