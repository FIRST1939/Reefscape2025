package frc.robot.Funnel;

import org.littletonrobotics.junction.AutoLog;

public interface FunnelIO { // Changed to interface
    @AutoLog
    public static class FunnelIOInputs {
        public double funnelPosition = 0.0;
        public double funnelVelocity = 0.0;
        public double funnelVoltage = 0.0;
        public double funnelCurrent = 0.0;
        public boolean funnelBeambreak = false;
    }

    void updateInputs(FunnelIOInputs inputs); // No "default"
    void setFunnelVoltage(double volts); // Corrected method name
}
    public class FunnelIOInputsAutoLogged {
    public class FunnelIOInputsAutoLogged {
    }
    public interface FunnelIOInputsAutoLogged {
    }
    }