package frc.ro
.subsystems;

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

        io.updateInputs(inputs);
        Logger.processInputs("Elevator", inputs);
    }

    public void runVoltage (double leaderVolts, double followerVolts) {

        io.setLeaderVoltage(leaderVolts);
        io.setFollowerVoltage(followerVolts);
    }
}
