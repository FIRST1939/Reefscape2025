package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class ElevatorIOVortex implements ElevatorIO {
    
    private final SparkFlex elevatorMotorLeader = new SparkFlex(ElevatorConstants.leaderCAN, MotorType.kBrushless);
    private final SparkFlex elevatorMotorFollower = new SparkFlex(ElevatorConstants.followerCAN, MotorType.kBrushless);

    public ElevatorIOVortex () {

        SparkFlexConfig config = new SparkFlexConfig();

        config.idleMode(IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.currentLimit).voltageCompensation(12.0);
        config.setReversed(false)
        elevatorMotorLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_elevatorMotorFollower.follow(m_elevatorMotorLeader);
        }

    @Override
    public void updateInputs (ElevatorIOInputs inputs) {

        inputs.leaderVoltage = elevatorMotorLeader.getAppliedOutput() * elevatorMotorLeader.getBusVoltage();
        inputs.leaderCurrent = elevatorMotorLeader.getOutputCurrent();

        inputs.followerVoltage = elevatorMotorFollower.getAppliedOutput() * elevatorMotorFollower.getBusVoltage();
        inputs.followerCurrent = elevatorMotorFollower.getOutputCurrent();
    }

    @Override
    public void move (double volts) {

        elevatorMotorLeader.setVoltage(volts);
        elevatorMotorFollower.setVoltage(-volts);
    }
}
