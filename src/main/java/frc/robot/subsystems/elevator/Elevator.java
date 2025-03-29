package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
    
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    private final ElevatorFeedforward firstFeedforward = new ElevatorFeedforward(
        ElevatorConstants.kS,
        ElevatorConstants.kG_1,
        ElevatorConstants.kV_1
    );

    private final ElevatorFeedforward secondFeedforward = new ElevatorFeedforward(
        ElevatorConstants.kS,
        ElevatorConstants.kG_2,
        ElevatorConstants.kV_2
    );

    private final ElevatorFeedforward thirdFeedforward = new ElevatorFeedforward(
        ElevatorConstants.kS,
        ElevatorConstants.kG_3,
        ElevatorConstants.kV_3
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

    private double offset = 0.0;

    public Elevator (ElevatorIO io) {

        this.io = io;
        this.controller.setTolerance(ElevatorConstants.tolerance);
        this.controller.setIZone(ElevatorConstants.kIZ);
    }

    @Override
    public void periodic () {

        this.io.updateInputs(this.inputs);
        Logger.processInputs("Elevator", this.inputs);

        if (DriverStation.isEnabled() && !this.isManual()) {

            double feedback = this.controller.calculate(this.inputs.elevatorPosition);
            double feedforward;
            
            if (this.inputs.elevatorPosition < ElevatorConstants.FIRST_ELEVATOR_TRANSITION) {

                feedforward = this.firstFeedforward.calculate(this.controller.getSetpoint().velocity);
            } else if (this.inputs.elevatorPosition < ElevatorConstants.SECOND_ELEVATOR_TRANSITION) {

                feedforward = this.secondFeedforward.calculate(this.controller.getSetpoint().velocity);
            } else {

                feedforward = this.thirdFeedforward.calculate(this.controller.getSetpoint().velocity);
            }

            double voltage = MathUtil.clamp(feedback + feedforward, -ElevatorConstants.maxVoltage, ElevatorConstants.maxVoltage);
            this.io.move(voltage);
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
