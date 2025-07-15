package frc.robot.subsystems.end_effector;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.units.Units;


public class EndEffector extends SubsystemBase {
    
    private final EndEffectorIO io;
    private final EndEffectorIOInputsAutoLogged inputs = new EndEffectorIOInputsAutoLogged();

    private final SimpleMotorFeedforward coralIntakeFeedforward = new SimpleMotorFeedforward(0.0, 0.0);
    private final PIDController algaeWristFeedback = new PIDController(0.06, 0.0, 0.0);
    private final SysIdRoutine sysIdRoutine;

    public EndEffector (EndEffectorIO io) {

        this.io = io;

        this.sysIdRoutine = new SysIdRoutine(
            new SysIdRoutine.Config(),
            new SysIdRoutine.Mechanism(
            voltage -> io.setAlgaeWristVoltage(voltage.in(Units.Volts)),
            log -> {
            log
                .motor("algaeWrist")
                .voltage(Volts.of(inputs.algaeWristVoltage))
                .angularPosition(Rotations.of(inputs.algaeWristPosition))
                .angularVelocity(RotationsPerSecond.of(inputs.algaeWristVelocity));
        },

        this
        ) 
    );
        
    }
    
   @Override
    public void periodic() {

        io.updateInputs(inputs);

        Logger.processInputs("End Effector", this.inputs);

        this.io.setAlgaeWristVoltage(MathUtil.clamp(this.algaeWristFeedback.calculate(this.inputs.algaeWristPosition), -3.5, 3.5));

        
    }

    public double getCoralIntakeVelocity () {

        return inputs.coralIntakeVelocity;
    }

    public double getAlgaeWristPosition () {

        return this.inputs.algaeWristPosition;
    }

    public boolean isManual () {

        return this.inputs.manual;
    }

    public boolean getCoralIntakeBeambreak () {

        return inputs.coralBeambreak;
    }

    public void setCoralIntakeVelocity (double velocity) {

        this.io.setCoralIntakeVoltage(this.coralIntakeFeedforward.calculate(velocity));
    }

    public void setAlgaeWristPosition (double position) {

        this.algaeWristFeedback.setSetpoint(position);
    }

    public Command sysIdQuasistaticForward() {
        Logger.recordOutput("sysIdQuasistaticForward running", true);
        return sysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward);
    }

    public Command sysIdQuasistaticReverse() {
        return sysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse);
    }

    public Command sysIdDynamicForward() {
        return sysIdRoutine.dynamic(SysIdRoutine.Direction.kForward);
    }

    public Command sysIdDynamicReverse() {
        return sysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse);
    }

    public void setAlgaeIntakeVoltage(double voltage) {
        
        this.io.setAlgaeIntakeVoltage(voltage);
    }

}
