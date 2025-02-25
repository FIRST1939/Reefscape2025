package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;

public class ConfirmAlliance extends Command {
    
    @Override
    public boolean isFinished () {

        return DriverStation.getAlliance().isPresent();
    }

    @Override
    public boolean runsWhenDisabled () {

        return true;
    }
}
