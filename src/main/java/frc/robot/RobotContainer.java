// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.swerve.Drive;
import frc.robot.commands.swerve.ZeroGyro;
import frc.robot.subsystems.swerve.Swerve;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.ConfirmAlliance;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.ElevatorConstants;
import frc.robot.subsystems.elevator.ElevatorIOSim;
import frc.robot.subsystems.elevator.ElevatorIOVortex;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.end_effector.EndEffectorIOSim;
import frc.robot.subsystems.end_effector.EndEffectorIOVortex;
import frc.robot.subsystems.funnel.Funnel;
import frc.robot.subsystems.funnel.FunnelIOSim;
import frc.robot.subsystems.funnel.FunnelIOVortex;


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
        }

        this.configureBindings();
        new ConfirmAlliance().andThen(new ZeroGyro(this.swerve)).schedule();
    }

    private void configureBindings () {

        SmartDashboard.putData("Commands", CommandScheduler.getInstance());

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

        Logger.recordOutput("Swerve_Pose", this.swerve.getPose());

        Logger.recordOutput("Component_Poses", new Pose3d[] {
            new Pose3d(0.0, 0.0, this.elevator.getHeight(), new Rotation3d()),
            new Pose3d(0.0, 0.0, MathUtil.clamp(this.elevator.getHeight(), ElevatorConstants.FIRST_STAGE_TRANSITION, ElevatorConstants.SECOND_STAGE_TRANSITION), new Rotation3d()),
            new Pose3d(0.0, 0.0, Math.max(this.elevator.getHeight(), ElevatorConstants.FIRST_STAGE_TRANSITION), new Rotation3d()),
            new Pose3d(0.0, 0.0, this.elevator.getHeight(), new Rotation3d()),
            new Pose3d(0.248739, -0.114171, 0.337435 + this.elevator.getHeight(), new Rotation3d(0.0, this.endEffector.getAlgaeWristPosition() * 2 * Math.PI, 0.0)),
        });
    }
}
