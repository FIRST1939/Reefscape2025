package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.end_effector.EndEffector;

public class LoadAlgae extends SequentialCommandGroup {
    
    public LoadAlgae (EndEffector endEffector, double position, double algaeIntakeVoltage) {

        this.addCommands(
            new PivotWrist(endEffector, position),
            new IntakeAlgae(endEffector, algaeIntakeVoltage)
        );
    }
}
