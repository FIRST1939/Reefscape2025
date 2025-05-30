package frc.robot.subsystems.funnel;

import org.littletonrobotics.junction.AutoLog;

public interface FunnelIO {

    @AutoLog
    public static class FunnelIOInputs {

        public boolean manual = false;

        public double funnelPosition = 0.0;
        public double funnelVelocity = 0.0;
        public double funnelVoltage = 0.0;
        public double funnelCurrent = 0.0;
    }

    public default void updateInputs (FunnelIOInputsAutoLogged inputs) {}
    public default void setMotorVoltage (double volts) {}

}
