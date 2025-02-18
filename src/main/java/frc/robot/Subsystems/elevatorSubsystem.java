package frc.robot;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class elevatorSubsystem extends SubsystemBase {
    
    private final elevatorIO io;
    private final elevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    public Elevator (elevatorIO io) {

        this.io = io;
    }

    @Override
    public void periodic () {

        io.updateInputs(inputs);
        Logger.processInputs("Elevator", inputs);
    }

    public void runVoltage (double topVolts, double bottomVolts) {

        io.setTopVoltage(topVolts);
        io.setBottomVoltage(bottomVolts);
    }
}
