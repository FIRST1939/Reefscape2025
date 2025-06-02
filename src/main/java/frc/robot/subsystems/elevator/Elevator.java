package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    public Elevator (ElevatorIO io) {

        this.io = io;
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
}
