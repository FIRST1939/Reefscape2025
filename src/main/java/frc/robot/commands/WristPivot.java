package frc.robot.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.end_effector.EndEffector;

public class WristPivot extends Command{
    private double wristspeed;
    private EndEffector endEffector;
    public WristPivot(EndEffector endEffector, double wristspeed) {
        this.wristspeed = wristspeed;
        
    }

@Override
public void initialize () 
{
    endEffector.runVoltage(0,0,wristspeed);
}

    
}
    


    


