package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

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
        this.controller.setTolerance(ElevatorConstants.tolerance);
    }

    @Override
    public void periodic () {

        this.io.updateInputs(this.inputs);
        Logger.processInputs("Elevator", this.inputs);

        SmartDashboard.putNumber("Ele Goal", this.controller.getGoal().position);

        if (!this.isManual()) {

            double feedback = this.controller.calculate(this.inputs.elevatorPosition);
            double feedforward = this.feedforward.calculate(this.controller.getSetpoint().velocity);

            double voltage = MathUtil.clamp(feedback + feedforward, -ElevatorConstants.maxVoltage, ElevatorConstants.maxVoltage);
            this.io.move(voltage);
        }
    }

    public boolean isManual () {

        return this.inputs.manual;
    }

    public void setGoal (double goal) {

        this.controller.setGoal(goal);
    }

    public boolean atGoal () {

        return this.controller.atGoal();
    }

    public void runVoltage (double volts) {

        io.move(volts);
        this.controller.setGoal(this.inputs.elevatorPosition);
    }
}
