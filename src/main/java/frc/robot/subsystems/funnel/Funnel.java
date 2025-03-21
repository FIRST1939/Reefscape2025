package frc.robot.subsystems.funnel;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Funnel extends SubsystemBase { 

    private final FunnelIO io;
    private final FunnelIOInputsAutoLogged inputs = new FunnelIOInputsAutoLogged();

    public Funnel (FunnelIO io) {

        this.io = io;
    }

    @Override
    public void periodic () {

        this.io.updateInputs(this.inputs);
        Logger.processInputs("Funnel", this.inputs);
    }

    public boolean isManual () {

        return this.inputs.manual;
    }

    public void runVoltage (double volts) {

        io.runVoltage(volts);
    }
}
