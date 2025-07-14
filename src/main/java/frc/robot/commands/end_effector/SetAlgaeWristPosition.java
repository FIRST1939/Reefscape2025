package frc.robot.commands.end_effector;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.util.SetPointConstants;


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
    }

    @Override
    public void execute() {
        
        endEffector.setAlgaeWristPosition(algaeWristPosition);
    }

    @Override
    public void end(boolean interrupted) {
        
        endEffector.setAlgaeWristPosition(SetPointConstants.ALGAE_HOLD_WRIST_POSITION);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
