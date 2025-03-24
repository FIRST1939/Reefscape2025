package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;

import static edu.wpi.first.units.Units.Seconds;

import java.util.function.DoubleSupplier;

public class LEDs extends SubsystemBase {
    
    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;
    private LEDPattern pattern; 

    public LEDs() {

        this.ledStrip = new AddressableLED(LEDConstants.port);
        this.ledBuffer = new AddressableLEDBuffer(LEDConstants.leds);

        this.ledStrip.setLength(LEDConstants.leds);
        this.ledStrip.start();

        setRainbowPattern();
    }

    @Override
    public void periodic () {
        
        pattern.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

    public void setCoralProcessing () {

        LEDPattern base = LEDPattern.solid(Color.kYellow);
        pattern = base.blink(Seconds.of(0.5));
    }

    public void setAlgaeProcessing () {
       
        LEDPattern base = LEDPattern.solid(Color.kSeaGreen);
        pattern = base.blink(Seconds.of(0.5));
    }

    public void setCoralHolding(){

        pattern = LEDPattern.solid(Color.kYellow);
    }

    public void setAlgaeHolding(){
   
        pattern = LEDPattern.solid(Color.kSeaGreen);
    }
    
    public void setRainbowPattern() {

        pattern = LEDPattern.rainbow(255, 128);
    }

    public void setElevatorProgress (DoubleSupplier elevatorHeight, double targetHeight) {

        LEDPattern base = LEDPattern.solid(Color.kPink);
        pattern = base.mask(LEDPattern.progressMaskLayer(() -> elevatorHeight.getAsDouble() / targetHeight));
    }
}