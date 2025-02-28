package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.swerve.Swerve;

public class ZeroGyro extends InstantCommand {
    
    private final Swerve swerve;

    public ZeroGyro (Swerve swerve) {

        this.swerve = swerve;
        this.addRequirements(this.swerve);
    }

    @Override
    public void initialize () {

        this.swerve.zeroGyro();
    }

    @Override
    public boolean runsWhenDisabled () {

        return true;
    }
}
