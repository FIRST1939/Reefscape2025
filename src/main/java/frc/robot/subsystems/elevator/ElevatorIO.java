package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
    
    @AutoLog
    public static class ElevatorIOInputs {

        public double elevatorPosition = 0.0;
        public double elevatorVelocity = 0.0;
        
        public double leaderVoltage = 0.0;
        public double leaderCurrent = 0.0;
    
        public double followerVoltage = 0.0;
        public double followerCurrent = 0.0;
    }

    public default void updateInputs (ElevatorIOInputs inputs) {}
    public default void move (double volts) {}
}
