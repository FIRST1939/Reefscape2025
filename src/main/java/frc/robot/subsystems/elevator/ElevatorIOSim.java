package frc.robot.subsystems.elevator;

import java.util.Random;

import com.revrobotics.sim.SparkFlexSim;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import frc.robot.util.CurrentDrawSim;
import frc.robot.util.LaserCanWrapper.LaserCanSim;

public class ElevatorIOSim extends ElevatorIOVortex {
 
    private final SparkFlexSim motor = new SparkFlexSim(super.leadMotor, DCMotor.getNeoVortex(2));
    private final LaserCanSim laserCan = super.laserCan.getSimulatedDevice();

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

        if (this.elevator.getPositionMeters() > 0.45) {

            this.laserCan.setStatus(2);
            this.laserCan.setDistance(0);
        } else {

            this.laserCan.setStatus(0);
            this.laserCan.setDistance(
                (int) Math.round(
                    this.random.nextGaussian(
                        this.elevator.getPositionMeters() * 1000, 
                        35 * this.elevator.getPositionMeters()
                    )
                )
            );
        }

        this.laserCan.setAmbient(
            (int) Math.round(
                this.random.nextGaussian(
                    875 * this.elevator.getPositionMeters(), 
                    200 * this.elevator.getPositionMeters()
                )
            )
        );

        super.updateInputs(inputs);
    }
}
