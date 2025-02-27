package frc.robot.subsystems.elevator;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    public Elevator (ElevatorIO io) {

        this.io = io;
    }

    @Override
    public void periodic () {

        Logger.processInputs("Elevator", inputs);
    }

    public void runVoltage (double volts) {

        io.move(volts);
    }
}
