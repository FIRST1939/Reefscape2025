package frc.robot.commands.end_effector;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class AlgaeWristVoltageTest extends Command {

    private final EndEffector endEffector;
    private final double position;

    public AlgaeWristVoltageTest(EndEffector endEffector, double position) {
        this.endEffector = endEffector;
        this.position = position;
        addRequirements(endEffector);
    }

    @Override
    public void initialize() {
        
        endEffector.setAlgaeWristPosition(position);
    }

    @Override
    public void end(boolean interrupted) {
        
        endEffector.setAlgaeWristPosition(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
