package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.end_effector.EndEffector;

public class PivotLoadAlgae extends Command {
    
    private final EndEffector endEffector;
    private final double position;
    private final double voltage;

    public PivotLoadAlgae (EndEffector endEffector, double position, double voltage) {

        this.endEffector = endEffector;
        this.position = position;
        this.voltage = voltage;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeWristPosition(this.position);
        this.endEffector.setAlgaeIntakeVoltage(this.voltage);
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setAlgaeWristPosition(0.0);
    }
}
