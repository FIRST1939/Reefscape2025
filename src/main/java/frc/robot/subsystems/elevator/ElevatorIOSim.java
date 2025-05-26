package frc.robot.subsystems.elevator;

import com.revrobotics.sim.SparkFlexSim;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorIOSim extends ElevatorIOVortex {
 
    private final SparkFlexSim motorSim = new SparkFlexSim(this.leadMotor, DCMotor.getNeoVortex(2));
    private double lastTimestamp = Timer.getTimestamp();

    private final ElevatorSim firstElevatorStage = new ElevatorSim(
        DCMotor.getNeoVortex(2), 
        3.0, 
        8.829, 
        0.058, 
        0.091168, 
        2.187, 
        true, 
        0.091168
    );

    private final ElevatorSim secondElevatorStage = new ElevatorSim(
        DCMotor.getNeoVortex(2),
        3.0,
        11.869,
        0.058,
        0.091168,
        2.187,
        true,
        0.091168
    );

    private final ElevatorSim thirdElevatorStage = new ElevatorSim(
        DCMotor.getNeoVortex(2),
        3.0,
        14.540,
        0.058,
        0.091168,
        2.187,
        true,
        0.091168
    );

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {

        ElevatorSim activeStage;

        if (this.firstElevatorStage.getPositionMeters() < ElevatorConstants.FIRST_STAGE_TRANSITION) {

            activeStage = this.firstElevatorStage;
        } else if (this.firstElevatorStage.getPositionMeters() < ElevatorConstants.SECOND_STAGE_TRANSITION) {

            activeStage = this.secondElevatorStage;
        } else {

            activeStage = this.thirdElevatorStage;
        }

        double dt = Timer.getTimestamp() - this.lastTimestamp;
        this.lastTimestamp = Timer.getTimestamp();

        activeStage.setInputVoltage(this.motorSim.getAppliedOutput() * 12.0);
        activeStage.update(dt);

        double elevatorPosition = activeStage.getPositionMeters();
        double elevatorVelocity = activeStage.getVelocityMetersPerSecond();

        SmartDashboard.putNumber("position", elevatorPosition);
        SmartDashboard.putNumber("velocity", elevatorVelocity);

        if (this.firstElevatorStage != activeStage) {

            this.firstElevatorStage.setState(elevatorPosition, elevatorVelocity);
            this.firstElevatorStage.update(0.000001);
        } if (this.secondElevatorStage != activeStage) {

            this.secondElevatorStage.setState(elevatorPosition, elevatorVelocity);
            this.secondElevatorStage.update(0.000001);
        } if (this.thirdElevatorStage != activeStage) {

            this.thirdElevatorStage.setState(elevatorPosition, elevatorVelocity);
            this.thirdElevatorStage.update(0.000001);
        }

        RoboRioSim.setVInVoltage(BatterySim.calculateDefaultBatteryLoadedVoltage(activeStage.getCurrentDrawAmps()));

        this.motorSim.iterate(
            elevatorVelocity, 
            12.0, 
            dt
        );

        inputs.manual = this.manual.get();

        inputs.motorPosition = this.motorSim.getPosition();
        inputs.motorVelocity = this.motorSim.getVelocity();
        inputs.motorVoltage = this.motorSim.getAppliedOutput() * 12.0;
        inputs.motorCurrent = this.motorSim.getMotorCurrent();
        inputs.motorTemperature = 0.0;

        inputs.laserCANStatus = 2;
        inputs.laserCANDistance = 0.0;
    }
}
