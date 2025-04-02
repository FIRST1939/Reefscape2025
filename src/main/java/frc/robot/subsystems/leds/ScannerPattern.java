package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.LEDReader;
import edu.wpi.first.wpilibj.LEDWriter;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.Timer;

public class ScannerPattern implements LEDPattern {
    private int currentIndex = 0;
    private int direction = 1;
    private final Color baseColor;
    private final Timer timer = new Timer();
    private final double updateInterval = 0.01; // seconds
    private double lastUpdateTime = 0;
    private final int trailLength = 8; // number of trailing LEDs

    public ScannerPattern(Color color) {
        this.baseColor = color;
        timer.start();
    }

    @Override
    public void applyTo(LEDReader reader, LEDWriter writer) {
        int length = reader.getLength();
        double now = timer.get();

        if (now - lastUpdateTime > updateInterval) {
            currentIndex += direction;

            if (currentIndex >= length) {
                currentIndex = length - 2;
                direction = -1;
            } else if (currentIndex < 0) {
                currentIndex = 1;
                direction = 1;
            }

            lastUpdateTime = now;
        }

        // Clear all LEDs
        for (int i = 0; i < length; i++) {
            writer.setLED(i, Color.kBlack);
        }

        // Set trail and head
        for (int i = 0; i <= trailLength; i++) {
            int index = currentIndex - (direction * i);
            if (index >= 0 && index < length) {
                double brightness = 1.0 - (i / (double)(trailLength + 1));
                writer.setLED(index, dimColor(baseColor, brightness));
            }
        }
    }

    private Color dimColor(Color color, double factor) {
        return new Color(
            color.red * factor,
            color.green * factor,
            color.blue * factor
        );
    }
}
