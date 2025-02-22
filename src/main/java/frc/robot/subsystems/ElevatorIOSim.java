package frc.robot.subsystems;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class ElevatorIOSim implements ElevatorIO {
 
   
    
    
    private final DCMotorSim elevatorMotorLeader = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 0.001, 1.0),
        DCMotor.getNeoVortex(1)
    ); //TODO Update sim constants
    //new SparkFlex(ElevatorConstants.leaderCAN, MotorType.kBrushless);
    private final DCMotorSim elevatorMotorFollower = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(DCMotor.getNeoVortex(1), 0.001, 1.0),
        DCMotor.getNeoVortex(1)
    ); //TODO Update sim constants
    //private final LaserCan LaserCanSensor = new LaserCan(ElevatorConstants.elevatorLaserCAN);

    private double leaderAppliedVolts = 0.0;
    private double followerAppliedVolts = 0.0;

    @Override
    
    public void updateInputs(ElevatorIOInputsAutoLogged inputs) {


        elevatorMotorLeader.setInputVoltage(leaderAppliedVolts);
        elevatorMotorLeader.update(0.02);
        
        elevatorMotorFollower.setInputVoltage(followerAppliedVolts);
        elevatorMotorFollower.update(0.02);
        inputs.elevatorPosition = 0.0;
        inputs.elevatorVelocity = 0.0;
        inputs.leaderVoltage = 0.0;
        inputs.leaderCurrent = 0.0;
        inputs.followerVoltage = 0.0;
        inputs.followerCurrent = 0.0;
        inputs.elevatorlaserDistance = 0.0;

    }


    @Override
    public  void move(double volts)
    {
        elevatorMotorLeader.setInputVoltage(volts);
        elevatorMotorFollower.setInputVoltage(-volts);
    }

    @Override
    public void setLeaderVoltage(double leaderVolts)
    {
        elevatorMotorLeader.setInputVoltage(leaderVolts);
    }

    @Override
    public void setFollowerVoltage(double followerVolts)
    {
        elevatorMotorFollower.setInputVoltage(followerVolts);
    }
}


