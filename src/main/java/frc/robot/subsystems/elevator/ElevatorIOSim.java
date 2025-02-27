package frc.robot.subsystems.elevator;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ElevatorIOSim implements ElevatorIO {
 
    private final DCMotorSim elevatorMotorLeader = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 0.001, 1.0),
        DCMotor.getNeoVortex(1)
    );

    private final DCMotorSim elevatorMotorFollower = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 0.001, 1.0),
        DCMotor.getNeoVortex(1)
    );

    private double appliedVolts = 0.0;

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {

        elevatorMotorLeader.setInputVoltage(appliedVolts);
        elevatorMotorLeader.update(0.02);
        
        elevatorMotorFollower.setInputVoltage(-appliedVolts);
        elevatorMotorFollower.update(0.02);

        inputs.elevatorPosition = 0.0;
        inputs.elevatorVelocity = 0.0;
        inputs.leaderVoltage = 0.0;
        inputs.leaderCurrent = 0.0;
        inputs.followerVoltage = 0.0;
        inputs.followerCurrent = 0.0;
    }

    @Override
    public  void move (double volts) {
        
        appliedVolts = volts;
    }
}
