package frc.robot.subsystems.end_effector;
import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;

public class EndEffectorIOVortex implements EndEffectorIO {

    private final LoggedNetworkBoolean manual = new LoggedNetworkBoolean("Manual End Effector", false);

    protected final SparkFlex coralIntakeMotor = new SparkFlex(EndEffectorConstants.CORAL_INTAKE_CAN, MotorType.kBrushless);
    protected final SparkFlex algaeIntakeMotor = new SparkFlex(EndEffectorConstants.ALGAE_INTAKE_CAN, MotorType.kBrushless); 
    protected final SparkFlex algaeWristMotor = new SparkFlex(EndEffectorConstants.ALGAE_WRIST_CAN, MotorType.kBrushless);
    
    private final SparkClosedLoopController algaeWristController = algaeWristMotor.getClosedLoopController();
    private final SparkClosedLoopController coralIntakeController = coralIntakeMotor.getClosedLoopController();
    protected final RelativeEncoder coralIntakeEncoder = this.coralIntakeMotor.getEncoder();
    protected final RelativeEncoder algaeIntakeEncoder = this.algaeIntakeMotor.getEncoder();
    protected final RelativeEncoder algaeWristEncoder = this.algaeWristMotor.getEncoder();

    private final SparkLimitSwitch coralBeambreak = coralIntakeMotor.getForwardLimitSwitch();
    
    public EndEffectorIOVortex () {

        SparkFlexConfig coralIntakeconfig = new SparkFlexConfig();
        SparkFlexConfig algaeIntakeconfig = new SparkFlexConfig();
        SparkFlexConfig algaeWristconfig = new SparkFlexConfig();


        coralIntakeconfig.closedLoop
        .p(0.027725)
        .i(0)
        .d(0)
        .outputRange(-12, 12);

        coralIntakeconfig.closedLoop.maxMotion
        .maxVelocity(2)
        .maxAcceleration(4)
        .allowedClosedLoopError(0);      

        algaeWristconfig.closedLoop
        .p(169.0)
        .i(0.0)
        .d(50)
        .outputRange(-12.0, 12.0);

        algaeWristconfig.closedLoop.maxMotion
        .maxVelocity(50)
        .maxAcceleration(300)
        .allowedClosedLoopError(0);      

        algaeWristconfig.closedLoop.velocityFF(1/20.95);

        coralIntakeconfig.limitSwitch.forwardLimitSwitchEnabled(false);

        coralIntakeconfig.encoder
            .positionConversionFactor(1.0 / EndEffectorConstants.CORAL_INTAKE_REDUCTION)
            .velocityConversionFactor(1.0 / EndEffectorConstants.CORAL_INTAKE_REDUCTION / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        algaeIntakeconfig.encoder
            .positionConversionFactor(1.0 / EndEffectorConstants.ALGAE_INTAKE_REDUCTION)
            .velocityConversionFactor(1.0 / EndEffectorConstants.ALGAE_INTAKE_REDUCTION / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        algaeWristconfig.encoder
            .positionConversionFactor(1.0 / EndEffectorConstants.ALGAE_WRIST_REDUCTION)
            .velocityConversionFactor(1.0 / EndEffectorConstants.ALGAE_WRIST_REDUCTION / 60.0)
            .uvwMeasurementPeriod(10)
            .uvwAverageDepth(2);

        coralIntakeMotor.configure(coralIntakeconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        algaeIntakeMotor.configure(algaeIntakeconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        algaeWristMotor.configure(algaeWristconfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
       
        algaeWristEncoder.setPosition(0.25);
        
    }

    
    @Override
    public void updateInputs (EndEffectorIOInputs inputs) {

        inputs.manual = this.manual.get();

        inputs.coralIntakePosition = coralIntakeEncoder.getPosition();
        inputs.coralIntakeVelocity = coralIntakeEncoder.getVelocity();
        inputs.coralIntakeVoltage = coralIntakeMotor.getAppliedOutput() * coralIntakeMotor.getBusVoltage();
        inputs.coralIntakeCurrent = coralIntakeMotor.getOutputCurrent();
        inputs.coralIntakeTemperature = coralIntakeMotor.getMotorTemperature();

        inputs.algaeIntakePosition = algaeIntakeEncoder.getPosition();
        inputs.algaeIntakeVelocity = algaeIntakeEncoder.getVelocity();
        inputs.algaeIntakeVoltage = algaeIntakeMotor.getAppliedOutput() * algaeIntakeMotor.getBusVoltage();
        inputs.algaeIntakeCurrent = algaeIntakeMotor.getOutputCurrent();
        inputs.algaeIntakeTemperature = algaeIntakeMotor.getMotorTemperature();
        
        inputs.algaeWristPosition = algaeWristEncoder.getPosition();
        inputs.algaeWristVelocity = algaeWristEncoder.getVelocity();
        inputs.algaeWristVoltage = algaeWristMotor.getAppliedOutput() * algaeWristMotor.getBusVoltage();
        inputs.algaeWristCurrent = algaeWristMotor.getOutputCurrent();
        inputs.algaeWristTemperature = algaeWristMotor.getMotorTemperature();

        inputs.coralBeambreak = coralBeambreak.isPressed();
    }

    @Override
    public void setCoralIntakeVoltage (double volts) {

        coralIntakeMotor.setVoltage(volts);
    }
    
    @Override
    public void setAlgaeIntakeVoltage (double volts) {

        algaeIntakeMotor.setVoltage(volts);
     }

    @Override
    public void setAlgaeWristVoltage (double volts) {

        algaeWristMotor.setVoltage(volts);
    }

    @Override
    public void setAlgaeWristControllerReference (double position, double feedforward) {
        
        algaeWristController.setReference(position, ControlType.kMAXMotionPositionControl, ClosedLoopSlot.kSlot0, feedforward);
    }

    @Override
    public void setCoralIntakeControllerReference (double position, double feedforward) {
        
        coralIntakeController.setReference(position, ControlType.kVelocity, ClosedLoopSlot.kSlot1, feedforward);
    }
        
    }
    
