package frc.robot.commands.end_effector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.leds.LEDs;

public class IntakeAlgae extends Command {
    
    private final EndEffector endEffector;
    private final double algaeWristPosition;
    private final LEDs leds;

    public IntakeAlgae (EndEffector endEffector, LEDs leds, double algaeWristPosition) {

        this.endEffector = endEffector;
        this.leds = leds;

        this.algaeWristPosition = algaeWristPosition;
        this.addRequirements(this.endEffector, this.leds);
    }

    @Override
    public void initialize () {

        this.endEffector.setAlgaeWristPosition(this.algaeWristPosition);
        this.endEffector.setAlgaeIntakeVoltage(SetPointConstants.ALGAE_INTAKE_VOLTAGE);
        this.leds.setAlgaeProcessing();
    }

    @Override
    public void end (boolean interrupted) {

        this.endEffector.setAlgaeWristPosition(0.0);
        this.leds.setAlgaeHolding();
    }
}
