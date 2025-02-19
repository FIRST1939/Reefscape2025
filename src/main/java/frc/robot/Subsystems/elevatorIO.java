package frc.robot;

import org.littletonrobotics.junction.AutoLog;

public interface elevatorIO {
    
    @AutoLog
    public static class elevatorIOInputs {

        public double topPosition = 0.0;
        public double topVelocity = 0.0;
        public double topVoltage = 0.0;
        public double topCurrent = 0.0;

        public double bottomPosition = 0.0;
        public double bottomVelocity = 0.0;
        public double bottomVoltage = 0.0;
        public double bottomCurrent = 0.0;
    }

    public default void updateInputs (ElevatorIOInputs inputs) {}
    public default void setTopVoltage (double volts) {}
    public default void setBottomVoltage (double volts) {}
    public default void move(double speedpercent){}
}
