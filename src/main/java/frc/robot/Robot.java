// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.pathfinding.Pathfinding;

import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.swerve.LocalADStarAK;

public class Robot extends TimedRobot {
    
    private final RobotContainer robotContainer;
    private final SendableChooser<Command> autoSelector;
    private Command autoCommand;

    public Robot () {

        robotContainer = new RobotContainer();
        autoSelector = AutoBuilder.buildAutoChooser();

        SmartDashboard.putData("Auto Selector", autoSelector);
    }

    @Override
    public void robotInit () {

        // TODO Limelight Networking
        for (int port = 5800; port <= 5809; port++) {

            PortForwarder.add(port, "limelight-left.local", port);
            PortForwarder.add(port + 10, "limelight-right.local", port);
        }

        Pathfinding.setPathfinder(new LocalADStarAK());
    }

    @Override
    public void robotPeriodic() {

        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit () {}

    @Override
    public void disabledPeriodic () {}

    @Override
    public void disabledExit () {}

    @Override
    public void autonomousInit () {

        autoCommand = autoSelector.getSelected();

        if (autoCommand != null) {

            autoCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic () {}

    @Override
    public void autonomousExit () {}

    @Override
    public void teleopInit () {

        if (autoCommand != null) {

            autoCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic () {}

    @Override
    public void teleopExit () {}

    @Override
    public void testInit () {

        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic () {}

    @Override
    public void testExit () {}
}
