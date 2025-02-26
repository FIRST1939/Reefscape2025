package frc.robot.funnel;

import org.littletonrobotics.junction.AutoLog;

public interface FunnelIO {

    @AutoLog
    public static class FunnelIOInputs {

        public double funnelPosition = 0.0;
        public double funnelVelocity = 0.0;
        public double funnelVoltage = 0.0;
        public double funnelCurrent = 0.0;

        public boolean funnelBeambreak = false;

    }

    public default void updateInputs (FunnelIOInputs inputs) {}
    public default void setFunnelVoltage (double volts) {}
}
