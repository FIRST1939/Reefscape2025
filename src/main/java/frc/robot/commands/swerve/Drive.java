package frc.robot.commands.swerve;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ControllerConstants;
import frc.robot.subsystems.swerve.Swerve;
import swervelib.SwerveInputStream;

public class Drive extends Command {
    
    private final Swerve swerve;
    private SwerveInputStream activeInputStream;
    private SwerveInputStream driverInputStream;
    private SwerveInputStream visionInputStream;

    public Drive (Swerve swerve, DoubleSupplier vx, DoubleSupplier vy, DoubleSupplier omega, Trigger drive, Trigger vision) {

        this.swerve = swerve;

        // TODO Swerve Input Scaling (Cubed)
        // TODO Swerve Input Simulation 
        this.driverInputStream = new SwerveInputStream(this.swerve.getSwerveDrive(), vx, vy, omega)
            .deadband(ControllerConstants.SWERVE_DEADBAND)
            //.scaleTranslation(ControllerConstants.SWERVE_TRANSLATION_SCALING)
            .cubeTranslationControllerAxis(true)
            .allianceRelativeControl(true);

        drive.onTrue(Commands.runOnce(() -> this.activeInputStream = driverInputStream));
        vision.onTrue(Commands.runOnce(() -> this.activeInputStream = visionInputStream));

        this.activeInputStream = driverInputStream;

        this.addRequirements(this.swerve);
    }

    @Override
    public void execute () {

        if (this.activeInputStream == this.visionInputStream) {

            double xDistance = 12.35 - this.swerve.getPose().getX();
            double yDistance = 3.00 - this.swerve.getPose().getY();

            this.swerve.driveToPose(new Translation2d(xDistance, yDistance).times(2));
        } else {

            this.swerve.driveFieldOriented(this.activeInputStream.get());
            ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(this.activeInputStream.get(), this.swerve.getSwerveDrive().getOdometryHeading());
        }

        // TODO Heading Lock
    }
}
