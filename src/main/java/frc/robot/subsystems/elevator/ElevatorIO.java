package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
    
    @AutoLog
    public static class ElevatorIOInputs {

        public boolean manual = false;

        public double leaderMotorPosition = 0.0;
        public double leaderMotorVelocity = 0.0;
        public double leaderMotorVoltage = 0.0;
        public double leaderMotorCurrent = 0.0;
        public double leaderMotorTemperature = 0.0;

        public double followerMotorPosition = 0.0;
        public double followerMotorVelocity = 0.0;
        public double follwerMotorVoltage = 0.0;
        public double followerMotorCurrent = 0.0;
        public double followerMotorTemperature = 0.0;

        public int laserCANStatus = 2;
        public double laserCANDistance = 0.0;
    }

    public default void updateInputs (ElevatorIOInputs inputs) {}
}
