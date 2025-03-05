package frc.robot.subsystems.funnel;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;

public class FunnelIOVortex implements FunnelIO {
    
    private final LoggedNetworkBoolean manual = new LoggedNetworkBoolean("Manual Funnel", false);
    private final LoggedNetworkBoolean beambreakOverride = new LoggedNetworkBoolean("Funnel Beambreak", true);

    private final SparkFlex funnel = new SparkFlex(FunnelConstants.funnelCAN, MotorType.kBrushless);
    private final RelativeEncoder funnelEncoder = funnel.getEncoder();

    private final Debouncer coralStuckFilter = new Debouncer(0.3, DebounceType.kBoth);
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

        inputs.manual = this.manual.get();

        inputs.funnelPosition = this.funnelEncoder.getPosition();
        inputs.funnelVelocity = this.funnelEncoder.getVelocity();
        inputs.funnelVoltage = this.funnel.getAppliedOutput() * this.funnel.getBusVoltage();
        inputs.funnelCurrent = this.funnel.getOutputCurrent();

        inputs.coralStuck = this.coralStuckFilter.calculate(inputs.funnelCurrent > 40.0 && Math.abs(inputs.funnelVelocity) < 5.0);
        //inputs.funnelBeambreak = !this.funnelBeambreak.isPressed();
        inputs.funnelBeambreak = this.beambreakOverride.get();
    }

    @Override
    public void runVoltage (double volts) {
       
        this.funnel.setVoltage(volts);
    }
}
