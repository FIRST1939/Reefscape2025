package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
        0.0, 
        ElevatorConstants.kD, 
        new TrapezoidProfile.Constraints(
            ElevatorConstants.maxVelocity, 
            ElevatorConstants.maxAcceleration
        )
    );

    private double offset = 0.0;

    private final PIDController close = new PIDController(0.0, ElevatorConstants.kI, 0.0);

    public Elevator (ElevatorIO io) {

        this.io = io;
        this.controller.setTolerance(ElevatorConstants.tolerance);
        this.close.setIZone(ElevatorConstants.kIZ);
    }

    @Override
    public void periodic () {

        this.io.updateInputs(this.inputs);
        Logger.processInputs("Elevator", this.inputs);

        if (!this.isManual()) {

            double feedback = this.controller.calculate(this.inputs.elevatorPosition);
            double feedforward = this.feedforward.calculate(this.controller.getSetpoint().velocity);
            double close = this.close.calculate(this.inputs.elevatorPosition, this.controller.getSetpoint().position);

            double voltage = MathUtil.clamp(feedback + feedforward + close, -ElevatorConstants.maxVoltage, ElevatorConstants.maxVoltage);
            this.io.move(voltage);
            
            SmartDashboard.putNumber("Ele", voltage);
        }
    }

    public boolean isManual () {

        return this.inputs.manual;
    }

    public void setGoal (double goal) {

        this.controller.setGoal(goal + this.offset);
    }

    public boolean atGoal () {

        return this.controller.atGoal();
    }

    public void runVoltage (double volts) {

        io.move(volts);
        this.controller.setGoal(this.inputs.elevatorPosition);
    }
}
