// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
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
    }

    private void configureBindings() {
        // this.driverTwo.leftBumper().whileTrue(new CoralScore(endEffector));
        // this.driverTwo.leftTrigger().whileTrue(new AlgaeOuttake(endEffector, SetPointConstants.ALGAE_OUTTAKE_SPEED));
        // this.driverTwo.rightTrigger().whileTrue(new AlgaeIntake(endEffector, SetPointConstants.ALGAE_INTAKE_SPEED));
        //this.driverTwo.povUp().whileTrue(new ElevatorMove(elevator, () -> this.driverTwo.getRightY() * SetPointConstants.ELEVATOR_MAXIMUM_MANUAL_SPEED));
        //this.driverTwo.povDown().whileTrue(new ElevatorMove(elevator, () -> this.driverTwo.getRightY() * -(SetPointConstants.ELEVATOR_MAXIMUM_MANUAL_SPEED)));
        //this.driverTwo.a().onTrue(new ElevatorMove(elevator, ()-> .5 ));
        //this.driverTwo.b().onTrue(new FunnelMove(funnel, SetPointConstants.FUNNEL_INTAKE_SPEED));
        //this.driverTwo.leftTrigger().whileTrue(new AlgaeMove(endEffector, SetPointConstants.ALGAE_OUTTAKE_SPEED));
        //this.driverTwo.rightTrigger().whileTrue(new AlgaeMove(endEffector, SetPointConstants.ALGAE_INTAKE_SPEED));

        //this.elevator.setDefaultCommand(new ElevatorMove(this.elevator, () -> -this.driver.getRightY() * 3.0));
        
        this.driver.a().onTrue(new SetpointElevator(this.elevator, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L1));
        this.driver.b().onTrue(new SetpointElevator(this.elevator, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L2));
        this.driver.x().onTrue(new SetpointElevator(this.elevator, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L3));
        this.driver.y().onTrue(new SetpointElevator(this.elevator, SetPointConstants.CORAL_OUTTAKE_HEIGHT_L4));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    public void onEnable () {

        this.elevator.runVoltage(0.0);
    }
}
