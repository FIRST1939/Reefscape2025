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
        this.endEffector.setAlgaeIntakeVoltage(2.0);
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setAlgaeWristPosition(0.0);
    }
}
