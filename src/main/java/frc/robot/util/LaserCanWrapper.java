package frc.robot.util;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import au.grapplerobotics.interfaces.LaserCanInterface.Measurement;
import au.grapplerobotics.interfaces.LaserCanInterface.RangingMode;
import au.grapplerobotics.interfaces.LaserCanInterface.RegionOfInterest;
import au.grapplerobotics.interfaces.LaserCanInterface.TimingBudget;
import edu.wpi.first.wpilibj.RobotBase;

public class LaserCanWrapper {

    private LaserCan laserCan;

    private RangingMode rangingMode = RangingMode.SHORT;
    private RegionOfInterest regionOfInterest = new RegionOfInterest(8, 8, 16, 16);
    private TimingBudget timingBudget = TimingBudget.TIMING_BUDGET_33MS;

    protected int status;
    protected int distance;
    protected int ambient;
    
    public LaserCanWrapper (int can_id) {

        if (RobotBase.isReal()) {

            this.laserCan = new LaserCan(can_id);
        }
    }

    public Measurement getMeasurement () {

        if (RobotBase.isReal()) {

            return this.laserCan.getMeasurement();
        } else {

            return new Measurement(
                this.status, 
                this.distance, 
                this.ambient, 
                this.rangingMode.equals(RangingMode.LONG), 
                this.timingBudget.asMilliseconds(), 
                this.regionOfInterest
            );
        }
    }

    public void setRangingMode (RangingMode mode) throws ConfigurationFailedException {

        if (RobotBase.isReal()) {

            this.laserCan.setRangingMode(mode);
        } else {

            this.rangingMode = mode;
        }
    }

    public void setRegionOfInterest (RegionOfInterest roi) throws ConfigurationFailedException {

        if (RobotBase.isReal()) {

            this.laserCan.setRegionOfInterest(roi);
        } else {

            this.regionOfInterest = roi;
        }
    }

    public void setTimingBudget (TimingBudget budget) throws ConfigurationFailedException {

        if (RobotBase.isReal()) {

            this.laserCan.setTimingBudget(budget);
        } else {

            this.timingBudget = budget;
        }
    }

    public LaserCanSim getSimulatedDevice () {

        if (!RobotBase.isSimulation()) {

            throw new IllegalStateException("Not running in simulation!");
        }

        return new LaserCanSim(this);
    }

    public static class LaserCanSim {

        private final LaserCanWrapper wrapper;

        private LaserCanSim (LaserCanWrapper wrapper) {

            this.wrapper = wrapper;
        }

        public void setStatus (int status) {

            this.wrapper.status = status;
        }

        public void setDistance (int distance) {

            this.wrapper.distance = distance;
        }

        public void setAmbient (int ambient) {

            this.wrapper.ambient = ambient;
        }
    }
}
