package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import java.util.function.DoubleSupplier;

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
    public void periodic () {
        
        pattern.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setSolidPatternCoralReadyToScore() {

        pattern = LEDPattern.solid(Color.kGreen);
    }

    public void setSolidPatternReefOuttaking() {
       
        pattern = LEDPattern.solid(Color.kPurple);
     
    }

    public void setSolidPatternAlgaeReadyToScore() {
        
        pattern = LEDPattern.solid(Color.kTeal);
    
    }

    public void setSolidPatternAlgaeReefOuttaking() {
     
        pattern = LEDPattern.solid(Color.kPurple);
      
    }

    public void setRainbowPattern() {

        pattern = LEDPattern.rainbow(255, 128);
    }

    public void setElevatorProgress (DoubleSupplier elevatorHeight, double targetHeight) {

        LEDPattern base = LEDPattern.solid(Color.kPink);
        pattern = base.mask(LEDPattern.progressMaskLayer(() -> elevatorHeight.getAsDouble() / targetHeight));
    }
}