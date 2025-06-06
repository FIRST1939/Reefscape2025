// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.net.PortForwarder;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;
import org.opencv.core.Core;

import edu.wpi.first.wpilibj.DriverStation;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.pathfinding.Pathfinding;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.swerve.LocalADStarAK;
import frc.robot.util.BuildConstants;
import frc.robot.util.RobotGoals;

public class Robot extends LoggedRobot {
    
    private final LoggedDashboardChooser<Command> autoSelector;
    private final LoggedDashboardChooser<Integer> postSelector;
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
    
            // TODO Replay
            setUseTiming(false);
            Logger.addDataReceiver(new NT4Publisher());
            //Logger.setReplaySource(new WPILOGReader(logPath));
            //Logger.addDataReceiver(new WPILOGWriter(LogFileUtil.addPathSuffix(logPath, "_sim")));
        }
    
        Logger.start();
          
        this.robotContainer = new RobotContainer(isReal());
        this.autoSelector = new LoggedDashboardChooser<>("Auto Selector", AutoBuilder.buildAutoChooser());

        SendableChooser<Integer> postChooser = new SendableChooser<>();
        postChooser.setDefaultOption("A", 0);
        postChooser.addOption("B", 1);
        postChooser.addOption("C", 2);
        postChooser.addOption("D", 3);
        postChooser.addOption("E", 4);
        postChooser.addOption("F", 5);
        postChooser.addOption("G", 6);
        postChooser.addOption("H", 7);
        postChooser.addOption("I", 8);
        postChooser.addOption("J", 9);
        postChooser.addOption("K", 10);
        postChooser.addOption("L", 11);
        this.postSelector = new LoggedDashboardChooser<>("Initial Post Target", postChooser);
    }
    
    @Override
    public void robotInit () {

        for (int port = 5800; port <= 5809; port++) {
    
            PortForwarder.add(port, "limelight-left.local", port);
            PortForwarder.add(port + 10, "limelight-right.local", port);
        }

        Pathfinding.setPathfinder(new LocalADStarAK());
        PathfindingCommand.warmupCommand().schedule();

        if (RobotBase.isSimulation()) {

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        }

        RobotGoals.load();
    }

    @Override
    public void robotPeriodic() {

        CommandScheduler.getInstance().run();
        this.robotContainer.updateComponents();
    }

    @Override
    public void disabledInit () {

      if (DriverStation.isFMSAttached()) {

        this.robotContainer.leds.setAlliancePattern();
      } else {

        this.robotContainer.leds.setAlliancePattern();
      }
    }

    @Override
    public void disabledPeriodic () {}

    @Override
    public void disabledExit () {
    
        this.robotContainer.onEnable();
    }
    
    @Override
    public void autonomousInit () {
    
        this.autoCommand = this.autoSelector.get();
    
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

        RobotGoals.setManualIndex(this.postSelector.get());
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
