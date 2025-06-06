package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.end_effector.IndexCoral;
import frc.robot.commands.funnel.FunnelCoral;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.funnel.Funnel;
import frc.robot.subsystems.leds.LEDs;

public class IntakeCoral extends ParallelDeadlineGroup {
    
    public IntakeCoral (EndEffector endEffector, Funnel funnel, LEDs leds) {

        super(
            new IndexCoral(endEffector, leds),
            new FunnelCoral(funnel)
        );
    }
}
