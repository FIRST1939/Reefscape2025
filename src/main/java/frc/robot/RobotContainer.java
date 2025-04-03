// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.swerve.AlignToReef;
import frc.robot.commands.swerve.Drive;
import frc.robot.commands.swerve.ZeroGyro;
import frc.robot.subsystems.swerve.Swerve;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.events.EventTrigger;

import frc.robot.util.RobotGoals;
import frc.robot.util.SetPointConstants;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ConfirmAlliance;
import frc.robot.commands.GroundIntakeAlgae;
import frc.robot.commands.IntakeCoral;
import frc.robot.commands.RumbleController;
import frc.robot.commands.auto.SetElevatorTarget;
import frc.robot.commands.elevator.ElevatorToHeight;
import frc.robot.commands.end_effector.ScoreCoral;
import frc.robot.commands.end_effector.IntakeAlgae;
import frc.robot.commands.end_effector.ScoreAlgae;
import frc.robot.commands.elevator.ManualElevator;
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
import frc.robot.subsystems.leds.LEDs;


public class RobotContainer {

    private final CommandXboxController driver = new CommandXboxController(0);
    private final CommandXboxController operator = new CommandXboxController(1);

    private final Swerve swerve = new Swerve();
    public final LEDs leds = new LEDs();

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
        this.configureNamedCommands();
        new ConfirmAlliance().andThen(new ZeroGyro(this.swerve)).schedule();
    }

    private void configureBindings () {

        SmartDashboard.putData("Commands", CommandScheduler.getInstance());
        SmartDashboard.putBoolean("Reef Aligned", false);

        this.swerve.setDefaultCommand(
            new Drive(
                swerve, 
                () -> -driver.getLeftY(), 
                () -> -driver.getLeftX(), 
                () -> -driver.getRightX()
            )
        );

        this.driver.rightBumper().whileTrue(new AlignToReef(() -> RobotGoals.getTargetCoralPath()));
        this.driver.leftBumper().whileTrue(new AlignToReef(() -> RobotGoals.getTargetAlgaePath()));

        this.driver.rightTrigger().onTrue(Commands.runOnce(() -> RobotGoals.transformTargetCW()));
        this.driver.leftTrigger().onTrue(Commands.runOnce(() -> RobotGoals.transformTargetCCW()));

        new Trigger(this.elevator::isManual).whileTrue(new ManualElevator(this.elevator, () -> -this.operator.getRightY() * SetPointConstants.ELEVATOR_MAXIMUM_MANUAL_SPEED));
        Trigger elevatorSetpoints = new Trigger(this.elevator::isManual).negate();

        elevatorSetpoints.and(this.operator.x()).onTrue(new ElevatorToHeight(this.elevator, this.leds, SetPointConstants.CORAL_INTAKE_HEIGHT));
        elevatorSetpoints.and(this.operator.a()).onTrue(new ElevatorToHeight(this.elevator, this.leds, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L2));
        elevatorSetpoints.and(this.operator.b()).onTrue(new ElevatorToHeight(this.elevator, this.leds, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L3));
        elevatorSetpoints.and(this.operator.y()).onTrue(new ElevatorToHeight(this.elevator, this.leds, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L4));

        elevatorSetpoints.and(this.operator.povDown()).onTrue(new ElevatorToHeight(this.elevator, this.leds, SetPointConstants.ALGAE_INTAKE_LOW_HEIGHT));
        elevatorSetpoints.and(this.operator.povRight()).onTrue(new ElevatorToHeight(this.elevator, this.leds, SetPointConstants.ALGAE_INTAKE_HIGH_HEIGHT));
        elevatorSetpoints.and(this.operator.povLeft()).onTrue(new ElevatorToHeight(this.elevator, this.leds, SetPointConstants.ALGAE_OUTTAKE_PROCESSOR_HEIGHT));
        elevatorSetpoints.and(this.operator.povUp()).onTrue(new ElevatorToHeight(this.elevator, this.leds, SetPointConstants.ALGAE_OUTTAKE_NET_HEIGHT));

        this.operator.rightBumper().toggleOnTrue(
            new IntakeCoral(this.endEffector, this.funnel, this.leds).andThen(
                new RumbleController(this.operator, RumbleType.kRightRumble).withTimeout(0.5)
            )
        );

        this.operator.rightTrigger().toggleOnTrue(
            new ScoreCoral(this.endEffector,leds).andThen(
                new RumbleController(this.driver, RumbleType.kBothRumble).withTimeout(0.5)
            )
        );

        this.operator.back().whileTrue(new GroundIntakeAlgae(this.elevator, this.endEffector, this.leds));
        this.operator.leftBumper().whileTrue(new IntakeAlgae(this.endEffector, this.leds, SetPointConstants.ALGAE_INTAKE_REEF_WRIST_POSITION));
        this.operator.leftTrigger().whileTrue(new ScoreAlgae(this.endEffector, this.leds));

        new Trigger(() -> DriverStation.isFMSAttached()).onTrue(Commands.runOnce(() -> this.leds.setAlliancePattern(), this.leds));
    }

    public void configureNamedCommands () {
        
        new EventTrigger("IntakeCoral").onTrue(new IntakeCoral(this.endEffector, this.funnel, this.leds));
        NamedCommands.registerCommand("ScoreCoral", new ScoreCoral(this.endEffector, this.leds));

        new EventTrigger("ElevatorToFunnel").onTrue(new SetElevatorTarget(this.elevator, SetPointConstants.CORAL_INTAKE_HEIGHT));
        new EventTrigger("ElevatorToL2").onTrue(new SetElevatorTarget(this.elevator, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L2));
        new EventTrigger("ElevatorToL3").onTrue(new SetElevatorTarget(this.elevator, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L3));
        new EventTrigger("ElevatorToL4").onTrue(new SetElevatorTarget(this.elevator, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L4));
        new EventTrigger("WaitForElevator").onTrue(new WaitUntilCommand(() -> this.elevator.atGoal()));
    }
    
    public void updateComponents () {

        Logger.recordOutput("Swerve_Pose", this.swerve.getPose());

        Logger.recordOutput("Component_Poses", new Pose3d[] {
            new Pose3d(0.0, 0.0, this.elevator.getHeight(), new Rotation3d()),
            new Pose3d(0.0, 0.0, MathUtil.clamp(this.elevator.getHeight(), ElevatorConstants.FIRST_ELEVATOR_TRANSITION, ElevatorConstants.SECOND_ELEVATOR_TRANSITION), new Rotation3d()),
            new Pose3d(0.0, 0.0, Math.max(this.elevator.getHeight(), ElevatorConstants.FIRST_ELEVATOR_TRANSITION), new Rotation3d()),
            new Pose3d(0.0, 0.0, this.elevator.getHeight(), new Rotation3d()),
            new Pose3d(0.0, 0.0, this.elevator.getHeight(), new Rotation3d()),
        });
    }

    public void onEnable () {

        this.elevator.runVoltage(0.0);
        this.elevator.setGoal(this.elevator.getHeight());
    }
}
