package frc.robot.commands.swerve;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.Swerve;
import frc.robot.util.ControllerConstants;
import swervelib.SwerveInputStream;

public class Drive extends Command {
    
    private final Swerve swerve;
    private SwerveInputStream driverInputStream;

    public Drive (Swerve swerve, DoubleSupplier vx, DoubleSupplier vy, DoubleSupplier omega) {

        this.swerve = swerve;

        // TODO Swerve Input Scaling (Cubed)
        // TODO Swerve Input Simulation 
        this.driverInputStream = new SwerveInputStream(this.swerve.getSwerveDrive(), vx, vy, omega)
            .deadband(ControllerConstants.SWERVE_DEADBAND)
            //.scaleTranslation(ControllerConstants.SWERVE_TRANSLATION_SCALING)
            .cubeTranslationControllerAxis(true)
            .allianceRelativeControl(true);

        this.addRequirements(this.swerve);
    }

    @Override
    public void execute () {

        this.swerve.driveFieldOriented(this.driverInputStream.get());
        // TODO Heading Lock
    }
}
