package frc.robot.commands;



import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.funnel.Funnel;



public class FunnelMove extends Command{
    
  private double funnelspeed;
    
  private Funnel funnel;


    
      
    public FunnelMove(Funnel funnel, double funnelspeed) {
        
      this.funnelspeed = funnelspeed;
      this.funnel=funnel;
        

    }
        
@Override
public void initialize () 
{
   funnel.runVoltage(funnelspeed);  
}
@Override
public void end (boolean interrupted) {
  funnel.runVoltage(0);
}

}
