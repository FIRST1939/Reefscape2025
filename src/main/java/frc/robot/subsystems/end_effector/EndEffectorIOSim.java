package frc.robot.subsystems.end_effector;

import com.revrobotics.sim.SparkFlexSim;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.util.CurrentDrawSim;

public class EndEffectorIOSim extends EndEffectorIOVortex {
 
    private final SparkFlexSim coralIntakeMotor = new SparkFlexSim(super.coralIntakeMotor, DCMotor.getNeoVortex(1));
    private final SparkFlexSim algaeIntakeMotor = new SparkFlexSim(super.algaeIntakeMotor, DCMotor.getNeoVortex(1));
    private final SparkFlexSim algaeWristMotor = new SparkFlexSim(super.algaeWristMotor, DCMotor.getNeoVortex(1));

    private final FlywheelSim coralIntake = new FlywheelSim(
        LinearSystemId.createFlywheelSystem(
            DCMotor.getNeoVortex(1), 
            0.017,
            (56.0 / 36.0)
        ), 
        DCMotor.getNeoVortex(1)
    );

    private final FlywheelSim algaeIntake = new FlywheelSim(
        LinearSystemId.createFlywheelSystem(
            DCMotor.getNeoVortex(1), 
            0.021,
            (68.0 / 24.0)
        ), 
        DCMotor.getNeoVortex(1)
    );

    private final SingleJointedArmSim algaeWrist = new SingleJointedArmSim(
        LinearSystemId.createSingleJointedArmSystem(
            DCMotor.getNeoVortex(1), 
            0.147, 
            (125.0 / 1.0) * (42.0 / 24.0)
        ),
        DCMotor.getNeoVortex(1), 
        (125.0 / 1.0) * (42.0 / 24.0), 
        0.25, 
        -Math.PI / 6, 
        Math.PI / 2, 
        true, 
        Math.PI / 2
    );

    @Override
    public void updateInputs(EndEffectorIOInputs inputs) {

        super.updateInputs(inputs);

        this.coralIntake.setInputVoltage(this.coralIntakeMotor.getAppliedOutput() * RoboRioSim.getVInVoltage());
        this.coralIntake.update(0.02);

        this.algaeIntake.setInputVoltage(this.algaeIntakeMotor.getAppliedOutput() * RoboRioSim.getVInVoltage());
        this.algaeIntake.update(0.02);

        this.algaeWrist.setInputVoltage(this.algaeWristMotor.getAppliedOutput() * RoboRioSim.getVInVoltage());
        this.algaeWrist.update(0.02);

        this.coralIntakeMotor.iterate(
            this.coralIntake.getAngularVelocityRPM() / 60.0,
            RoboRioSim.getVInVoltage(),
            0.02
        );
        
        this.algaeIntakeMotor.iterate(
            this.algaeIntake.getAngularVelocityRPM() / 60.0,
            RoboRioSim.getVInVoltage(),
            0.02
        );

        this.algaeWristMotor.iterate(
            -this.algaeWrist.getVelocityRadPerSec() / (2 * Math.PI),
            RoboRioSim.getVInVoltage(),
            0.02
        );

        CurrentDrawSim.setEndEffectorCurrentDraw(
            this.coralIntake.getCurrentDrawAmps() + 
            this.algaeIntake.getCurrentDrawAmps() +
            this.algaeWrist.getCurrentDrawAmps()
        );

        inputs.manual = super.manual.get();

        inputs.coralIntakePosition = this.coralIntakeMotor.getPosition();
        inputs.coralIntakeVelocity = this.coralIntakeMotor.getVelocity();
        inputs.coralIntakeVoltage = this.coralIntakeMotor.getAppliedOutput() * this.coralIntakeMotor.getBusVoltage();
        inputs.coralIntakeCurrent = this.coralIntakeMotor.getMotorCurrent();
        inputs.coralIntakeTemperature = 0.0;

        inputs.algaeIntakePosition = this.algaeIntakeMotor.getPosition();
        inputs.algaeIntakeVelocity = this.algaeIntakeMotor.getVelocity();
        inputs.algaeIntakeVoltage = this.algaeIntakeMotor.getAppliedOutput() * this.coralIntakeMotor.getBusVoltage();
        inputs.algaeIntakeCurrent = this.algaeIntakeMotor.getMotorCurrent();
        inputs.algaeIntakeTemperature = 0.0;

        inputs.algaeWristPosition = this.algaeWristMotor.getPosition();
        inputs.algaeWristVelocity = this.algaeWristMotor.getVelocity();
        inputs.algaeWristVoltage = this.algaeWristMotor.getAppliedOutput() * this.coralIntakeMotor.getBusVoltage();
        inputs.algaeWristCurrent = this.algaeWristMotor.getMotorCurrent();
        inputs.algaeWristTemperature = 0.0;

        inputs.coralBeambreak = true;
    }
}
