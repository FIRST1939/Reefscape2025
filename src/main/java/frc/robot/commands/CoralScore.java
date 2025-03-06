package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.SetPointConstants;
import frc.robot.subsystems.end_effector.EndEffector;

public class CoralScore extends Command{

    private EndEffector endEffector;
    
    private double coralscorespeed;
    
    public CoralScore(EndEffector endEffector, double coralscorespeed)
    {
        this.endEffector=endEffector;
        this.addRequirements(this.endEffector);
        //this.addCommands(
            //new ElevatorMoveToHeight(SetPointConstants.CORAL_INTAKE_HEIGHT),
            //new CoralMove(endEffector, 15)
            //new FunnelIntake(endEffector, 0)
        //);
    }
    @Override
    public void execute () {

        endEffector.runVoltage(coralscorespeed, 0, 0);
    }   
}