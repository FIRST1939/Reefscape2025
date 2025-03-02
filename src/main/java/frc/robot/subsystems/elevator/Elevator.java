package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    private boolean manual = false;

    private final ElevatorFeedforward feedforward = new ElevatorFeedforward(
        ElevatorConstants.kS,
        ElevatorConstants.kG,
        ElevatorConstants.kV
    );

    private final ProfiledPIDController controller = new ProfiledPIDController(
        ElevatorConstants.kP, 
        ElevatorConstants.kI, 
        ElevatorConstants.kD, 
        new TrapezoidProfile.Constraints(
            ElevatorConstants.maxVelocity, 
            ElevatorConstants.maxAcceleration
        )
    );

    public Elevator (ElevatorIO io) {

        this.io = io;
    }

    @Override
    public void periodic () {

        Logger.processInputs("Elevator", inputs);

        if (!this.manual) {

            double feedback = this.controller.calculate(this.inputs.elevatorPosition);
            double feedforward = this.feedforward.calculate(this.controller.getSetpoint().velocity);

            this.io.move(feedback + feedforward);
        }
    }

    public void setPosition (double position) {

        this.manual = false;
        this.controller.setGoal(position);
    }

    public void runVoltage (double volts) {

        this.manual = true;
        io.move(volts);
    }
}
