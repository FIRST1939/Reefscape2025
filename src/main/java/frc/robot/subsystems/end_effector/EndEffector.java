package frc.robot.subsystems.end_effector;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndEffector extends SubsystemBase {
    
    private final EndEffectorIO io;
    private final EndEffectorIOInputsAutoLogged inputs = new EndEffectorIOInputsAutoLogged();

    private double coralIntakeVelocity;
    private final SimpleMotorFeedforward coralIntakeFeedforward = new SimpleMotorFeedforward(0.53, 0.025);

    public EndEffector (EndEffectorIO io) {

        this.io = io;
    }
    
    @Override
    public void periodic() {
        
        io.updateInputs(inputs);
        Logger.processInputs("End Effector", this.inputs);

        this.runVoltage(this.coralIntakeFeedforward.calculate(this.coralIntakeVelocity), 0.0, 0.0);
    }

    public void runVoltage (double coralIntakeVolts, double algaeIntakeVolts, double algaeWristVolts) {

        io.setCoralIntakeVoltage(coralIntakeVolts);
        io.setAlgaeIntakeVoltage(algaeIntakeVolts);
        io.setAlgaeWristVoltage(algaeWristVolts);
    }

    public void setCoralIntakeVelocity (double velocity) {

        this.coralIntakeVelocity = velocity;
    }

    public double getCoralIntakePosition () {

        return this.inputs.coralIntakePosition;
    }

    public boolean getCoralIntakeBeambreak () {

        return inputs.coralBeambreak;
    }
}
