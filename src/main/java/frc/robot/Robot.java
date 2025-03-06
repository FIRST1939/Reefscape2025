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

import org.littletonrobotics.junction.LogFileUtil;
import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGReader;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
//import frc.robot.subsystems.swerve.LocalADStarAK;

public class Robot extends LoggedRobot {
    
    //private final SendableChooser<Command> autoSelector;
    private final RobotContainer robotContainer;
    private Command autoCommand;

    public Robot () {

      Logger.recordMetadata("ProjectName", BuildConstants.MAVEN_NAME);
    Logger.recordMetadata("BuildDate", BuildConstants.BUILD_DATE);
    Logger.recordMetadata("GitSHA", BuildConstants.GIT_SHA);
    Logger.recordMetadata("GitDate", BuildConstants.GIT_DATE);
    Logger.recordMetadata("GitBranch", BuildConstants.GIT_BRANCH);

    if (isReal()) {

      Logger.addDataReceiver(new WPILOGWriter());
      Logger.addDataReceiver(new NT4Publisher());
      new PowerDistribution(1, ModuleType.kRev);
    } else {

      setUseTiming(false);
      String logPath = LogFileUtil.findReplayLog();
      Logger.addDataReceiver(new NT4Publisher());
      System.out.println("test");
      //Logger.setReplaySource(new WPILOGReader(logPath));
      //Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
    }

    Logger.start();
      
        this.robotContainer = new RobotContainer(this.isReal());
//        this.autoSelector = AutoBuilder.buildAutoChooser();

  //      SmartDashboard.putData("Auto Selector", this.autoSelector);
    }

    @Override
    public void robotInit () {

        // TODO Limelight Networking
        for (int port = 5800; port <= 5809; port++) {

            PortForwarder.add(port, "limelight-left.local", port);
            PortForwarder.add(port + 10, "limelight-right.local", port);
        }
      //  Pathfinding.setPathfinder(new LocalADStarAK());
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
    public void disabledExit () {
    
      this.robotContainer.onEnable();
    }

    @Override
    public void autonomousInit () {

        //this.autoCommand = this.autoSelector.getSelected();

        if (this.autoCommand != null) {

            this.autoCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic () {}

    @Override
    public void autonomousExit () {}

    @Override
    public void teleopInit () {

        if (this.autoCommand != null) {

            this.autoCommand.cancel();
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
