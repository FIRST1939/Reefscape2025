package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class AlgaeWristVoltageTest extends Command {

    private final EndEffector endEffector;
    private final double voltage;

    public AlgaeWristVoltageTest(EndEffector endEffector, double voltage) {
        this.endEffector = endEffector;
        this.voltage = voltage;
        addRequirements(endEffector);
    }

    @Override
    public void execute() {
        endEffector.setAlgaeIntakeVoltage(voltage);
    }

    @Override
    public void end(boolean interrupted) {
        endEffector.setAlgaeIntakeVoltage(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
