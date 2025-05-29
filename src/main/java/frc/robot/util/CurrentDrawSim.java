package frc.robot.util;

import static edu.wpi.first.units.Units.Amps;

import org.ironmaple.simulation.motorsims.SimulatedBattery;

public class CurrentDrawSim {
    
    private static double endEffector;
    private static double elevator;
    private static double funnel;

    public CurrentDrawSim () {

        SimulatedBattery.addElectricalAppliances(() -> Amps.of(endEffector));
        SimulatedBattery.addElectricalAppliances(() -> Amps.of(elevator));
        SimulatedBattery.addElectricalAppliances(() -> Amps.of(funnel));
    }

    public static void setEndEffectorCurrentDraw (double amps) {

        endEffector = amps;
    }

    public static void setElevatorCurrentDraw (double amps) {

        elevator = amps;
    }

    public static void setFunnelCurrentDraw (double amps) {

        funnel = amps;
    }
}
