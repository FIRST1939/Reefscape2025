// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.swerve.AlignToClosest;
import frc.robot.commands.swerve.Drive;
import frc.robot.commands.swerve.ZeroGyro;
import frc.robot.subsystems.swerve.Swerve;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.auto.TaxiBlue;
import frc.robot.commands.auto.TaxiRed;
import frc.robot.commands.elevator.SetpointElevator;
import frc.robot.commands.end_effector.ScoreCoral;
import frc.robot.commands.end_effector.LoadAlgae;
import frc.robot.commands.end_effector.PivotLoadAlgae;
import frc.robot.commands.end_effector.PivotWrist;
import frc.robot.commands.end_effector.ScoreAlgae;
import frc.robot.commands.elevator.ManualElevator;
import frc.robot.subsystems.elevator.Elevator;
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
    private final CommandXboxController operator = new CommandXboxController(1);

    private final Swerve swerve = new Swerve();
  private final Elevator elevator;
    private final EndEffector endEffector;
    private final Funnel funnel;

    public RobotContainer (boolean isReal) {

      if (isReal) {

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

        this.driver.rightBumper().whileTrue(new AlignToClosest(this.swerve, SetPointConstants.REEF_CORAL_POSITIONS));
        this.driver.rightTrigger().whileTrue(new AlignToClosest(this.swerve, SetPointConstants.REEF_ALGAE_POSITIONS));

        new Trigger(this.elevator::isManual).whileTrue(new ManualElevator(this.elevator, () -> -this.operator.getRightY() * 3.0));
        Trigger elevatorSetpoints = new Trigger(this.elevator::isManual).negate();

        elevatorSetpoints.and(this.operator.x()).onTrue(new SetpointElevator(this.elevator, -0.015));
        elevatorSetpoints.and(this.operator.a()).onTrue(new SetpointElevator(this.elevator, 0.55));
        elevatorSetpoints.and(this.operator.b()).onTrue(new SetpointElevator(this.elevator, 0.97));
        elevatorSetpoints.and(this.operator.y()).onTrue(new SetpointElevator(this.elevator, 1.60));

        elevatorSetpoints.and(this.operator.povUp()).onTrue(new SetpointElevator(this.elevator, 1.68));
        elevatorSetpoints.and(this.operator.povLeft()).onTrue(new SetpointElevator(this.elevator, 0.0)); //TODO Calculate Processer Height
        elevatorSetpoints.and(this.operator.povRight()).onTrue(new SetpointElevator(this.elevator, 0.76));
        elevatorSetpoints.and(this.operator.povDown()).onTrue(new SetpointElevator(this.elevator, 0.37));

        this.operator.rightBumper().toggleOnTrue(new LoadCoral(funnel, endEffector, 10.0, -15.0, this.operator));
        this.operator.rightTrigger().toggleOnTrue(new ScoreCoral(endEffector, SetPointConstants.CORAL_OUTTAKE_SPEED));

        this.operator.leftBumper().whileTrue(new PivotLoadAlgae(this.endEffector, 150.0, 2.0));
        this.operator.leftTrigger().whileTrue(new ScoreAlgae(this.endEffector, -3.0));

        this.operator.back().whileTrue(new GroundIntakeAlgae(this.elevator, this.endEffector));
        this.operator.start().whileTrue(new Purge(this.endEffector, this.funnel));

        this.driver.a().whileTrue(new AlignToClosest(this.swerve, new Pose2d[] { //Left Far Barge
             new Pose2d(new Translation2d(7.547, 5.439), Rotation2d.fromDegrees(0)),
             new Pose2d(new Translation2d(9.989, 2.461), Rotation2d.fromDegrees(180))
        }));

        this.driver.b().whileTrue(new AlignToClosest(this.swerve, new Pose2d[] { //Left Close Barge
            new Pose2d(new Translation2d(7.747, 5.439), Rotation2d.fromDegrees(180)),
            new Pose2d(new Translation2d(9.789, 2.461), Rotation2d.fromDegrees(180))
        }));

        this.driver.x().whileTrue(new AlignToClosest(this.swerve, new Pose2d[] { //Right Far Barge
            new Pose2d(new Translation2d(7.547, 6.439), Rotation2d.fromDegrees(0)),
            new Pose2d(new Translation2d(9.989, 1.461), Rotation2d.fromDegrees(180))
        }));
       
        this.driver.y().whileTrue(new AlignToClosest(this.swerve, new Pose2d[] { //Right Close Barge
            new Pose2d(new Translation2d(7.547, 6.439), Rotation2d.fromDegrees(0)),
            new Pose2d(new Translation2d(9.789, 1.461), Rotation2d.fromDegrees(180))
        }));
       
        //new Trigger(this.endEffector::isManual).whileTrue(new ManualEndEffector(this.endEffector, () -> this.operator.getRightX() * 3.0, ));

        /**
        new Trigger(this.funnel::isManual).and(this.driver.leftBumper()).whileTrue(new ManualFunnel(this.funnel, SetPointConstants.FUNNEL_OUTTAKE_SPEED));
        new Trigger(this.funnel::isManual).and(this.driver.rightBumper()).whileTrue(new ManualFunnel(this.funnel, SetPointConstants.FUNNEL_INTAKE_SPEED));
        new Trigger(this.funnel::isManual).negate().and(this.driver.rightBumper()).onTrue(new AutomaticFunnel(this.funnel, SetPointConstants.FUNNEL_INTAKE_SPEED, SetPointConstants.FUNNEL_STUCK_SPEED)); // TODO Funnel Intake Deadline
        */

        /**
        this.endEffector.setDefaultCommand(
            new ManualEndEffector(endEffector, () -> (0.34 + (0.017 * 50.0)), () -> 0.0, () -> 0.0)
        );
        */
    }

    public Command getAutonomousCommand() {

        var alliance = DriverStation.getAlliance();
        if (alliance.get() == DriverStation.Alliance.Red) {

            return Commands.sequence(
                new AlignToClosest(this.swerve, SetPointConstants.REEF_CORAL_POSITIONS).withTimeout(5.0),
                new SetpointElevator(this.elevator, 1.60),
                new WaitCommand(1.0),
                new ScoreCoral(endEffector, SetPointConstants.CORAL_OUTTAKE_SPEED),
                new WaitCommand(1.0),
                new SetpointElevator(this.elevator, -0.015)
            );
        } else {

            return Commands.sequence(
                new AlignToClosest(this.swerve, SetPointConstants.REEF_CORAL_POSITIONS).withTimeout(5.0),
                new SetpointElevator(this.elevator, 1.60),
                new WaitCommand(1.0),
                new ScoreCoral(endEffector, SetPointConstants.CORAL_OUTTAKE_SPEED),
                new WaitCommand(1.0),
                new SetpointElevator(this.elevator, -0.015)
            );
        }
    }

    public void onEnable () {

        this.elevator.runVoltage(0.0);
    }
}
