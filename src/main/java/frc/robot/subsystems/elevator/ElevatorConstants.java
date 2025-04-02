package frc.robot.subsystems.elevator;

public class ElevatorConstants {
    
    public static final int leaderCAN = 31;
    public static final int followerCAN = 32;
    public static final int currentLimit = 100;
    public static final boolean leaderReversed = true;
    public static final boolean followerReversed = true;
    public static final int laserCAN = 33;

    public static final double kS_1 = 0.74;
    public static final double kG_1 = 0.25;
    public static final double kV_1 = 1.18;

    public static final double kS_2 = 0.435;
    public static final double kG_2 = 1.045;
    public static final double kV_2 = 3.20;

    public static final double kS_3 = 0.67;
    public static final double kG_3 = 1.42;
    public static final double kV_3 = 3.24;

    public static final double FIRST_ELEVATOR_TRANSITION = 0.625;
    public static final double SECOND_ELEVATOR_TRANSITION = 1.19;

    public static final double kP = 35.0;
    public static final double kD = 2.0;

    public static final double kI = 65.0;
    public static final double kIZ = 0.05;

    public static final double maxVelocity = 1.4;
    public static final double maxAcceleration = 5.0;
    public static final double maxVoltage = 6.0;
    public static final double tolerance = 0.02;
}
