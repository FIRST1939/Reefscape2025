package frc.robot.subsystems.end_effector;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import com.revrobotics.spark.config.SparkFlexConfig;

public class EndEffectorIOVortex implements EndEffectorIO {

    protected final LoggedNetworkBoolean manual = new LoggedNetworkBoolean("Manual End Effector", false);

    protected final SparkFlex coralIntakeMotor = new SparkFlex(EndEffectorConstants.CORAL_INTAKE_CAN, MotorType.kBrushless);
    protected final SparkFlex algaeIntakeMotor = new SparkFlex(EndEffectorConstants.ALGAE_INTAKE_CAN, MotorType.kBrushless);
    protected final SparkFlex algaeWristMotor = new SparkFlex(EndEffectorConstants.ALGAE_WRIST_CAN, MotorType.kBrushless);

    private final SparkLimitSwitch coralBeambreak = coralIntakeMotor.getForwardLimitSwitch();

    public EndEffectorIOVortex () {

        SparkFlexConfig coralIntakeconfig = new SparkFlexConfig();
        SparkFlexConfig algaeIntakeconfig = new SparkFlexConfig();
        SparkFlexConfig algaeWristconfig = new SparkFlexConfig();

        coralIntakeconfig.limitSwitch.forwardLimitSwitchEnabled(false);

        coralIntakeconfig.encoder
            .positionConversionFactor(1.0 / EndEffectorConstants.CORAL_INTAKE_REDUCTION)
            .velocityConversionFactor(1.0 / EndEffectorConstants.CORAL_INTAKE_REDUCTION / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        algaeIntakeconfig.encoder
            .positionConversionFactor(1.0 / EndEffectorConstants.ALGAE_INTAKE_REDUCTION)
            .velocityConversionFactor(1.0 / EndEffectorConstants.ALGAE_INTAKE_REDUCTION / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        algaeWristconfig.encoder
            .positionConversionFactor(1.0 / EndEffectorConstants.ALGAE_WRIST_REDUCTION)
            .velocityConversionFactor(1.0 / EndEffectorConstants.ALGAE_WRIST_REDUCTION / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        coralIntakeMotor.configure(coralIntakeconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        algaeIntakeMotor.configure(algaeIntakeconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        algaeWristMotor.configure(algaeWristconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    
    @Override
    public void updateInputs (EndEffectorIOInputs inputs) {}
}
