package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class elevatorIOVortex implements ElevatorIO {
    
    private final SparkFlex elevatorMotorLeader = new SparkFlex(elevatorConstants.elevatorMotorLeaderID, MotorType.kBrushless);
    private final RelativeEncoder elevatorEncoderLeader = elevatorMotorLeader.getEncoder();

    private final SparkFlex elevatorMotorFollower = new SparkFlex(ExampleConstants.bottomCAN, MotorType.kBrushless);
    private final RelativeEncoder elevatorEncoderFollower = elevatorMotorFollower.getEncoder();

    public ExampleIOVortex () {

        SparkFlexConfig config = new SparkFlexConfig();

        config.idleMode(IdleMode.kBrake).smartCurrentLimit(ExampleConstants.currentLimit).voltageCompensation(12.0);

        // config.encoder
        //     .positionConversionFactor(2.0 * Math.PI)
        //     .velocityConversionFactor((2.0 * Math.PI) / 60.0)
        //     .uvwMeasurementPeriod(10)
        //     .uvwAverageDepth(2);
                   elevatorMotorLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
         elevatorMotorFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void updateInputs (ExampleIOInputs inputs) {

        inputs.topPosition = elevatorEncoderLeader.getPosition();
        inputs.topVelocity = elevatorEncoderLeader.getVelocity();
        inputs.topVoltage = elevatorMotorLeader.getAppliedOutput() * elevatorMotorLeader.getBusVoltage();
        inputs.topCurrent = elevatorMotorLeader.getOutputCurrent();

        inputs.bottomPosition = elevatorEncoderFollower.getPosition();
        inputs.bottomVelocity = elevatorEncoderFollower.getVelocity();
        inputs.bottomVoltage = elevatorMotorFollower.getAppliedOutput() * elevatorMotorFollower.getBusVoltage();
        inputs.bottomCurrent = elevatorMotorFollower.getOutputCurrent();
    }

    @Override
    public void move (double volts) {

        elevatorMotorLeader.setVoltage(volts);
        elevatorMotorFollower.setVoltage(-volts);
    }

}
