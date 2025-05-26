package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.RegionOfInterest;

public class ElevatorIOVortex implements ElevatorIO {
    
    protected final LoggedNetworkBoolean manual = new LoggedNetworkBoolean("Manual Elevator", false);
    protected final SparkFlex leadMotor = new SparkFlex(ElevatorConstants.leaderCAN, MotorType.kBrushless);
    private final SparkFlex followerMotor = new SparkFlex(ElevatorConstants.followerCAN, MotorType.kBrushless);
    private final LaserCan laserCAN = new LaserCan(ElevatorConstants.laserCAN);

    public ElevatorIOVortex () {
        
        SparkFlexConfig globalConfig = new SparkFlexConfig();
        SparkFlexConfig leadConfig = new SparkFlexConfig();
        SparkFlexConfig followerConfig = new SparkFlexConfig();

        globalConfig
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(ElevatorConstants.currentLimit)
            .voltageCompensation(12.0);

        globalConfig.encoder
            .positionConversionFactor(0.045)
            .velocityConversionFactor(0.045 / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        leadConfig
            .apply(globalConfig)
            .inverted(ElevatorConstants.gearboxInverted);

        followerConfig
            .apply(globalConfig)
            .follow(leadMotor, true);

        this.leadMotor.configure(leadConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        this.followerMotor.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        try {

            this.laserCAN.setRangingMode(LaserCan.RangingMode.SHORT);
            this.laserCAN.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_100MS);
            this.laserCAN.setRegionOfInterest(new RegionOfInterest(8, 8, 4, 4));
        } catch (ConfigurationFailedException error) {

            System.out.println("LaserCAN configuration failed! " + error);
        }
    }

    @Override
    public void updateInputs (ElevatorIOInputs inputs) {}

    @Override
    public void run (double volts) {

        this.leadMotor.setVoltage(volts);
    }
}
