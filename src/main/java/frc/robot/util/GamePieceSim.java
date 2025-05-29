package frc.robot.util;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

import java.util.List;
import java.util.function.Supplier;

import org.ironmaple.simulation.SimulatedArena;
import org.ironmaple.simulation.seasonspecific.reefscape2025.ReefscapeCoralOnFly;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GamePieceSim extends SubsystemBase {
    
    private final Supplier<Pose2d> robotPoseSupplier;
    private final Supplier<ChassisSpeeds> chassisSpeedsSupplier;
    private final Supplier<Double> coralIntakeVelocitySupplier;
    private final Supplier<Double> elevatorHeightSupplier;
    private final Supplier<Double> funnelVelocitySupplier;

    private CoralStatus coralStatus = null;
    private Transform3d coralTransform;
    private double fallingVelocity;
    private double fallingGravity;

    private static boolean coralBeambreak = true;

    public GamePieceSim (Supplier<Pose2d> robotPoseSupplier, Supplier<ChassisSpeeds> chassisSpeedsSupplier, Supplier<Double> coralIntakeVelocitySupplier, Supplier<Double> elevatorHeightSupplier, Supplier<Double> funnelVelocitySupplier) {

        this.robotPoseSupplier = robotPoseSupplier;
        this.chassisSpeedsSupplier = chassisSpeedsSupplier;
        this.coralIntakeVelocitySupplier = coralIntakeVelocitySupplier;
        this.elevatorHeightSupplier = elevatorHeightSupplier;
        this.funnelVelocitySupplier = funnelVelocitySupplier;
    }

    private enum CoralStatus {
        FUNNELING,
        INDEXING,
        FALLING,
        STUCK
    }

    @Override
    public void periodic () {

        if (this.coralStatus == CoralStatus.FUNNELING) {

            if (this.funnelVelocitySupplier.get() > 0.0) {

                if (Math.abs(this.elevatorHeightSupplier.get() - 0.139) < 0.014) {

                    this.coralStatus = CoralStatus.INDEXING;
                } else if (this.elevatorHeightSupplier.get() > 0.432) {

                    this.coralStatus = CoralStatus.FALLING;
                }
            }
        }

        if (this.coralStatus == CoralStatus.INDEXING) {

            if (this.coralTransform.getX() >= 0.014 && this.coralTransform.getX() <= 0.264) {

                coralBeambreak = false;
            } else {

                coralBeambreak = true;
            }

            if (this.coralTransform.getX() > 0.264) {

                this.coralStatus = null;

                SimulatedArena.getInstance().addGamePieceProjectile(
                    new ReefscapeCoralOnFly(
                        this.robotPoseSupplier.get().getTranslation(), 
                        new Translation2d(0.264, 0.0), 
                        this.chassisSpeedsSupplier.get(), 
                        this.robotPoseSupplier.get().getRotation(), 
                        Meters.of(this.coralTransform.getZ() + (this.elevatorHeightSupplier.get() - 0.139)), 
                        MetersPerSecond.of(this.coralIntakeVelocitySupplier.get()), 
                        Degrees.of(-30.0)
                    )
                );
            } else if (this.coralTransform.getX() > 0.130) {
    
                this.coralTransform = this.project(this.coralTransform, this.coralIntakeVelocitySupplier.get() * 0.02, new Rotation3d(0.0, Units.degreesToRadians(30.0), 0.0));
            } else {

                this.coralTransform = this.project(this.coralTransform, this.funnelVelocitySupplier.get() * 0.02, new Rotation3d(0.0, Units.degreesToRadians(30.0), 0.0));
            }
        } else {

            coralBeambreak = true;
        }
        
        if (this.coralStatus == CoralStatus.FALLING) {

            if (this.coralTransform.getX() <= 0.260) {

                this.fallingVelocity = this.funnelVelocitySupplier.get();
                this.fallingGravity = 0.0;
            }

            if (this.coralTransform.getZ() > 0.178) {

                this.coralTransform = this.project(this.coralTransform, this.fallingVelocity * 0.02, new Rotation3d(0.0, Units.degreesToRadians(30.0), 0.0));
                this.coralTransform = this.project(this.coralTransform, this.fallingGravity * 0.02, new Rotation3d(0.0, Units.degreesToRadians(90.0), 0.0));
                this.fallingGravity += (9.8 * 0.02);
            } else {

                this.coralStatus = CoralStatus.STUCK;
                this.coralTransform = new Transform3d(new Translation3d(0.216, 0.0, 0.076), new Rotation3d(0.0, 0.0, Units.degreesToRadians(90.0)));
            }
        }

        List<Pose3d> coral = SimulatedArena.getInstance().getGamePiecesByType("Coral");

        if (this.coralStatus != null) {
            
            Transform3d transform = this.coralTransform;

            if (this.coralStatus == CoralStatus.INDEXING) {

                Translation3d elevatorTranslation = new Translation3d(0.0, 0.0, this.elevatorHeightSupplier.get() - 0.139);
                transform = transform.plus(new Transform3d(elevatorTranslation, new Rotation3d()));
            }

            coral.add(new Pose3d(this.robotPoseSupplier.get()).plus(transform));
        }

        Logger.recordOutput("Visualization/Coral", coral.toArray(new Pose3d[0]));
        Logger.recordOutput("Visualization/Algae", SimulatedArena.getInstance().getGamePiecesArrayByType("Algae"));
    }

    public void loadCoral () {

        if (this.coralStatus == null) {

            this.coralStatus = CoralStatus.FUNNELING;
            this.coralTransform = new Transform3d(new Translation3d(-0.145, 0.0, 0.495), new Rotation3d(0.0, Units.degreesToRadians(30.0), 0.0));
        }
    }

    public void preloadCoral () {

        this.coralStatus = CoralStatus.INDEXING;
        this.coralTransform = new Transform3d(new Translation3d(0.214, 0.0, 0.269), new Rotation3d(0.0, Units.degreesToRadians(30.0), 0.0));
    }

    public void removeCoral () {

        this.coralStatus = null;
    }

    public static boolean getCoralBeambreak () {

        return coralBeambreak;
    }

    private Transform3d project (Transform3d transform, double distance, Rotation3d direction) {

        Translation3d displacement = new Translation3d(distance, direction);
        return new Transform3d(transform.getTranslation().plus(displacement), transform.getRotation());
    }
}
