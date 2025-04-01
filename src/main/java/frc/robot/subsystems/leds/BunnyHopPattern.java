package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.LEDReader;
import edu.wpi.first.wpilibj.LEDWriter;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.Timer;

public class BunnyHopPattern implements LEDPattern {
    
    private final Color hopColor;
    private final int numHops;
    private final int hopLength;
    private final int startIndex;
    private final boolean reversed;
    private final Timer timer = new Timer();

    public BunnyHopPattern(Color color, int numHops, int hopLength, int startIndex, boolean reversed) {
        this.hopColor = color;
        this.numHops = numHops;
        this.hopLength = hopLength;
        this.startIndex = startIndex;
        this.reversed = reversed;
        timer.start();
    }
    

    @Override
    public void applyTo(LEDReader reader, LEDWriter writer) {
        double time = timer.get();
        int hopIndex = (int) (time % numHops);

        // Clear all hops
        for (int i = 0; i < numHops; i++) {
            int start = getSectionStart(i);
            for (int j = 0; j < hopLength; j++) {
                int index = start + j;
                if (index >= 0 && index < reader.getLength()) {
                    writer.setLED(index, Color.kBlack);
                }
            }
        }

        // Light the active hop
        int activeStart = getSectionStart(hopIndex);
        for (int j = 0; j < hopLength; j++) {
            int index = activeStart + j;
            if (index >= 0 && index < reader.getLength()) {
                writer.setLED(index, hopColor);
            }
        }
    }

    private int getSectionStart(int hopIndex) {
        if (reversed) {
            return startIndex - (hopIndex * hopLength);
        } else {
            return startIndex + (hopIndex * hopLength);
        }
    }
}
