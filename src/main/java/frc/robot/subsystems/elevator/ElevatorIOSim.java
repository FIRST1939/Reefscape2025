package frc.robot.subsystems.elevator;

import java.util.Random;

import com.revrobotics.sim.SparkFlexSim;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import frc.robot.util.CurrentDrawSim;

public class ElevatorIOSim extends ElevatorIOVortex {
 
    private final SparkFlexSim motor = new SparkFlexSim(this.leadMotor, DCMotor.getNeoVortex(2));

    private final ElevatorSim elevator = new ElevatorSim(
        DCMotor.getNeoVortex(2),
        3.0,
        14.540,
        0.058,
        0.091168,
        2.187,
        true,
        0.091168
    );

    private final Random random = new Random();
    private double beltSlippage;

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {

        this.elevator.setInputVoltage(this.motor.getAppliedOutput() * RoboRioSim.getVInVoltage());
        this.elevator.update(0.02);

        this.motor.iterate(
            this.elevator.getVelocityMetersPerSecond(), 
            RoboRioSim.getVInVoltage(), 
            0.02
        );

        CurrentDrawSim.setElevatorCurrentDraw(this.elevator.getCurrentDrawAmps());

        this.beltSlippage += 0.001 * Math.abs(this.elevator.getVelocityMetersPerSecond() * 0.02);

        inputs.manual = this.manual.get();

        inputs.motorPosition = this.random.nextGaussian(this.motor.getPosition(), 0.001) + this.beltSlippage;
        inputs.motorVelocity = this.random.nextGaussian(this.motor.getVelocity(), 0.01);
        inputs.motorVoltage = this.motor.getAppliedOutput() * this.motor.getBusVoltage();
        inputs.motorCurrent = this.motor.getMotorCurrent();
        inputs.motorTemperature = 0.0;

        if (this.elevator.getPositionMeters() > 0.45) {

            inputs.laserCANStatus = 2;
            inputs.laserCANDistance = 0.0;
        } else {

            inputs.laserCANStatus = 0;
            inputs.laserCANDistance = this.random.nextGaussian(this.elevator.getPositionMeters(), 0.035 * this.elevator.getPositionMeters());
        }
    }
}
