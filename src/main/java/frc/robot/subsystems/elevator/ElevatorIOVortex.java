package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import edu.wpi.first.wpilibj.LEDPattern;
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
    }

    @Override
    public void updateInputs (ElevatorIOInputs inputs) {

        inputs.manual = false;

        inputs.leaderMotorPosition = leadEncoder.getPosition();
        inputs.leaderMotorVelocity = leadEncoder.getVelocity();
        inputs.leaderMotorVoltage = leadMotor.getAppliedOutput() * leadMotor.getBusVoltage();
        inputs.leaderMotorCurrent = leadMotor.getOutputCurrent();
        inputs.leaderMotorTemperature = leadMotor.getMotorTemperature();

        inputs.followerMotorPosition = followerEncoder.getPosition();
        inputs.followerMotorVelocity = followerEncoder.getVelocity();
        inputs.follwerMotorVoltage = followerMotor.getAppliedOutput() * followerMotor.getBusVoltage();
        inputs.followerMotorCurrent = followerMotor.getOutputCurrent();
        inputs.followerMotorTemperature = followerMotor.getMotorTemperature();

    }

}
