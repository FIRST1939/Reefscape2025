package frc.robot.commands.swerve;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ControllerConstants;
import frc.robot.subsystems.swerve.Swerve;
import swervelib.SwerveInputStream;

public class Drive extends Command {
    
    private final Swerve swerve;
    private SwerveInputStream activeInputStream;
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

        this.activeInputStream = driverInputStream;

        this.addRequirements(this.swerve);
    }

    @Override
    public void execute () {

        // TODO Heading Lock
        this.swerve.driveFieldOriented(this.activeInputStream.get());
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(this.activeInputStream.get(), this.swerve.getSwerveDrive().getOdometryHeading());
    }
}
