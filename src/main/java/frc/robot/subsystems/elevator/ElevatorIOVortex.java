package frc.robot.subsystems.elevator;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface;

public class ElevatorIOVortex implements ElevatorIO {
    
    private final SparkFlex elevatorMotorLeader = new SparkFlex(ElevatorConstants.leaderCAN, MotorType.kBrushless);
    private final SparkFlex elevatorMotorFollower = new SparkFlex(ElevatorConstants.followerCAN, MotorType.kBrushless);
    private final LaserCan laserCAN = new LaserCan(ElevatorConstants.laserCAN);

    public ElevatorIOVortex () {
        try {
        SparkFlexConfig leaderConfig = new SparkFlexConfig();
        SparkFlexConfig followerConfig = new SparkFlexConfig();

        leaderConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.currentLimit).voltageCompensation(12.0);
        leaderConfig.inverted(ElevatorConstants.leaderReversed);

        elevatorMotorLeader.configure(leaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        followerConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.currentLimit).voltageCompensation(12.0);
       // followerConfig.inverted(ElevatorConstants.followerReversed);
       // This doesn't work.. you have to put the follower reversed in the follow command
       
       followerConfig.follow(ElevatorConstants.leaderCAN,ElevatorConstants.followerReversed);
        elevatorMotorFollower.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      

            // TODO Elevator LaserCAN ROI
            laserCAN.setRangingMode(LaserCan.RangingMode.LONG);
            laserCAN.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
        } catch (ConfigurationFailedException error) {

            System.out.println("LaserCAN configuration failed! " + error);
        }
    }

    @Override
    public void updateInputs (ElevatorIOInputs inputs) {

        inputs.leaderVoltage = elevatorMotorLeader.getAppliedOutput() * elevatorMotorLeader.getBusVoltage();
        inputs.leaderCurrent = elevatorMotorLeader.getOutputCurrent();

        inputs.followerVoltage = elevatorMotorFollower.getAppliedOutput() * elevatorMotorFollower.getBusVoltage();
        inputs.followerCurrent = elevatorMotorFollower.getOutputCurrent();

        LaserCan.Measurement measurement = laserCAN.getMeasurement();

        if (measurement != null && measurement.status == LaserCanInterface.LASERCAN_STATUS_VALID_MEASUREMENT) {

            inputs.elevatorVelocity = (measurement.distance_mm - inputs.elevatorPosition) / 0.02;
            inputs.elevatorPosition = measurement.distance_mm;
        }
    }

    @Override
    public void move (double volts) {

        elevatorMotorLeader.setVoltage(volts);
       // elevatorMotorFollower.setVoltage(-volts);
    }
}
