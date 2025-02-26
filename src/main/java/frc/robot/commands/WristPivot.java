package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.end_effector.EndEffector;

public class WristPivot extends Command {
    private double wristSpeed;
    private EndEffector endEffector;
        public WristPivot(EndEffector endEffector, double wristspeed) {
        
    }

    @Override
    public void end(boolean interrupted) {
        
        endEffector.runVoltage(0, 0, wristSpeed); 
    }

    @Override
    public boolean isFinished() {
        return false; 
    }
}


    


