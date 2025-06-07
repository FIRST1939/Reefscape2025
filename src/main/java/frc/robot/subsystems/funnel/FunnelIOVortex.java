package frc.robot.subsystems.funnel;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

public class FunnelIOVortex implements FunnelIO {

    private final LoggedNetworkBoolean manual = new LoggedNetworkBoolean("Manual Funnel", false);
    protected final SparkFlex motor = new SparkFlex(FunnelConstants.FUNNEL_CAN, MotorType.kBrushless);
    private final RelativeEncoder motorEncoder = motor.getEncoder();

    public FunnelIOVortex () {

        SparkFlexConfig config = new SparkFlexConfig();

        config.encoder
            .positionConversionFactor(1.0 / FunnelConstants.FUNNEL_REDUCTION)
            .velocityConversionFactor(1.0 / FunnelConstants.FUNNEL_REDUCTION / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        this.motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void updateInputs (FunnelIOInputsAutoLogged inputs) {

        inputs.manual = this.manual.get();

        inputs.funnelPosition = this.motorEncoder.getPosition();
        inputs.funnelVelocity = this.motorEncoder.getVelocity();
        inputs.funnelVoltage = this.motor.getAppliedOutput() * this.motor.getBusVoltage();
        inputs.funnelCurrent = this.motor.getOutputCurrent();

    }

    @Override
    public void setMotorVoltage (double volts) {

        this.motor.setVoltage(volts);
    }

}
