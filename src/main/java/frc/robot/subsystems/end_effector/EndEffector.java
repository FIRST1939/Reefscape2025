package frc.robot.subsystems.end_effector;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndEffector extends SubsystemBase {
    
    private final EndEffectorIO io;
    private final EndEffectorIOInputsAutoLogged inputs = new EndEffectorIOInputsAutoLogged();

    public EndEffector (EndEffectorIO io) {

        this.io = io;
    }
    
    @Override
    public void periodic() {
        
        io.updateInputs(inputs);
        Logger.processInputs("End Effector", this.inputs);
    }

    public double getAlgaeWristPosition () {

        return this.inputs.algaeWristPosition;
    }
}
