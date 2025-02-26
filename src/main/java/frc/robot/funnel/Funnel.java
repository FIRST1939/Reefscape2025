package frc.robot.funnel;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.funnel.FunnelIO.FunnelIOInputs;

public class Funnel extends SubsystemBase { 

    private final FunnelIO io;
    private final FunnelIOInputs inputs = new FunnelIOInputsAutoLogged();

    public Funnel (FunnelIO io) {

        this.io = io;
    }

    @Override
    public void periodic () {

        io.updateInputs(inputs);
        Logger.processInputs("Funnel", inputs);
    }

    public void runVoltage (double volts) {

        io.setFunnelVoltage(volts);
    }
}
