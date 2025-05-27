package frc.robot.subsystems.end_effector;


import org.littletonrobotics.junction.AutoLog;

public interface EndEffectorIO {
    
    @AutoLog
    public static class EndEffectorIOInputs {

        public boolean manual = false;

        public double coralIntakePosition = 0.0;
        public double coralIntakeVelocity = 0.0;
        public double coralIntakeVoltage = 0.0;
        public double coralIntakeCurrent = 0.0;
        public double coralIntakeTemperature = 0.0;

        public double algaeIntakePosition = 0.0;
        public double algaeIntakeVelocity = 0.0;
        public double algaeIntakeVoltage = 0.0;
        public double algaeIntakeCurrent = 0.0;
        public double algaeIntakeTemperature = 0.0;

        public double algaeWristPosition = 0.0;
        public double algaeWristVelocity = 0.0;
        public double algaeWristVoltage = 0.0;
        public double algaeWristCurrent = 0.0;
        public double algaeWristTemperature = 0.0;

        public boolean coralBeambreak = true;
    }

    public default void updateInputs (EndEffectorIOInputs inputs) {}
}
