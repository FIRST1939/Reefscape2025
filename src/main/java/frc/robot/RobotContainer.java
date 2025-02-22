// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.*;

public class RobotContainer {
  final CommandXboxController driverOne = new CommandXboxController(0);
  final CommandXboxController driverTwo = new CommandXboxController(0);

  public RobotContainer() {
    
    configureBindings();
  }

  private void configureBindings() {
    this.driverTwo.leftBumper().whileTrue(new elevatorMoveToHeight2(SetPointConstants.coralIntakeHeight));
    this.driverTwo.leftTrigger().whileTrue(new algaeOuttake2(SetPointConstants.algaeOuttakeSpeed));
    this.driverTwo.rightTrigger().whileTrue(new algaeIntake2(SetPointConstants.algaeIntakeSpeed));
    this.driverTwo.a().whileTrue(new elevatorMoveToHeight2(SetPointConstants.coralOuttakeHeightL1));
    this.driverTwo.b().whileTrue(new elevatorMoveToHeight2(SetPointConstants.coralOuttakeHeightL2));
    this.driverTwo.x().whileTrue(new elevatorMoveToHeight2(SetPointConstants.coralOuttakeHeightL3));
    this.driverTwo.y().whileTrue(new elevatorMoveToHeight2(SetPointConstants.coralOuttakeHeightL4));
    this.driverTwo.povUp().whileTrue(new elevatorMove2(SetPointConstants.elevatorManualUpSpeed));
    this.driverTwo.povDown().whileTrue(new elevatorMove2(SetPointConstants.elevatorManualDownSpeed));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
