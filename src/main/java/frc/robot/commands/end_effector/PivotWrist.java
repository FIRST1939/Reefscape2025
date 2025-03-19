package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class PivotWrist extends Command {
    
    private final EndEffector endEffector;
    private final double position;

    public PivotWrist (EndEffector endEffector, double position) {
    
        this.endEffector = endEffector;
        this.position = position;
    
        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeWristPosition(position);
    }

    @Override
    public boolean isFinished () {

        return Math.abs(this.position - this.endEffector.getAlgaeWristPosition()) < 15.0;
    }
}
