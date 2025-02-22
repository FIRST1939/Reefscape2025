package frc.robot.Funnel;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

// Funnel Simulation
public class FunnelIOSim implements FunnelIO {
 
    private final DCMotorSim FunnelMotorLeader = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 0.001, 1.0),
        DCMotor.getNeoVortex(1)
    );

    private final DCMotorSim FunnelMotorFollower = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 0.001, 1.0),
        DCMotor.getNeoVortex(1)
    );

    private double appliedVolts = 0.0;

    @Override
    public void updateInputs(FunnelIOInputs inputs) {

        FunnelMotorLeader.setInputVoltage(appliedVolts);
        FunnelMotorLeader.update(0.02);
        
        FunnelMotorFollower.setInputVoltage(-appliedVolts);
        FunnelMotorFollower.update(0.02);

        inputs.funnelPosition = 0.0;
        inputs.funnelVelocity = 0.0;
        inputs.funnelVoltage = 0.0;
        inputs.funnelCurrent = 0.0;
        inputs.funnelBeambreak = false;
    }

    @Override
    public void setFunnelVoltage(double volts) {
        
        appliedVolts = volts;
    }
}