package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
    
    @AutoLog
    public static class ElevatorIOInputs {

        public boolean manual = false;

        public double leadMotorPosition = 0.0;
        public double leadMotorVelocity = 0.0;
        public double leadMotorVoltage = 0.0;
        public double leadMotorCurrent = 0.0;
        public double leadMotorTemperature = 0.0;

        public double followerMotorPosition = 0.0;
        public double followerMotorVelocity = 0.0;
        public double followerMotorVoltage = 0.0;
        public double followerMotorCurrent = 0.0;
        public double followerMotorTemperature = 0.0;

        public int laserCANStatus = 2;
        public double laserCANDistance = 0.0;
    }

    public default void updateInputs (ElevatorIOInputs inputs) {}
}
