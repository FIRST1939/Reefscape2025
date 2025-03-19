\

public class LEDs extends SubsystemBase {
    
    public class AddressableLEDs extends SubsystemBase {
    
    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;

    public LEDs (int port, int leds) {

        this.ledStrip = new AddressableLED(port);
        this.ledBuffer = new AddressableLEDBuffer(leds);

        this.ledStrip.setLength(leds);
        this.ledStrip.start();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

        // Update the buffer with the pattern
        pattern.applyTo(m_ledBuffer);

        // Set the LEDs
        ledBuffer.setData(m_ledBuffer);
    }

    // Colors include Color.kRed, Color.kOrange, Color.kYellow, Color.kGreen
    LEDPattern pattern = LEDPattern.progressMaskLayer(() -> m_elevator.getHeight() / m_elevator.getMaxHeight());
    pattern.applyTo(m_ledBuffer);
    m_led.setData(m_ledBuffer);
    }
public void setSolidPatternC1(){
    
LEDPattern green = LEDPattern.solid(Color.kGreen);
green.applyTo(m_ledBuffer);
m_led.setData(m_ledBuffer);
    }

    public void setSolidPatternC2() {
    
LEDPattern purple = LEDPattern.solid(Color.kPurple);
red.applyTo(m_ledBuffer);
m_led.setData(m_ledBuffer);
    }
    public void setSolidPatternA1() {
    
        LEDPattern teal = LEDPattern.solid(Color.kTeal);
        teal.applyTo(m_ledBuffer);
        m_led.setData(m_ledBuffer);
    }

    public void setSolidPatternA2() {
    
        LEDPattern purple = LEDPattern.solid(Color.kPurple);
        red.applyTo(m_ledBuffer);
        m_led.setData(m_ledBuffer);
}

public void setRainbowPattern(){}
  private final LEDPattern m_rainbow = LEDPattern.rainbow(255, 128);

  private static final Distance kLedSpacing = Meters.of(1 / 120.0);
  private final LEDPattern m_scrollingRainbow =
      m_rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), kLedSpacing);

    public void setYellowProgressMask(){
        LEDPattern pattern = LEDPattern.progressMaskLayer(() -> m_elevator.getHeight() / m_elevator.getMaxHeight());
        pattern.applyTo(m_ledBuffer);
        m_led.setData(m_ledBuffer);
    }
       
    public void setRedProgressMask(){
            LEDPattern pattern = LEDPattern.progressMaskLayer(() -> m_elevator.getHeight() / m_elevator.getMaxHeight());
            pattern.applyTo(m_ledBuffer);
            m_led.setData(m_ledBuffer);}
    
    
    public void seBlueAndGreenFlamesPattern(){
    LEDPattern steps = LEDPattern.steps(Map.of(0, Color.kGreen, 0.5, Color.kBlue));
steps.applyTo(m_ledBuffer);

// Write the data to the LED strip
m_led.setData(m_ledBuffer);
    }
    