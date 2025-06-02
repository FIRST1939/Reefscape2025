package frc.robot.subsystems.end_effector;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndEffector extends SubsystemBase {
    
    private final EndEffectorIO io;
    private final EndEffectorIOInputsAutoLogged inputs = new EndEffectorIOInputsAutoLogged();

    private final SimpleMotorFeedforward coralIntakeFeedforward = new SimpleMotorFeedforward(0.0, 0.0);
    private final PIDController algaeWristFeedback = new PIDController(0.0, 0.0, 0.0);

    public EndEffector (EndEffectorIO io) {

        this.io = io;
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
}
