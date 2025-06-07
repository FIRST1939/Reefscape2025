package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import au.grapplerobotics.interfaces.LaserCanInterface.RegionOfInterest;
import frc.robot.util.LaserCanWrapper;

public class ElevatorIOVortex implements ElevatorIO {
    
    private final LoggedNetworkBoolean manual = new LoggedNetworkBoolean("Manual Elevator", false);

    protected final SparkFlex leadMotor = new SparkFlex(ElevatorConstants.LEADER_CAN, MotorType.kBrushless);
    private final SparkFlex followerMotor = new SparkFlex(ElevatorConstants.FOLLOWER_CAN, MotorType.kBrushless);
    private final RelativeEncoder leadEncoder = leadMotor.getEncoder();
    private final RelativeEncoder followerEncoder = followerMotor.getEncoder();
    
    protected final LaserCanWrapper laserCan = new LaserCanWrapper(ElevatorConstants.LASER_CAN);

    public ElevatorIOVortex () {
        
        SparkFlexConfig globalConfig = new SparkFlexConfig();
        SparkFlexConfig leadConfig = new SparkFlexConfig();
        SparkFlexConfig followerConfig = new SparkFlexConfig();

        globalConfig.encoder
            .positionConversionFactor(0.18 / ElevatorConstants.GEAR_RATIO)
            .velocityConversionFactor(0.18 / ElevatorConstants.GEAR_RATIO / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        leadConfig
            .apply(globalConfig)
            .inverted(ElevatorConstants.GEARBOX_INVERTED);

        followerConfig
            .apply(globalConfig)
            .follow(leadMotor, true);

        this.leadMotor.configure(leadConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        this.followerMotor.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        try {

            this.laserCan.setRangingMode(LaserCan.RangingMode.LONG);
            this.laserCan.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_100MS);
            this.laserCan.setRegionOfInterest(new RegionOfInterest(8, 8, 4, 4));
        } catch (ConfigurationFailedException error) {

            System.out.println("LaserCAN configuration failed! " + error);
        }

    }

    @Override
    public void updateInputs (ElevatorIOInputs inputs) {


        Measurement laserCanMeasurement = this.laserCan.getMeasurement();
        if (laserCanMeasurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT && laserCanMeasurement.distance_mm <= 100) {
            System.out.println("The target is " + laserCanMeasurement.distance_mm + "mm away!");
        } else {System.out.println("Oh no! The target is out of range, or we can't get a reliable measurement!");

        }


        inputs.manual = this.manual.get();

        inputs.leadMotorPosition = leadEncoder.getPosition();
        inputs.leadMotorVelocity = leadEncoder.getVelocity();
        inputs.leadMotorVoltage = leadMotor.getAppliedOutput() * leadMotor.getBusVoltage();
        inputs.leadMotorCurrent = leadMotor.getOutputCurrent();
        inputs.leadMotorTemperature = leadMotor.getMotorTemperature();

        inputs.followerMotorPosition = followerEncoder.getPosition();
        inputs.followerMotorVelocity = followerEncoder.getVelocity();
        inputs.followerMotorVoltage = followerMotor.getAppliedOutput() * followerMotor.getBusVoltage();
        inputs.followerMotorCurrent = followerMotor.getOutputCurrent();
        inputs.followerMotorTemperature = followerMotor.getMotorTemperature();

    }

}
