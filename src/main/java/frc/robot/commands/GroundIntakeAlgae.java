package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.elevator.ElevatorToHeight;
import frc.robot.commands.end_effector.IntakeAlgae;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.leds.LEDs;
import frc.robot.util.SetPointConstants;

public class GroundIntakeAlgae extends SequentialCommandGroup {
    
    public GroundIntakeAlgae (Elevator elevator, EndEffector endEffector, LEDs leds) {

        this.addCommands(
            new ElevatorToHeight(elevator, leds, SetPointConstants.ALGAE_INTAKE_GROUND_HEIGHT),
            new IntakeAlgae(endEffector, leds, SetPointConstants.ALGAE_INTAKE_GROUND_WRIST_POSITION)
        );
    }
}
