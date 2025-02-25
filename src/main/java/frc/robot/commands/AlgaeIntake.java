package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.end_effector.EndEffector;

public class AlgaeIntake extends Command{

    private EndEffector endEffector;

    private double algaeintakespeed;

    public AlgaeIntake(EndEffector endEffector, double algaeintakespeed) {

        this.endEffector = endEffector;
        this.addRequirements(this.endEffector);
    }

    public AlgaeIntake(double algaeintakespeed) {
        //TODO Auto-generated constructor stub
    }

    @Override
    public void initialize () {

        endEffector.runVoltage(0, algaeintakespeed, 0);
    }
}
