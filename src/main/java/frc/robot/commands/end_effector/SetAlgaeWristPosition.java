package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.util.SetPointConstants;
import org.littletonrobotics.junction.Logger;

public class SetAlgaeWristPosition extends Command {
    
    private final EndEffector endEffector;
    private final double algaeWristPosition;
    
    public SetAlgaeWristPosition(EndEffector endEffector, double algaeWristPosition) {
        this.endEffector = endEffector;
        this.algaeWristPosition = algaeWristPosition;
        addRequirements(endEffector); 
    }

    @Override
    public void initialize() {
        
        endEffector.setAlgaeWristPosition(algaeWristPosition);
        endEffector.setAlgaeIntakeVoltage(SetPointConstants.ALGAE_INTAKE_VOLTAGE);
    }

    @Override
    public void execute() {
        Logger.recordOutput("AlgaeWristPosition Running", true);
        endEffector.setAlgaeWristPosition(algaeWristPosition);
    }

    @Override
    public void end(boolean interrupted) {
        Logger.recordOutput("AlgaeWristPosition Running", false);
        endEffector.setAlgaeWristPosition(SetPointConstants.ALGAE_HOLD_WRIST_POSITION);
        endEffector.setAlgaeIntakeVoltage(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
