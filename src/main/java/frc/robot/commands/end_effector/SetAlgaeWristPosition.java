package frc.robot.commands.end_effector;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.util.SetPointConstants;

public class SetAlgaeWristPosition extends Command {
    
    private final EndEffector endEffector;
    private final double algaeWristPosition;

    public SetAlgaeWristPosition (EndEffector endEffector, double algaeWristPosition) {

        this.endEffector = endEffector;

        this.algaeWristPosition = algaeWristPosition;
        this.addRequirements(this.endEffector);
    }

    @Override
    public void execute () {
        Logger.recordOutput("AlgaeWristPosition Running", true);
        this.endEffector.setAlgaeWristPosition(this.algaeWristPosition);
        this.endEffector.setAlgaeIntakeVoltage(SetPointConstants.ALGAE_INTAKE_VOLTAGE);
    }

    @Override
    public void end (boolean interrupted) {
        Logger.recordOutput("AlgaeWristPosition Running", false);
        this.endEffector.setAlgaeWristPosition(SetPointConstants.ALGAE_HOLD_WRIST_POSITION);
    }
}