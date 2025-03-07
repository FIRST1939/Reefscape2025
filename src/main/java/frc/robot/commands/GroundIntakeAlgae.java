package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.elevator.SetpointElevator;
import frc.robot.commands.end_effector.IntakeAlgae;
import frc.robot.commands.end_effector.PivotWrist;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.end_effector.EndEffector;

public class GroundIntakeAlgae extends SequentialCommandGroup {
    
    public GroundIntakeAlgae (Elevator elevator, EndEffector endEffector) {

        this.addCommands(
            new SetpointElevator(elevator, -0.15),
            new PivotWrist(endEffector, 175.0),
            new IntakeAlgae(endEffector, 2.0),
            new PivotWrist(endEffector, 0.0)
        );
    }
}
