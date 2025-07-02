package frc.robot.subsystems.end_effector;

import org.littletonrobotics.junction.Logger;

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
    private final PIDController algaeWristFeedback = new PIDController(0.0, 0.0, 0.0);
    private final SysIdRoutine sysIdRoutine;

    public EndEffector (EndEffectorIO io) {

        this.io = io;

        sysIdRoutine = new SysIdRoutine(
            new SysIdRoutine.Config(),
            new SysIdRoutine.Mechanism(
                voltage -> io.setCoralIntakeVoltage(voltage.in(Units.Volts)),
                log -> {
                    log.motor("coralIntake")    
                        .voltage(inputs.coralIntakeVoltage)
                        .position(inputs.coralIntakePosition)    
                        .velocity(inputs.coralIntakeVelocity);
                },
                this
            )
        );
        
    }
    
    @Override
    public void periodic() {
        
        io.updateInputs(inputs);
        Logger.processInputs("End Effector", this.inputs);
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

    public Command sysIdQuasistaticForward() {
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

}
