// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.ConfirmAlliance;
import frc.robot.commands.swerve.Drive;
import frc.robot.commands.swerve.SetWheelAngleDeg;
import frc.robot.commands.swerve.ZeroGyro;
import frc.robot.subsystems.swerve.Swerve;

public class RobotContainer {

    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);

    private final Swerve swerve = new Swerve();

    public RobotContainer () {

        configureBindings();

        new ConfirmAlliance().andThen(new ZeroGyro(this.swerve)).schedule();
    }

    private void configureBindings () {

        this.swerve.setDefaultCommand(
            new Drive(
                swerve, 
                () -> -driver.getLeftY(), 
                () -> -driver.getLeftX(), 
                () -> driver.getRightX()
            )
        );
      // SetWheelAngleDeg a = new SetWheelAngleDeg(swerve, 90.0);
         driver.a().onTrue(new SetWheelAngleDeg(swerve, 90.0));
          driver.x().onTrue(new SetWheelAngleDeg(swerve, 0.0));
          driver.b().onTrue(new SetWheelAngleDeg(swerve, 180.0));
          driver.y().onTrue(new SetWheelAngleDeg(swerve, 270.0));
    }
}
