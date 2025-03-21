package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.end_effector.EndEffector;

public class IndexCoral extends SequentialCommandGroup {
    
    public IndexCoral (EndEffector endEffector) {

        super(
            new IndexCoralIn(endEffector),
            new IndexCoralBack(endEffector)
        );
    }
}
