package frc.robot.subsystems.end_effector;

public class EndEffectorConstants {

    public static final int coralIntakeCAN = 77; // TODO Coral Intake CAN ID
    public static final double coralIntakeGearReduction = (36.0 / 56.0);
    public static final int coralIntakeCurrentLimit = 40; // TODO Coral Intake Current Limit

    public static final int algaeIntakeCAN = 52; // TODO Algae Intake CAN ID
    public static final double algaeIntakeGearReduction = (36.0 / 56.0);
    public static final int algaeIntakeCurrentLimit = 40; // TODO Algae Intake Current Limit

    public static final int algaeWristCAN = 70; // TODO Algae Wrist CAN ID
    public static final double algaeWristGearReduction = (1.0 / 125.0) * (24.0 / 42.0);
    public static final int algaeWristCurrentLimit = 40; // TODO Algae Wrist Current Limit

    public static final int coralIntakeBeambreakDIO = 1; // TODO Beambreak DIO Port
    public static final int algaeIntakeLaserCAN = 71; // TODO LaserCAN CAN ID
}
