package frc.robot.subsystems.funnel;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

public class FunnelIOVortex implements FunnelIO {
    
    protected final SparkFlex motor = new SparkFlex(FunnelConstants.FUNNEL_CAN, MotorType.kBrushless);

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
    public void updateInputs (FunnelIOInputsAutoLogged inputs) {}
}
