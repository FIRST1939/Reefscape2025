package frc.robot.end_effector;

public class EndEffectorConstants {
    public static final int coralIntakeCAN = 38; //TODO coral Intake CAN ID AFTER ROBOT IS WIRED
    public static final double coralIntakeGearReduction = (36.0/56.0);
    public static final int coralIntakecurrentLimit = 40;
    public static final int algaeIntakeCAN = 39; //TODO algae Intake CAN ID AFTER ROBOT IS WIRED
    public static final double algaeIntakeGearReduction = (36.0/56.0);
    public static final int algaeIntakecurrentLimit = 40; 
    public static final int algaeWristCAN = 39; //TODO algae Wrist CAN ID AFTER ROBOT IS WIRED
    public static final double algaeWristGearReduction = (42.0/42.0);
    public static final int algaeWristcurrentLimit = 40; 
    public static final int coralIntakeBeamBreakCAN = 40; //TODO Beam Break CAN ID AFTER ROBOT IS WIRED
    public static final int AlgaeIntakeLaserCAN = 40; //TODO Laser CAN CAN ID AFTER ROBOT IS WIRED
    public static final int coralIntakeBeamBreakDIO = 1; //TODO Beam Break DIO Port ID AFTER ROBOT IS WIRED
    public static double laserDistance = -1.0; //Default measurement indicating no measurement yet
}
