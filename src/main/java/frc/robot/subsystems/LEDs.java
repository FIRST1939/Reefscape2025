package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import java.util.Map;

public class LEDs extends SubsystemBase {
    
    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;
    private LEDPattern pattern; 

    public LEDs(int port, int leds) {

        this.ledStrip = new AddressableLED(port);
        this.ledBuffer = new AddressableLEDBuffer(leds);

        this.ledStrip.setLength(leds);
        this.ledStrip.start();

        setRainbowPattern();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
       // LEDPattern pattern = LEDPattern.progressMaskLayer(() -> m_elevator.getHeight() / m_elevator.getMaxHeight());
       // pattern.applyTo(ledBuffer);

        // Set the LEDs
        ledStrip.setData(ledBuffer);
    }

    public void setSolidPatternC1() {
        LEDPattern green = LEDPattern.solid(Color.kGreen);
        green.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setSolidPatternC2() {
        LEDPattern purple = LEDPattern.solid(Color.kPurple);
        purple.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setSolidPatternA1() {
        LEDPattern teal = LEDPattern.solid(Color.kTeal);
        teal.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setSolidPatternA2() {
        LEDPattern purple = LEDPattern.solid(Color.kPurple);
        purple.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setRainbowPattern() {
        LEDPattern rainbow = LEDPattern.rainbow(255, 128); // Example
        rainbow.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setYellowProgressMask() {
        LEDPattern pattern = LEDPattern.progressMaskLayer(() -> m_elevator.getHeight() / m_elevator.getMaxHeight());
        pattern.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setRedProgressMask() {
        LEDPattern pattern = LEDPattern.progressMaskLayer(() -> m_elevator.getHeight() / m_elevator.getMaxHeight());
        pattern.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setBlueAndGreenFlamesPattern() {
        LEDPattern steps = LEDPattern.steps(Map.of(0, Color.kGreen, 0.5, Color.kBlue));
        steps.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }
}