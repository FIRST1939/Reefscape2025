// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.*;
import frc.robot.funnel.Funnel;
import frc.robot.funnel.FunnelIOSim;
import frc.robot.funnel.FunnelIOVortex;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.ElevatorIOSim;
import frc.robot.subsystems.elevator.ElevatorIOVortex;
import frc.robot.subsystems.end_effector.EndEffector;
import frc.robot.subsystems.end_effector.EndEffectorIOSim;
import frc.robot.subsystems.end_effector.EndEffectorIOVortex;

public class RobotContainer {
  final CommandXboxController driverOne = new CommandXboxController(0);
  final CommandXboxController driverTwo = new CommandXboxController(1);

   private final EndEffector endEffector;

  private final Funnel funnel;
  private final Elevator elevator;
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

    configureBindings();
  }

  private void configureBindings() {
    // this.driverTwo.leftBumper().whileTrue(new CoralScore(endEffector));
    // this.driverTwo.leftTrigger().whileTrue(new AlgaeOuttake(endEffector, SetPointConstants.ALGAE_OUTTAKE_SPEED));
    // this.driverTwo.rightTrigger().whileTrue(new AlgaeIntake(endEffector, SetPointConstants.ALGAE_INTAKE_SPEED));
    // this.driverTwo.a().onTrue(new ElevatorMoveToHeight(SetPointConstants.CORAL_OUTTAKE_HEIGHT_L1));
    // this.driverTwo.b().onTrue(new ElevatorMoveToHeight(SetPointConstants.CORAL_OUTTAKE_HEIGHT_L2));
    // this.driverTwo.x().onTrue(new ElevatorMoveToHeight(SetPointConstants.CORAL_OUTTAKE_HEIGHT_L3));
    // this.driverTwo.y().onTrue(new ElevatorMoveToHeight(SetPointConstants.CORAL_OUTTAKE_HEIGHT_L4));
     //this.driverTwo.povUp().whileTrue(new ElevatorMove(elevator, () -> this.driverTwo.getRightY() * SetPointConstants.ELEVATOR_MAXIMUM_MANUAL_SPEED));
     //this.driverTwo.povDown().whileTrue(new ElevatorMove(elevator, () -> this.driverTwo.getRightY() * -(SetPointConstants.ELEVATOR_MAXIMUM_MANUAL_SPEED)));
    this.driverTwo.a().onTrue(new ElevatorMove(elevator, ()-> .5 ));
    this.driverTwo.b().onTrue(new FunnelMove(funnel, SetPointConstants.FUNNEL_INTAKE_SPEED));
    this.driverTwo.leftTrigger().whileTrue(new AlgaeMove(endEffector, SetPointConstants.ALGAE_OUTTAKE_SPEED));
    this.driverTwo.rightTrigger().whileTrue(new AlgaeMove(endEffector, SetPointConstants.ALGAE_INTAKE_SPEED));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
