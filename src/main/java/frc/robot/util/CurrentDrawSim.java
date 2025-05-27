package frc.robot.util;

import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;

public class CurrentDrawSim {
    
    private static double elevator;
    private static double endEffector;

    public static void setElevatorCurrentDraw (double amps) {

        elevator = amps;
    }

    public static void setEndEffectorCurrentDraw (double amps) {

        endEffector = amps;
    }

    public static void updateBatteryLoad () {

        RoboRioSim.setVInVoltage(
            BatterySim.calculateDefaultBatteryLoadedVoltage(
                elevator,
                endEffector
            )
        );
    }
}
