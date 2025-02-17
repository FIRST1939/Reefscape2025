package frc.robot.end_effector;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface;
import edu.wpi.first.wpilibj.DigitalInput;

public class EndEffectorIOVortex implements EndEffectorIO {
    
        private final SparkFlex coralIntake = new SparkFlex(EndEffectorConstants.coralIntakeCAN, MotorType.kBrushless);
        private final RelativeEncoder coralIntakeEncoder = coralIntake.getEncoder();
        private final SparkFlex algaeIntake = new SparkFlex(EndEffectorConstants.algaeIntakeCAN, MotorType.kBrushless);
        private final RelativeEncoder algaeIntakeEncoder = algaeIntake.getEncoder();
        private final SparkFlex algaeWrist = new SparkFlex(EndEffectorConstants.algaeWristCAN, MotorType.kBrushless);
        private final RelativeEncoder algaeWristEncoder = algaeWrist.getEncoder();
        private final DigitalInput coralIntakeBeamBreakDigitalInput = new DigitalInput(EndEffectorConstants.coralIntakeBeamBreakDIO); 
        private final static LaserCan LaserCanSensor = new LaserCan(EndEffectorConstants.AlgaeIntakeLaserCAN); 
    public EndEffectorIOVortex () {

        SparkFlexConfig config = new SparkFlexConfig();

        config.idleMode(IdleMode.kBrake).smartCurrentLimit(EndEffectorConstants.coralIntakecurrentLimit).voltageCompensation(12.0);
        config.idleMode(IdleMode.kBrake).smartCurrentLimit(EndEffectorConstants.algaeIntakecurrentLimit).voltageCompensation(12.0);
        config.idleMode(IdleMode.kBrake).smartCurrentLimit(EndEffectorConstants.algaeWristcurrentLimit).voltageCompensation(12.0);

        config.encoder
            .positionConversionFactor(2.0 * Math.PI)
            .velocityConversionFactor((2.0 * Math.PI) / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        coralIntake.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        algaeIntake.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        algaeWrist.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        LaserCan.Measurement measurement = LaserCanSensor.getMeasurement();
        if (measurement != null && measurement.status == LaserCanInterface.LASERCAN_STATUS_VALID_MEASUREMENT) {
            EndEffectorConstants.laserDistance = measurement.distance_mm;
        } else {
            EndEffectorConstants.laserDistance = -1.0;  // Error value
        }

        }
        
        
    @Override
    public void updateInputs (EndEffectorIOInputs inputs) {

        inputs.coralIntakePosition = coralIntakeEncoder.getPosition();
        inputs.coralIntakeVelocity = coralIntakeEncoder.getVelocity();
        inputs.coralIntakeVoltage = coralIntake.getAppliedOutput() * coralIntake.getBusVoltage();
        inputs.coralIntakeCurrent = coralIntake.getOutputCurrent();

        inputs.algaeIntakePosition = algaeIntakeEncoder.getPosition();
        inputs.algaeIntakeVelocity = algaeIntakeEncoder.getVelocity();
        inputs.algaeIntakeVoltage = algaeIntake.getAppliedOutput() * algaeIntake.getBusVoltage();
        inputs.algaeIntakeCurrent = algaeIntake.getOutputCurrent();
        
        inputs.algaeWristPosition = algaeWristEncoder.getPosition();
        inputs.algaeWristVelocity = algaeWristEncoder.getVelocity();
        inputs.algaeWristVoltage = algaeWrist.getAppliedOutput() * algaeWrist.getBusVoltage();
        inputs.algaeWristCurrent = algaeWrist.getOutputCurrent();
        inputs.coralIntakeBeamBreak = coralIntakeBeamBreakDigitalInput.get();
        inputs.laserDistance = EndEffectorConstants.laserDistance;

    }
   
    

    @Override
    public void setCoralIntakeVoltage (double volts) {

        coralIntake.setVoltage(volts);
    }
    
    @Override
    public void setAlgaeIntakeVoltage (double volts) {

        algaeIntake.setVoltage(volts);
    }

    @Override
    public void setAlgaeWristVoltage (double volts) {

        algaeWrist.setVoltage(volts);
    }
}