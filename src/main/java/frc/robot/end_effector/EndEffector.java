package frc.robot.end_effector;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.end_effector.EndEffectorIOInputsAutoLogged;

public class EndEffector extends SubsystemBase {
    
    private final EndEffectorIO io;
    private final EndEffectorIOInputsAutoLogged inputs = new EndEffectorIOInputsAutoLogged();

    public EndEffector (EndEffectorIO io) {

        this.io = io;
    }
    
    @Override
    public void periodic() {
        
        io.updateInputs(inputs);
    }

    public void runVoltage (double coralIntakeVolts, double algaeIntakeVolts, double algaeWristVolts) {

        io.setCoralIntakeVoltage(coralIntakeVolts);
        io.setAlgaeIntakeVoltage(algaeIntakeVolts);
        io.setAlgaeWristVoltage(algaeWristVolts);
    }

    public boolean getCoralIntakeBeambreak () {

        return inputs.coralIntakeBeambreak;
    }

    public double getAlgaeIntakeLaserDistance () {

        return inputs.algaeIntakelaserDistance;
    }
}
