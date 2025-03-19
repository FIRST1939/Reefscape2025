package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.funnel.Funnel;

public class Purge extends Command {
    
    private final EndEffector endEffector;
    private final Funnel funnel;

    public Purge (EndEffector endEffector, Funnel funnel) {

        this.endEffector = endEffector;
        this.funnel = funnel;

        this.addRequirements(this.endEffector, this.funnel);
    }

    @Override
    public void initialize () {

        this.endEffector.setCoralIntakeVelocity(-20.0);
        this.funnel.runVoltage(-1.5);
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setCoralIntakeVelocity(0.0);
        this.funnel.runVoltage(0.0);
    }
}
