package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class AutomaticEndEffectorBack extends Command {
    
    private final EndEffector endEffector;
    private final double backVoltage;
    private double coralIntakePosition;

    public AutomaticEndEffectorBack (EndEffector endEffector, double inVoltage) {

        this.endEffector = endEffector;
        this.backVoltage = inVoltage;

        this.addRequirements(this.endEffector);
    }

    @Override
    public void initialize () {

        this.coralIntakePosition = this.endEffector.getCoralIntakePosition();
        this.endEffector.setCoralIntakeVelocity(backVoltage);
    }

    @Override
    public boolean isFinished () {

        return (this.coralIntakePosition - this.endEffector.getCoralIntakePosition()) > 0.5;
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setCoralIntakeVelocity(0.0);
    }
}
