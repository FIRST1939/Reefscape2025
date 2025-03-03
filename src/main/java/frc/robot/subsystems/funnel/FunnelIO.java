package frc.robot.subsystems.funnel;

import org.littletonrobotics.junction.AutoLog;

import frc.robot.subsystems.funnel.FunnelIOInputsAutoLogged;

public interface FunnelIO {

    @AutoLog
    public static class FunnelIOInputs {

        // public double funnelPosition = 0.0;
        // public double funnelVelocity = 0.0;
        // public double funnelVoltage = 0.0;
        // public double funnelCurrent = 0.0;

        // public boolean funnelBeambreak = false;

    }

    public default void updateInputs (FunnelIOInputsAutoLogged inputs) {}
    public default void setFunnelVoltage (double volts) {}
}
