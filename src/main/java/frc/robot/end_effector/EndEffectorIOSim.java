package frc.robot.end_effector;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class EndEffectorIOSim implements EndEffectorIO {
 
    private final DCMotorSim coralIntake = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 0.017, EndEffectorConstants.coralIntakeGearReduction),
        DCMotor.getNeoVortex(1)
    );

    private final DCMotorSim algaeIntake = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 0.028, EndEffectorConstants.algaeIntakeGearReduction),
        DCMotor.getNeoVortex(1)
    );

    private final DCMotorSim algaeWrist = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 0.177, EndEffectorConstants.algaeWristGearReduction),
        DCMotor.getNeoVortex(1)
    );
    
    
    private double coralIntakeAppliedVolts;
    private double algaeIntakeAppliedVolts;
    private double algaeWristAppliedVolts;

    @Override
    public void updateInputs(EndEffectorIOInputs inputs) {

        coralIntake.setInputVoltage(coralIntakeAppliedVolts);
        coralIntake.update(0.02);

        algaeIntake.setInputVoltage(algaeIntakeAppliedVolts);
        algaeIntake.update(0.02);

        algaeWrist.setInputVoltage(algaeWristAppliedVolts);
        algaeWrist.update(0.02);

        inputs.coralIntakePosition = coralIntake.getAngularPositionRad();
        inputs.coralIntakeVelocity = coralIntake.getAngularVelocityRadPerSec();
        inputs.coralIntakeVoltage = coralIntakeAppliedVolts;
        inputs.coralIntakeCurrent = coralIntake.getCurrentDrawAmps();

        inputs.algaeIntakePosition = algaeIntake.getAngularPositionRad();
        inputs.algaeIntakeVelocity = algaeIntake.getAngularVelocityRadPerSec();
        inputs.algaeIntakeVoltage = algaeIntakeAppliedVolts;
        inputs.algaeIntakeCurrent = algaeIntake.getCurrentDrawAmps();

        inputs.algaeWristPosition = algaeWrist.getAngularPositionRad();
        inputs.algaeWristVelocity = algaeWrist.getAngularVelocityRadPerSec();
        inputs.algaeWristVoltage = algaeWristAppliedVolts;
        inputs.algaeWristCurrent = algaeWrist.getCurrentDrawAmps();

        
    }

    @Override
    public void setCoralIntakeVoltage (double volts) {

        coralIntakeAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
    }

    @Override
    public void setAlgaeIntakeVoltage (double volts) {

        algaeIntakeAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
    }

    @Override
    public void setAlgaeWristVoltage (double volts) {

        algaeWristAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
    }

}