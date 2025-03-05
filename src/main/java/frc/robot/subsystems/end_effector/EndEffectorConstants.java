package frc.robot.subsystems.end_effector;

public class EndEffectorConstants {

    public static final int coralIntakeCAN = 51;
    public static final double coralIntakeGearReduction = (36.0 / 56.0);
    public static final boolean coralIntakeInverted = true;
    public static final int coralIntakeCurrentLimit = 80; // TODO Coral Intake Current Limit

    public static final int algaeIntakeCAN = 52;
    public static final double algaeIntakeGearReduction = (36.0 / 56.0);
    public static final int algaeIntakeCurrentLimit = 60; // TODO Algae Intake Current Limit

    public static final int algaeWristCAN = 53;
    public static final double algaeWristGearReduction = (1.0 / 125.0) * (24.0 / 42.0);
    public static final int algaeWristCurrentLimit = 40; // TODO Algae Wrist Current Limit
}
