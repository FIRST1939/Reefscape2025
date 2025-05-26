package frc.robot.util;

import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;

public class CurrentDrawSim {
    
    private static double elevator;

    public static void setElevatorCurrentDraw (double amps) {

        elevator = amps;
    }

    public static void updateBatteryLoad () {

        RoboRioSim.setVInVoltage(
            BatterySim.calculateDefaultBatteryLoadedVoltage(
                elevator
            )
        );
    }
}
