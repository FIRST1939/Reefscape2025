package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.elevator.SetpointElevator;
import frc.robot.commands.end_effector.PivotLoadAlgae;
import frc.robot.commands.end_effector.PivotWrist;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.end_effector.EndEffector;

public class GroundIntakeAlgae extends SequentialCommandGroup {
    
    public GroundIntakeAlgae (Elevator elevator, EndEffector endEffector, CommandXboxController controller) {

        this.addCommands(
            Commands.parallel(
                new SetpointElevator(elevator, -0.15),
                new PivotLoadAlgae(endEffector, 175.0, 2.0, controller)
            ),
            new PivotWrist(endEffector, 0.0)
        );
    }
}
