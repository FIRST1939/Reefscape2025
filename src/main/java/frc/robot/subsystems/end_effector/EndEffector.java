package frc.robot.subsystems.end_effector;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndEffector extends SubsystemBase {
    
    private final EndEffectorIO io;
    private final EndEffectorIOInputsAutoLogged inputs = new EndEffectorIOInputsAutoLogged();

    private final SimpleMotorFeedforward coralIntakeFeedforward = new SimpleMotorFeedforward(0.53, 0.025);

    public EndEffector (EndEffectorIO io) {

        this.io = io;
    }
    
    @Override
    public void periodic() {
        
        io.updateInputs(inputs);
        Logger.processInputs("End Effector", this.inputs);    }

    public boolean isManual () {

        return this.inputs.manual;
    }

    public void setCoralIntakeVelocity (double velocity) {

        this.io.setCoralIntakeVoltage(this.coralIntakeFeedforward.calculate(velocity));
    }

    public void setAlgaeIntakeVoltage (double voltage) {

        this.io.setAlgaeIntakeVoltage(voltage);
    }

    public void setAlgaeWristVoltage (double voltage) {

        this.io.setAlgaeWristVoltage(voltage);
    }

    public double getCoralIntakePosition () {

        return this.inputs.coralIntakePosition;
    }

    public boolean getCoralIntakeBeambreak () {

        return inputs.coralBeambreak;
    }
}
