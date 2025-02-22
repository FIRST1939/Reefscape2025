package frc.robot.subsystems;

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
        public double elevatorlaserDistance = 0.0;

    }

  
    public  void move(double volts);


    public void setLeaderVoltage(double leaderVolts);


    public void setFollowerVoltage(double followerVolts);


    public void updateInputs(ElevatorIOInputsAutoLogged inputs);
}
