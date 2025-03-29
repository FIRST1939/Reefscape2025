package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import au.grapplerobotics.interfaces.LaserCanInterface.RegionOfInterest;

public class ElevatorIOVortex implements ElevatorIO {
    
    private final LoggedNetworkBoolean manual = new LoggedNetworkBoolean("Manual Elevator", false);
    private double positionOffset;

    private final SparkFlex leadMotor = new SparkFlex(ElevatorConstants.leaderCAN, MotorType.kBrushless);
    private final SparkFlex followerMotor = new SparkFlex(ElevatorConstants.followerCAN, MotorType.kBrushless);

    private final LaserCan laserCAN = new LaserCan(ElevatorConstants.laserCAN);
    private final RelativeEncoder leadEncoder = leadMotor.getEncoder();
    private final RelativeEncoder followerEncoder = followerMotor.getEncoder();

    public ElevatorIOVortex () {
        
        SparkFlexConfig leadConfig = new SparkFlexConfig();
        SparkFlexConfig followerConfig = new SparkFlexConfig();

        leadConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.currentLimit).voltageCompensation(12.0);
        leadConfig.inverted(ElevatorConstants.leaderReversed);

        leadConfig.encoder
            .positionConversionFactor(0.045)
            .velocityConversionFactor(0.045 / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        this.leadMotor.configure(leadConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        followerConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.currentLimit).voltageCompensation(12.0);
        followerConfig.inverted(ElevatorConstants.followerReversed);

        followerConfig.encoder
            .positionConversionFactor(0.045)
            .velocityConversionFactor(0.045 / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);
        
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
    public void updateInputs (ElevatorIOInputs inputs) {

        Measurement laserCANMeasurement = this.laserCAN.getMeasurement();
        
        if (laserCANMeasurement.status == LaserCanInterface.LASERCAN_STATUS_VALID_MEASUREMENT) {

            this.positionOffset = (laserCANMeasurement.distance_mm / 1000.0) - ((this.leadEncoder.getPosition() - this.followerEncoder.getPosition()) / 2.0);
        }

        inputs.manual = this.manual.get();

        inputs.elevatorPosition = ((this.leadEncoder.getPosition() - this.followerEncoder.getPosition()) / 2.0) + positionOffset;
        inputs.elevatorVelocity = (this.leadEncoder.getVelocity() - this.followerEncoder.getVelocity()) / 2.0;

        inputs.leaderVoltage = this.leadMotor.getAppliedOutput() * this.leadMotor.getBusVoltage();
        inputs.leaderCurrent = this.leadMotor.getOutputCurrent();

        inputs.followerVoltage = this.followerMotor.getAppliedOutput() * this.followerMotor.getBusVoltage();
        inputs.followerCurrent = this.followerMotor.getOutputCurrent();
    }

    @Override
    public void move (double volts) {

        this.leadMotor.setVoltage(volts);
        this.followerMotor.setVoltage(-volts);
    }
}
