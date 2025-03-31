package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SetPointConstants;

public class Elevator extends SubsystemBase {
    
    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    private final ElevatorFeedforward firstFeedforward = new ElevatorFeedforward(
        ElevatorConstants.kS_1,
        ElevatorConstants.kG_1,
        ElevatorConstants.kV_1
    );

    private final ElevatorFeedforward secondFeedforward = new ElevatorFeedforward(
        ElevatorConstants.kS_2,
        ElevatorConstants.kG_2,
        ElevatorConstants.kV_2
    );

    private final ElevatorFeedforward thirdFeedforward = new ElevatorFeedforward(
        ElevatorConstants.kS_3,
        ElevatorConstants.kG_3,
        ElevatorConstants.kV_3
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

        if (DriverStation.isEnabled()) {

            double feedback = this.controller.calculate(this.inputs.elevatorPosition);
            if (this.inputs.elevatorPosition < SetPointConstants.CORAL_INTAKE_HEIGHT && feedback < 0) { return; }

            double close = this.close.calculate(this.inputs.elevatorPosition, this.controller.getSetpoint().position);
            double feedforward;
            
            if (this.inputs.elevatorPosition < ElevatorConstants.FIRST_ELEVATOR_TRANSITION) {

                feedforward = this.firstFeedforward.calculate(this.controller.getSetpoint().velocity);
            } else if (this.inputs.elevatorPosition < ElevatorConstants.SECOND_ELEVATOR_TRANSITION) {

                feedforward = this.secondFeedforward.calculate(this.controller.getSetpoint().velocity);
            } else {

                feedforward = this.thirdFeedforward.calculate(this.controller.getSetpoint().velocity);
            }

            SmartDashboard.putNumber("DesiredV", this.controller.getSetpoint().velocity);
            SmartDashboard.putNumber("DesiredP", this.controller.getSetpoint().position);

            double voltage = MathUtil.clamp(feedback + close + feedforward, -ElevatorConstants.maxVoltage, ElevatorConstants.maxVoltage);
            this.io.move(voltage);
        }
    }

    public boolean isManual () {

        return this.inputs.manual;
    }

    public double getHeight () {

        return this.inputs.elevatorPosition;
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
