// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.ConfirmAlliance;
import frc.robot.commands.swerve.Drive;
import frc.robot.commands.swerve.ZeroGyro;
import frc.robot.subsystems.swerve.Swerve;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
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

        this.swerve.setDefaultCommand(
            new Drive(
                swerve, 
                () -> -driver.getLeftY(), 
                () -> -driver.getLeftX(), 
                () -> -driver.getRightX()
            )
        );

        // this.driverTwo.leftBumper().whileTrue(new CoralScore(endEffector));
        // this.driverTwo.leftTrigger().whileTrue(new AlgaeOuttake(endEffector, SetPointConstants.ALGAE_OUTTAKE_SPEED));
        // this.driverTwo.rightTrigger().whileTrue(new AlgaeIntake(endEffector, SetPointConstants.ALGAE_INTAKE_SPEED));
        //this.driverTwo.povUp().whileTrue(new ElevatorMove(elevator, () -> this.driverTwo.getRightY() * SetPointConstants.ELEVATOR_MAXIMUM_MANUAL_SPEED));
        //this.driverTwo.povDown().whileTrue(new ElevatorMove(elevator, () -> this.driverTwo.getRightY() * -(SetPointConstants.ELEVATOR_MAXIMUM_MANUAL_SPEED)));
        //this.driverTwo.a().onTrue(new ElevatorMove(elevator, ()-> .5 ));
        //this.driverTwo.leftTrigger().whileTrue(new AlgaeMove(endEffector, SetPointConstants.ALGAE_OUTTAKE_SPEED));
        //this.driverTwo.rightTrigger().whileTrue(new AlgaeMove(endEffector, SetPointConstants.ALGAE_INTAKE_SPEED));

        SmartDashboard.putData(CommandScheduler.getInstance());

        new Trigger(this.elevator::isManual).whileTrue(new ManualElevator(this.elevator, () -> -this.driver.getRightY() * 3.0));
        Trigger elevatorSetpoints = new Trigger(this.elevator::isManual).negate();

        elevatorSetpoints.and(this.driver.x()).onTrue(new SetpointElevator(this.elevator, -0.015));
        elevatorSetpoints.and(this.driver.a()).onTrue(new SetpointElevator(this.elevator, 0.55));
        elevatorSetpoints.and(this.driver.b()).onTrue(new SetpointElevator(this.elevator, 0.97));
        elevatorSetpoints.and(this.driver.y()).onTrue(new SetpointElevator(this.elevator, 1.60));

        /**
        new Trigger(this.funnel::isManual).and(this.driver.leftBumper()).whileTrue(new ManualFunnel(this.funnel, SetPointConstants.FUNNEL_OUTTAKE_SPEED));
        new Trigger(this.funnel::isManual).and(this.driver.rightBumper()).whileTrue(new ManualFunnel(this.funnel, SetPointConstants.FUNNEL_INTAKE_SPEED));
        new Trigger(this.funnel::isManual).negate().and(this.driver.rightBumper()).onTrue(new AutomaticFunnel(this.funnel, SetPointConstants.FUNNEL_INTAKE_SPEED, SetPointConstants.FUNNEL_STUCK_SPEED)); // TODO Funnel Intake Deadline
        */

        this.driver.rightBumper().whileTrue(
            new LoadCoral(funnel, endEffector, 25.0, -5.0)
        );

        this.driver.rightTrigger().whileTrue(
            new ManualEndEffector(endEffector, () -> 2.5, () -> 0.0, () -> 0.0)
        );

        /**
        this.endEffector.setDefaultCommand(
            new ManualEndEffector(endEffector, () -> (0.34 + (0.017 * 50.0)), () -> 0.0, () -> 0.0)
        );
        */

        /**
        this.endEffector.setDefaultCommand(
            new ManualEndEffector(
                this.endEffector, 
                () -> this.driver.getLeftX() * 3.0, 
                //() -> (this.driver.getRightTriggerAxis() - this.driver.getLeftTriggerAxis()) * 3.0, 
                () -> 0.0,
                () -> (this.driver.getRightTriggerAxis() - this.driver.getLeftTriggerAxis()) * 4.0
            )
        );
        */
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    public void onEnable () {

        this.elevator.runVoltage(0.0);
    }
}
