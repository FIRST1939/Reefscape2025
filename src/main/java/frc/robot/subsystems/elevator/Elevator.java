package frc.robot.subsystems.elevator;

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

        this.io.updateInputs(this.inputs);
        Logger.processInputs("Elevator", this.inputs);
    }

    public double getHeight () {

        return this.inputs.motorPosition;
    }

    public void run (double volts) {

        this.io.run(volts);
    }
}
