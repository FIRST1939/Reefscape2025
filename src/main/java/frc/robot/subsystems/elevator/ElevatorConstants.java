package frc.robot.subsystems.elevator;

public class ElevatorConstants {
    
    public static final int leaderCAN = 31;
    public static final int followerCAN = 32;
    public static final int currentLimit = 100;
    public static final boolean leaderReversed = true;
    public static final boolean followerReversed = true;
    public static final int laserCAN = 5; //TODO Elevator LaserCAN CAN ID

    public static final double kS = 0.475;
    public static final double kG = 0.755;
    public static final double kV = 1.35;

    public static final double kP = 39.0;
    public static final double kI = 0.0;
    public static final double kD = 1.0;

    public static final double maxVelocity = 1.4;
    public static final double maxAcceleration = 5.0;
    public static final double tolerance = 0.02;
}
