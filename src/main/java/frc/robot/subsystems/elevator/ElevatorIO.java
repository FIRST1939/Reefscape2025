package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
    
    @AutoLog
    public static class ElevatorIOInputs {

        public boolean manual = false;

        public double motorPosition = 0.0;
        public double motorVelocity = 0.0;
        public double motorVoltage = 0.0;
        public double motorCurrent = 0.0;
        public double motorTemperature = 0.0;

        public int laserCANStatus = 2;
        public double laserCANDistance = 0.0;
    }

    public default void updateInputs (ElevatorIOInputs inputs) {}
    public default void run (double volts) {}
}
