package frc.robot.subsystems.funnel;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class FunnelIOVortex implements FunnelIO {
    
    private final SparkFlex funnel = new SparkFlex(FunnelConstants.funnelCAN, MotorType.kBrushless);
    private final RelativeEncoder funnelEncoder = funnel.getEncoder();
    private final SparkLimitSwitch funnelBeambreak = funnel.getForwardLimitSwitch();

    public FunnelIOVortex () {

        SparkFlexConfig config = new SparkFlexConfig();

        config
            .inverted(FunnelConstants.funnelInverted)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(FunnelConstants.currentLimit)
            .voltageCompensation(12.0);

        config.encoder
            .positionConversionFactor(2.0 * Math.PI)
            .velocityConversionFactor((2.0 * Math.PI) / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        this.funnel.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void updateInputs (FunnelIOInputsAutoLogged inputs) {

        inputs.funnelPosition = this.funnelEncoder.getPosition();
        inputs.funnelVelocity = this.funnelEncoder.getVelocity();
        inputs.funnelVoltage = this.funnel.getAppliedOutput() * this.funnel.getBusVoltage();
        inputs.funnelCurrent = this.funnel.getOutputCurrent();

        inputs.funnelBeambreak = !this.funnelBeambreak.isPressed();
    }

    @Override
    public void runVoltage (double volts) {
       
        this.funnel.setVoltage(volts);
    }
}
