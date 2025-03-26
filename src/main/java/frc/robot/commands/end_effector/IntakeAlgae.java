package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.util.SetPointConstants;

public class IntakeAlgae extends Command {
    
    private final EndEffector endEffector;
    private final double algaeWristPosition;

    public IntakeAlgae (EndEffector endEffector, double algaeWristPosition) {

        this.endEffector = endEffector;
        this.algaeWristPosition = algaeWristPosition;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeWristPosition(this.algaeWristPosition);
        this.endEffector.setAlgaeIntakeVoltage(SetPointConstants.ALGAE_INTAKE_VOLTAGE);
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setAlgaeWristPosition(0.0);
    }
}
