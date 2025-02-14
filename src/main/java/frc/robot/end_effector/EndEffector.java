package frc.robot.end_effector;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.end_effector.EndEffectorIO.EndEffectorIOInputs;

public class EndEffector extends SubsystemBase {
    
    private final EndEffectorIO io;
    private final EndEffectorIOInputs inputs = new EndEffectorIOInputs();

    public EndEffector (EndEffectorIO io) {

        this.io = io;
    }

   @Override
public void periodic() {
    io.updateInputs((EndEffectorIOInputs) inputs);

    // Log each input manually
    Logger.recordOutput("EndEffector/AlgaeIntakeVoltage", ((EndEffectorIOInputs) inputs).algaeIntakeVoltage);
    Logger.recordOutput("EndEffector/AlgaeWristVoltage", ((EndEffectorIOInputs) inputs).algaeWristVoltage);
    Logger.recordOutput("EndEffector/CoralIntakeVoltage", ((EndEffectorIOInputs) inputs).coralIntakeVoltage);
    Logger.recordOutput("EndEffector/IsRunning", ((EndEffectorIOInputs) inputs).isRunning);
}


    public void runVoltage (double algaeIntakeVolts, double algaeWristVolts, double coralIntakeVolts) {

        io.setAlgaeIntakeVoltage(algaeIntakeVolts);
        io.setAlgaeIntakeVoltage(algaeWristVolts);
        io.setCoralIntakeVoltage(coralIntakeVolts);
    }
};