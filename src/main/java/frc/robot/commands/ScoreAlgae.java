package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.end_effector.EndEffector;

public class ScoreAlgae extends Command {

    private final EndEffector endEffector;

    public ScoreAlgae(EndEffector endEffector) {
        
        this.endEffector = endEffector;
        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeWristPosition(0.0);
        this.endEffector.setAlgaeIntakeVoltage(-3.0);
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setAlgaeWristPosition(0.0);
        this.endEffector.setAlgaeIntakeVoltage(0.0);
    }
}