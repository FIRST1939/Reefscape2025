package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;


import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.end_effector.EndEffectorConstants;


public class ElevatorIOVortex implements ElevatorIO {
    
    private final SparkFlex elevatorMotorLeader = new SparkFlex(ElevatorConstants.leaderCAN, MotorType.kBrushless);
    private final SparkFlex elevatorMotorFollower = new SparkFlex(ElevatorConstants.followerCAN, MotorType.kBrushless);
    private final LaserCan LaserCanSensor = new LaserCan(ElevatorConstants.elevatorLaserCAN);

    public ElevatorIOVortex () {

        SparkFlexConfig config = new SparkFlexConfig();
        SparkFlexConfig followerConfig = new SparkFlexConfig();


        config.idleMode(IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.currentLimit).voltageCompensation(12.0);
        config.inverted(false);
        followerConfig.follow(ElevatorConstants.leaderCAN);
        elevatorMotorLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        

        try {

            LaserCanSensor.setRangingMode(LaserCan.RangingMode.SHORT);
            LaserCanSensor.setRegionOfInterest(new LaserCan.RegionOfInterest(8, 8, 16, 16));
            LaserCanSensor.setTimingBudget(LaserCan.TimingBudget.TIMING_BUDGET_33MS);
        } catch (ConfigurationFailedException error) {

            System.out.println("LaserCAN configuration failed! " + error);
        }
    }

    //@Override
    public void updateInputs(ElevatorIOInputs inputs) {

        inputs.leaderVoltage = elevatorMotorLeader.getAppliedOutput() * elevatorMotorLeader.getBusVoltage();
        inputs.leaderCurrent = elevatorMotorLeader.getOutputCurrent();

        inputs.followerVoltage = elevatorMotorFollower.getAppliedOutput() * elevatorMotorFollower.getBusVoltage();
        inputs.followerCurrent = elevatorMotorFollower.getOutputCurrent();

        LaserCan.Measurement measurement = LaserCanSensor.getMeasurement();

        if (measurement != null && measurement.status == LaserCanInterface.LASERCAN_STATUS_VALID_MEASUREMENT) {

            inputs.elevatorlaserDistance = measurement.distance_mm;
        } else {

            inputs.elevatorlaserDistance = -1.0;
        }
    }


    

    @Override
    public void move (double volts) {

    }

    @Override
    public void setLeaderVoltage(double leaderVolts) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setLeaderVoltage'");
    }

    @Override
    public void setFollowerVoltage(double followerVolts) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFollowerVoltage'");
    }

    @Override
    public void updateInputs(ElevatorIOInputsAutoLogged inputs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInputs'");
    }
}
