package frc.robot.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.funnel.Funnel;
import frc.robot.subsystems.end_effector.EndEffector;


public class FunnelIntake extends Command{
    private double funnelspeed;
    private Funnel funnel;
      public FunnelIntake(EndEffector endEffector, double funnelspeed) {
        this.funnelspeed = funnelspeed;
        
    }

@Override
public void initialize () 
{
   funnel.runVoltage(funnelspeed);

    
}
}
