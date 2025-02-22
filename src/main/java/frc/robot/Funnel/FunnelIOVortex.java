package frc.robot.Funnel;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
// import com.revrobotics.CANSparkFlex; 

// import au.grapplerobotics.ConfigurationFailedException;
// import au.grapplerobotics.LaserCan;
// import au.grapplerobotics.interfaces.LaserCanInterface;
import edu.wpi.first.wpilibj.DigitalInput;


public class FunnelIOVortex implements FunnelIO {
    
    private final SparkFlex funnel = new SparkFlex(FunnelConstants.funnelCAN, MotorType.kBrushless);
    private final RelativeEncoder funnelEncoder = funnel.getEncoder();

    private final DigitalInput funnelBeambreak = new DigitalInput(FunnelConstants.funnelBeambreakDIO); 
    // private final LaserCan LaserCanSensor = new LaserCan(EndEffectorConstants.algaeIntakeLaserCAN);

    public FunnelIOVortex () {

        SparkFlexConfig config = new SparkFlexConfig();

        config.idleMode(IdleMode.kBrake).smartCurrentLimit(FunnelConstants.currentLimit).voltageCompensation(12.0);

        config.encoder
            .positionConversionFactor(2.0 * Math.PI)
            .velocityConversionFactor((2.0 * Math.PI) / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        funnel.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void updateInputs (FunnelIOInputs inputs) {

        inputs.funnelPosition = funnelEncoder.getPosition();
        inputs.funnelVelocity = funnelEncoder.getVelocity();
        inputs.funnelVoltage = funnel.getAppliedOutput() * funnel.getBusVoltage();
        inputs.funnelCurrent = funnel.getOutputCurrent();
        inputs.funnelBeambreak = funnelBeambreak.get();

    }

    @Override
        public void setFunnelVoltage(double volts) {
        funnel.setVoltage(volts);
    }
}