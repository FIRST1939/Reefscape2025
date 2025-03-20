package main.java.frc.robot.subsystems;

;

public class LEDs extends SubsystemBase {
    
    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;

    public LEDs(int port, int leds) {
        this.ledStrip = new AddressableLED(port);
        this.ledBuffer = new AddressableLEDBuffer(leds);

        this.ledStrip.setLength(leds);
        this.ledStrip.start();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        LEDPattern pattern = LEDPattern.progressMaskLayer(() -> m_elevator.getHeight() / m_elevator.getMaxHeight());
        pattern.applyTo(ledBuffer);

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
        purple.applyTo(ledBuffer); // Fixed to use purple instead of red
        ledStrip.setData(ledBuffer);
    }

    public void setSolidPatternA1() {
        LEDPattern teal = LEDPattern.solid(Color.kTeal);
        teal.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setSolidPatternA2() {
        LEDPattern purple = LEDPattern.solid(Color.kPurple);
        purple.applyTo(ledBuffer); // Fixed to use purple instead of red
        ledStrip.setData(ledBuffer);
    }

    public void setRainbowPattern() {
        // Add functionality for rainbow pattern here
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