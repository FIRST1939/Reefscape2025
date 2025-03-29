package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.LEDPattern.GradientType;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import java.util.Map;
import java.util.function.DoubleSupplier;

public class LEDs extends SubsystemBase {

    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;
    private AddressableLEDBufferView m_bunny_full;
    private AddressableLEDBufferView m_bunny_1;
    private AddressableLEDBufferView m_bunny_2;
    private AddressableLEDBufferView m_bunny_3;
    private AddressableLEDBufferView m_bunny_4;
    private LEDPattern pattern;
   
    private Timer bhTimer = new Timer();
    public LEDs() {
        bhTimer.start();
        this.ledStrip = new AddressableLED(LEDConstants.port);
        this.ledBuffer = new AddressableLEDBuffer(LEDConstants.leds);

        this.ledStrip.setLength(LEDConstants.leds);
        this.ledStrip.start();
        m_bunny_full = ledBuffer.createView(0, 40);
         m_bunny_1 = ledBuffer.createView(0, 10);
         m_bunny_2 = ledBuffer.createView(11, 20);
         m_bunny_3 = ledBuffer.createView(21, 30);
         m_bunny_4 = ledBuffer.createView(31, 40);
         this.setRainbowPattern();
         this.BunnyHop();
    }

    @Override
    public void periodic () {

        if (pattern!=null){
        pattern.applyTo(ledBuffer);
        }

        double timerMod = (bhTimer.get()*300)%1000;
        LEDPattern green = LEDPattern.solid(Color.kGreen);
        LEDPattern black = LEDPattern.solid(Color.kBlack);
        black.applyTo(m_bunny_full);
        if (timerMod> 0 && timerMod < 250)
        {

            green.applyTo(m_bunny_1);
        }
        if (timerMod> 250 && timerMod < 500)
        {
            green.applyTo(m_bunny_2);
        }
        if (timerMod> 500 && timerMod < 750)
        {
            green.applyTo(m_bunny_3);
        }
       
        if (timerMod> 750 && timerMod < 1000)
        {
            green.applyTo(m_bunny_4);
        }
        ledStrip.setData(ledBuffer);
    }

    public void BunnyHop()
    {
        
  
       // pattern.applyTo(m_bunny_1);
       // pattern.applyTo(m_bunny_2)
     //   LEDPattern base = LEDPattern.gradient(GradientType.kDiscontinuous, Color.kGreen, Color.kBlack);
 //pattern = base.scrollAtRelativeSpeed(Percent.per(Second).of(25));
//LEDPattern absolute = base.scrollAtAbsoluteSpeed(Centimeters.per(Second).of(12.5), ledSpacing);

// Apply the LED pattern to the data buffer
//pattern.applyTo(ledBuffer);

// Write the data to the LED strip
   ledStrip.setData(ledBuffer);
    }
    public void setAlliance () {

        Color allianceColor;
        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
            if (alliance.get() == Alliance.Red) {

                allianceColor = Color.kRed;
            } else {

                allianceColor = Color.kBlue;
            }
        } else { //No alliance.. Flash white
            allianceColor = Color.kWhite;
        }

        LEDPattern base = LEDPattern.solid(allianceColor);
        pattern = base.blink(Seconds.of(1.0));
    }

    public void setCoralProcessing() {

        LEDPattern base = LEDPattern.solid(Color.kYellow);
        pattern = base.blink(Seconds.of(0.5));
    }

    public void setAlgaeProcessing() {

        LEDPattern base = LEDPattern.solid(Color.kSeaGreen);
        pattern = base.blink(Seconds.of(0.5));
    }

    public void setCoralHolding() {

        pattern = LEDPattern.solid(Color.kYellow);
    }

    public void setAlgaeHolding() {

        pattern = LEDPattern.solid(Color.kSeaGreen);
    }

    public void setRainbowPattern() {
        LEDPattern base = LEDPattern.rainbow(255, 128);
        
        pattern = base.scrollAtRelativeSpeed(Percent.per(Second).of(25));
    }

    public void setElevatorProgress(DoubleSupplier elevatorHeight, double targetHeight) {

        LEDPattern base = LEDPattern.solid(Color.kPink);
        pattern = base.mask(LEDPattern.progressMaskLayer(() -> elevatorHeight.getAsDouble() / targetHeight));
    }
}
