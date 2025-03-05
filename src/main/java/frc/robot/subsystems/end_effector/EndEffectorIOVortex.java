package frc.robot.subsystems.end_effector;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

public class EndEffectorIOVortex implements EndEffectorIO {

    private final SparkFlex coralIntake = new SparkFlex(EndEffectorConstants.coralIntakeCAN, MotorType.kBrushless);
    private final RelativeEncoder coralIntakeEncoder = coralIntake.getEncoder();

    private final SparkFlex algaeIntake = new SparkFlex(EndEffectorConstants.algaeIntakeCAN, MotorType.kBrushless);
    private final RelativeEncoder algaeIntakeEncoder = algaeIntake.getEncoder();

    private final SparkFlex algaeWrist = new SparkFlex(EndEffectorConstants.algaeWristCAN, MotorType.kBrushless);
    private final RelativeEncoder algaeWristEncoder = algaeWrist.getEncoder();

    private final SparkLimitSwitch coralBeambreak = coralIntake.getForwardLimitSwitch();

    public EndEffectorIOVortex () {

        SparkFlexConfig coralIntakeconfig = new SparkFlexConfig();
        SparkFlexConfig algaeIntakeconfig = new SparkFlexConfig();
        SparkFlexConfig algaeWristconfig = new SparkFlexConfig();

        coralIntakeconfig.idleMode(IdleMode.kBrake).inverted(EndEffectorConstants.coralIntakeInverted).smartCurrentLimit(EndEffectorConstants.coralIntakeCurrentLimit).voltageCompensation(12.0);
        algaeIntakeconfig.idleMode(IdleMode.kBrake).smartCurrentLimit(EndEffectorConstants.algaeIntakeCurrentLimit).voltageCompensation(12.0);
        algaeWristconfig.idleMode(IdleMode.kBrake).smartCurrentLimit(EndEffectorConstants.algaeWristCurrentLimit).voltageCompensation(12.0);

        coralIntakeconfig.limitSwitch.forwardLimitSwitchEnabled(false);

        coralIntakeconfig.encoder
            .positionConversionFactor(2.0 * Math.PI)
            .velocityConversionFactor((2.0 * Math.PI) / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        algaeIntakeconfig.encoder
            .positionConversionFactor(2.0 * Math.PI)
            .velocityConversionFactor((2.0 * Math.PI) / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        algaeWristconfig.encoder
            .positionConversionFactor(2.0 * Math.PI)
            .velocityConversionFactor((2.0 * Math.PI) / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        coralIntake.configure(coralIntakeconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        algaeIntake.configure(algaeIntakeconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        algaeWrist.configure(algaeWristconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
        
    @Override
    public void updateInputs (EndEffectorIOInputs inputs) {

        inputs.coralIntakePosition = coralIntakeEncoder.getPosition();
        inputs.coralIntakeVelocity = coralIntakeEncoder.getVelocity();
        inputs.coralIntakeVoltage = coralIntake.getAppliedOutput() * coralIntake.getBusVoltage();
        inputs.coralIntakeCurrent = coralIntake.getOutputCurrent();

        inputs.algaeIntakePosition = algaeIntakeEncoder.getPosition();
        inputs.algaeIntakeVelocity = algaeIntakeEncoder.getVelocity();
        inputs.algaeIntakeVoltage = algaeIntake.getAppliedOutput() * algaeIntake.getBusVoltage();
        inputs.algaeIntakeCurrent = algaeIntake.getOutputCurrent();
        
        inputs.algaeWristPosition = algaeWristEncoder.getPosition();
        inputs.algaeWristVelocity = algaeWristEncoder.getVelocity();
        inputs.algaeWristVoltage = algaeWrist.getAppliedOutput() * algaeWrist.getBusVoltage();
        inputs.algaeWristCurrent = algaeWrist.getOutputCurrent();

        inputs.coralBeambreak = !coralBeambreak.isPressed();
    }


    @Override
    public void setCoralIntakeVoltage (double volts) {

        coralIntake.setVoltage(volts);
    }
    
    @Override
    public void setAlgaeIntakeVoltage (double volts) {

        algaeIntake.setVoltage(volts);
     }

    @Override
    public void setAlgaeWristVoltage (double volts) {

        algaeWrist.setVoltage(volts);
    }
}
