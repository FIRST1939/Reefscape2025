package frc.robot.subsystems.elevator;

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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorIOVortex implements ElevatorIO {
    
    private final SparkFlex elevatorMotorLeader = new SparkFlex(ElevatorConstants.leaderCAN, MotorType.kBrushless);
    private final SparkFlex elevatorMotorFollower = new SparkFlex(ElevatorConstants.followerCAN, MotorType.kBrushless);
    private final LaserCan laserCAN = new LaserCan(ElevatorConstants.laserCAN);

    private final RelativeEncoder leadEncoder = elevatorMotorLeader.getEncoder();
    private final RelativeEncoder followerEncoder = elevatorMotorFollower.getEncoder();

    public ElevatorIOVortex () {
        
        SparkFlexConfig leaderConfig = new SparkFlexConfig();
        SparkFlexConfig followerConfig = new SparkFlexConfig();

        leaderConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.currentLimit).voltageCompensation(12.0);
        leaderConfig.inverted(ElevatorConstants.leaderReversed);

        leaderConfig.encoder
            .positionConversionFactor(0.045)
            .velocityConversionFactor(0.045 / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        elevatorMotorLeader.configure(leaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        followerConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.currentLimit).voltageCompensation(12.0);
        followerConfig.inverted(ElevatorConstants.followerReversed);

        followerConfig.encoder
            .positionConversionFactor(0.045)
            .velocityConversionFactor(0.045 / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);
        
        elevatorMotorFollower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        try {

            // TODO Elevator LaserCAN ROI
            laserCAN.setRangingMode(LaserCan.RangingMode.LONG);
            laserCAN.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
        } catch (ConfigurationFailedException error) {

            System.out.println("LaserCAN configuration failed! " + error);
        }
    }

    @Override
    public void updateInputs (ElevatorIOInputs inputs) {

        inputs.elevatorPosition = (this.leadEncoder.getPosition() - this.followerEncoder.getPosition()) / 2.0;
        inputs.elevatorVelocity = (this.leadEncoder.getVelocity() - this.followerEncoder.getVelocity()) / 2.0;

        inputs.leaderVoltage = elevatorMotorLeader.getAppliedOutput() * elevatorMotorLeader.getBusVoltage();
        inputs.leaderCurrent = elevatorMotorLeader.getOutputCurrent();

        inputs.followerVoltage = elevatorMotorFollower.getAppliedOutput() * elevatorMotorFollower.getBusVoltage();
        inputs.followerCurrent = elevatorMotorFollower.getOutputCurrent();
    }

    @Override
    public void move (double volts) {

        SmartDashboard.putNumber(
            "Elevator_Voltage", 
            ((elevatorMotorLeader.getAppliedOutput() * elevatorMotorLeader.getBusVoltage()) - (elevatorMotorFollower.getAppliedOutput() * elevatorMotorFollower.getBusVoltage())) / 2.0
        );

        elevatorMotorLeader.setVoltage(volts);
        elevatorMotorFollower.setVoltage(-volts);
    }
}
