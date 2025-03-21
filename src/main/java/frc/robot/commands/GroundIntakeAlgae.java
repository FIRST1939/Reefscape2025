package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.SetPointConstants;
import frc.robot.commands.elevator.ElevatorToHeight;
import frc.robot.commands.end_effector.IntakeAlgae;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.end_effector.EndEffector;

public class GroundIntakeAlgae extends ParallelCommandGroup {
    
    public GroundIntakeAlgae (Elevator elevator, EndEffector endEffector) {

        this.addCommands(
            new ElevatorToHeight(elevator, SetPointConstants.ALGAE_INTAKE_GROUND_HEIGHT),
            new IntakeAlgae(endEffector, SetPointConstants.ALGAE_INTAKE_GROUND_WRIST_POSITION)
        );
    }
}
