package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.leds.LEDs;

public class IndexCoral extends SequentialCommandGroup {
    
    public IndexCoral (EndEffector endEffector, LEDs leds) {

        super(
            Commands.runOnce(() -> leds.setCoralProcessing(), leds),
            new IndexCoralIn(endEffector),
            new IndexCoralBack(endEffector),
            Commands.runOnce(() -> leds.setCoralHolding(), leds)
        );
    }
}
