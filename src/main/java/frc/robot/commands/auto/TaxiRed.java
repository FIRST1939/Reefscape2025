package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.Swerve;

public class TaxiRed extends Command {
    
    private final Swerve swerve;

    public TaxiRed (Swerve swerve) {

        this.swerve = swerve;
        this.addRequirements(this.swerve);
    }

    @Override
    public void execute () {

        this.swerve.driveToPose(new Translation2d(1.5, 0.0), 0);
    }
}
