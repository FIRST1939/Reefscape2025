package frc.robot.subsystems.elevator;

public class ElevatorConstants {
    
    public static final int leaderCAN = 31;
    public static final int followerCAN = 32;
    public static final int currentLimit = 100;
    public static final boolean leaderReversed = true;
    public static final boolean followerReversed = true;
    public static final int laserCAN = 5; //TODO Elevator LaserCAN CAN ID

    public static final double kS = 0.998;
    public static final double kG = 0.665;
    public static final double kV = 0.045;

    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double maxVelocity = 0.0;
    public static final double maxAcceleration = 0.0;
}
