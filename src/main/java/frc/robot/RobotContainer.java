// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.swerve.Drive;
import frc.robot.commands.swerve.ZeroGyro;

import org.ironmaple.simulation.SimulatedArena;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ConfirmAlliance;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.ElevatorConstants;
import frc.robot.subsystems.elevator.ElevatorIOSim;
import frc.robot.subsystems.elevator.ElevatorIOVortex;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.end_effector.EndEffectorConstants;
import frc.robot.subsystems.end_effector.EndEffectorIOSim;
import frc.robot.subsystems.end_effector.EndEffectorIOVortex;
import frc.robot.subsystems.funnel.Funnel;
import frc.robot.subsystems.funnel.FunnelConstants;
import frc.robot.subsystems.funnel.FunnelIOSim;
import frc.robot.subsystems.funnel.FunnelIOVortex;
import frc.robot.subsystems.swerve.Swerve;
import frc.robot.util.CurrentDrawSim;
import frc.robot.util.GamePieceSim;


public class RobotContainer {

    private final CommandXboxController driver = new CommandXboxController(0);

    private final Swerve swerve = new Swerve();
    private final Elevator elevator;
    private final EndEffector endEffector;
    private final Funnel funnel;

    public RobotContainer () {

      if (RobotBase.isReal()) {

            this.endEffector = new EndEffector(new EndEffectorIOVortex());
            this.elevator = new Elevator(new ElevatorIOVortex());
            this.funnel = new Funnel(new FunnelIOVortex());
        } else {

            this.endEffector = new EndEffector(new EndEffectorIOSim());
            this.elevator = new Elevator(new ElevatorIOSim());
            this.funnel = new Funnel(new FunnelIOSim());

            new CurrentDrawSim();

            GamePieceSim gamePieceSim = new GamePieceSim(
                () -> this.swerve.getSimulationPose(),
                () -> this.swerve.getSimulationFieldVelocity(),
                () -> this.endEffector.getCoralIntakeVelocity() * EndEffectorConstants.CORAL_INTAKE_DIAMETER * Math.PI,
                () -> this.elevator.getHeight(),
                () -> this.funnel.getVelocity() * FunnelConstants.PITCH_DIAMETER * Math.PI
            );

            SmartDashboard.putData("Reset Field", Commands.runOnce(() -> SimulatedArena.getInstance().resetFieldForAuto()).ignoringDisable(true));
            SmartDashboard.putData("Load Coral", Commands.runOnce(() -> gamePieceSim.loadCoral(), gamePieceSim).ignoringDisable(true));
            SmartDashboard.putData("Preload Coral", Commands.runOnce(() -> gamePieceSim.preloadCoral(), gamePieceSim).ignoringDisable(true));
            SmartDashboard.putData("Remove Coral", Commands.runOnce(() -> gamePieceSim.removeCoral(), gamePieceSim).ignoringDisable(true));
        }

        this.configureBindings();
        new ConfirmAlliance().andThen(new ZeroGyro(this.swerve)).schedule();
    }

    private void configureBindings () {

        this.swerve.setDefaultCommand(
            new Drive(
                swerve, 
                () -> -driver.getLeftY(), 
                () -> -driver.getLeftX(), 
                () -> -driver.getRightX()
            )
        );
    }
    
    public void updateComponents () {

        if (RobotBase.isReal()) {

            Logger.recordOutput("Visualization/Swerve Pose", this.swerve.getPose());
        } else {

            Logger.recordOutput("Visualization/Swerve Pose", this.swerve.getSimulationPose());
        }

        Logger.recordOutput("Visualization/Component Poses", new Pose3d[] {
            new Pose3d(0.0, 0.0, this.elevator.getHeight(), new Rotation3d()),
            new Pose3d(0.0, 0.0, MathUtil.clamp(this.elevator.getHeight(), ElevatorConstants.FIRST_STAGE_TRANSITION, ElevatorConstants.SECOND_STAGE_TRANSITION), new Rotation3d()),
            new Pose3d(0.0, 0.0, Math.max(this.elevator.getHeight(), ElevatorConstants.FIRST_STAGE_TRANSITION), new Rotation3d()),
            new Pose3d(0.0, 0.0, this.elevator.getHeight(), new Rotation3d()),
            new Pose3d(0.249, -0.114, 0.337 + this.elevator.getHeight(), new Rotation3d(0.0, Units.rotationsToRadians(this.endEffector.getAlgaeWristPosition()), 0.0)),
        });
    }
}
