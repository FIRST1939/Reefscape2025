package frc.robot.funnel2;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Funnel extends SubsystemBase { // Inherit SubsystemBase
    private final FunnelIO io;
    private final FunnelIOInputsAutoLogged inputs = new FunnelIOInputsAutoLogged();

    public Funnel(FunnelIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Funnel", inputs);
    }

    public void runVoltage(double funnelVolts) {
        io.setFunnelVoltage(funnelVolts); // Corrected method name
    }
}