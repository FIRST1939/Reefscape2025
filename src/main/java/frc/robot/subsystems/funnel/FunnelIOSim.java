package frc.robot.subsystems.funnel;

import com.revrobotics.sim.SparkFlexSim;
import com.revrobotics.sim.SparkRelativeEncoderSim;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import frc.robot.util.CurrentDrawSim;

public class FunnelIOSim extends FunnelIOVortex {

    private final SparkFlexSim motor = new SparkFlexSim(super.motor, DCMotor.getNeoVortex(1));
    private final SparkRelativeEncoderSim encoder = new SparkRelativeEncoderSim(super.motor);

    private final FlywheelSim funnel = new FlywheelSim(
        LinearSystemId.createFlywheelSystem(
            DCMotor.getNeoVortex(1), 
            0.016, 
            1.0
        ), 
        DCMotor.getNeoVortex(1)
    );

    public FunnelIOSim () {

        super();
        
        this.encoder.setPosition(super.encoder.getPosition());
    }

    @Override
    public void updateInputs (FunnelIOInputsAutoLogged inputs) {

        this.funnel.setInputVoltage(this.motor.getAppliedOutput() * RoboRioSim.getVInVoltage());
        this.funnel.update(0.02);

        this.motor.iterate(
            this.funnel.getAngularVelocityRPM() / 60.0,
            RoboRioSim.getVInVoltage(),
            0.02
        );

        CurrentDrawSim.setFunnelCurrentDraw(this.funnel.getCurrentDrawAmps());

        super.updateInputs(inputs);
    }
}
