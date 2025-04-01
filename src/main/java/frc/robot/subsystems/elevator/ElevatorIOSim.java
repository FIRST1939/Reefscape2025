package frc.robot.subsystems.elevator;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;

public class ElevatorIOSim implements ElevatorIO {
 
    private final ElevatorSim firstElevatorStage = new ElevatorSim(
        LinearSystemId.createElevatorSystem(DCMotor.getNeoVortex(2), 8.829, 0.058, 3.0),
        DCMotor.getNeoVortex(2),
        0.091168,
        2.187,
        true,
        0.091168
    );

    private final ElevatorSim secondElevatorStage = new ElevatorSim(
        LinearSystemId.createElevatorSystem(DCMotor.getNeoVortex(2), 11.869, 0.058, 3.0),
        DCMotor.getNeoVortex(2),
        0.091168,
        2.187,
        true,
        0.091168
    );

    private final ElevatorSim thirdElevatorStage = new ElevatorSim(
        LinearSystemId.createElevatorSystem(DCMotor.getNeoVortex(2), 14.540, 0.058, 3.0),
        DCMotor.getNeoVortex(2),
        0.091168,
        2.187,
        true,
        0.091168
    );

    private double appliedVolts = 0.0;

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {

        ElevatorSim simulatedStage;

        if (inputs.elevatorPosition < 0.625) {

            simulatedStage = this.firstElevatorStage;
        } else if (inputs.elevatorPosition < 1.19) {

            simulatedStage = this.secondElevatorStage;
        } else {

            simulatedStage = this.thirdElevatorStage;
        }

        simulatedStage.setInputVoltage(appliedVolts);
        simulatedStage.update(0.02);

        inputs.elevatorPosition = simulatedStage.getPositionMeters();
        inputs.elevatorVelocity = simulatedStage.getVelocityMetersPerSecond();
        inputs.leaderVoltage = appliedVolts;
        inputs.followerVoltage = appliedVolts;
        inputs.leaderCurrent = simulatedStage.getCurrentDrawAmps();
        inputs.followerCurrent = simulatedStage.getCurrentDrawAmps();

        if (simulatedStage == this.firstElevatorStage) {

            this.secondElevatorStage.setState(inputs.elevatorPosition, inputs.elevatorVelocity);
            this.thirdElevatorStage.setState(inputs.elevatorPosition, inputs.elevatorVelocity);
        } else if (simulatedStage == this.secondElevatorStage) {

            this.firstElevatorStage.setState(inputs.elevatorPosition, inputs.elevatorVelocity);
            this.thirdElevatorStage.setState(inputs.elevatorPosition, inputs.elevatorVelocity);
        } else {

            this.firstElevatorStage.setState(inputs.elevatorPosition, inputs.elevatorVelocity);
            this.secondElevatorStage.setState(inputs.elevatorPosition, inputs.elevatorVelocity);
        }
    }

    @Override
    public  void move (double volts) {
        
        appliedVolts = volts;
    }
}
