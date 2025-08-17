package frc.robot.subsystems.elevator;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import static edu.wpi.first.units.Units.Seconds;
import static edu.wpi.first.units.Units.Volts;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.units.Units;


public class Elevator extends SubsystemBase {
    
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    private final SysIdRoutine elevatorSysIdRoutine;
    public final static ElevatorFeedforward coralIntakeFeedforward = new ElevatorFeedforward(0, 0, 0);
      

    public Elevator (ElevatorIO io) {

        this.io = io;

        elevatorSysIdRoutine = new SysIdRoutine(
            new SysIdRoutine.Config(Volts.per(Units.Second).of(1), Volts.of(2), Seconds.of(10)),
            new SysIdRoutine.Mechanism(
            voltage -> io.setElevatorVoltage(voltage.in(Units.Volts)),
            log -> {
            log
                .motor("coralIntake")
                .voltage(Volts.of(getVoltage()))
                .angularPosition(Radians.of(getHeight()))
                .angularVelocity(RotationsPerSecond.of(getVelocity()));
        },

        this
        ) 
    );
    }

    @Override
    public void periodic () {

        this.io.updateInputs(this.inputs);
        Logger.processInputs("Elevator", this.inputs);
    }

    public double getHeight () {

        return ((this.inputs.leadMotorPosition) + (this.inputs.followerMotorPosition) / 2)+ 0.091;
    }

    public double getVelocity () {
        return ((this.inputs.leadMotorVelocity) + (this.inputs.followerMotorVelocity) / 2 );
    }

    public double getVoltage () {
        return ((this.inputs.leadMotorVoltage) + (this.inputs.followerMotorVoltage) / 2 );
    }

    public double getCurrent () {
        return ((this.inputs.leadMotorCurrent) + (this.inputs.followerMotorCurrent) / 2 );
    }

    public double getTemperature () {
        return ((this.inputs.leadMotorTemperature) + (this.inputs.followerMotorTemperature) / 2 );
    }
/* 
    public Command sysIdQuasistaticForward() {
        return elevatorSysIdRoutine.quasistatic(SysIdRoutine.Direction.kForward);
    }

    public Command sysIdQuasistaticReverse() {
        return elevatorSysIdRoutine.quasistatic(SysIdRoutine.Direction.kReverse);
    }

    public Command sysIdDynamicForward() {
        return elevatorSysIdRoutine.dynamic(SysIdRoutine.Direction.kForward);
    }

    public Command sysIdDynamicReverse() {
        return elevatorSysIdRoutine.dynamic(SysIdRoutine.Direction.kReverse);
    }
        */
}
