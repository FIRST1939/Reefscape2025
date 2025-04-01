package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;

import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import java.util.function.DoubleSupplier;

public class LEDs extends SubsystemBase {

    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;

    private AddressableLEDBufferView m_bunny_right;
    private AddressableLEDBufferView m_bunny_left;

    private LEDPattern bunnyPattern;

    private AddressableLEDBufferView TopBar;

    private LEDPattern pattern;

    private Timer bhTimer = new Timer();

    public LEDs() {

        bhTimer.start();

        this.ledStrip = new AddressableLED(LEDConstants.port);
        this.ledBuffer = new AddressableLEDBuffer(LEDConstants.leds);

        this.ledStrip.setLength(LEDConstants.leds);
        this.ledStrip.start();

        // Full bunny strip
        m_bunny_right = ledBuffer.createView(0, 40);
        m_bunny_left = ledBuffer.createView(LEDConstants.leds - 40, LEDConstants.leds - 1).reversed();

        // Define bunny sections

        TopBar = ledBuffer.createView(40, LEDConstants.leds - 81);

        // Set initial pattern and effect
        this.setRainbowPattern();
        // this.setScannerPattern();
        // this.BunnyHopStart();
    }

    @Override
    public void periodic() {
        try {

            if (bunnyPattern != null) {
                LEDPattern black = LEDPattern.solid(Color.kBlack);
                black.applyTo(ledBuffer);
                bunnyPattern.applyTo(m_bunny_left);
                bunnyPattern.applyTo(m_bunny_right);
                if (pattern != null) {
                    pattern.applyTo(TopBar);
                }
            } else {
                if (pattern != null) {
                    pattern.applyTo(ledBuffer);
                }
            }

            ledStrip.setData(ledBuffer);
        }

        catch (Exception e) {
        }
    }

    public void setScannerPattern() {
        pattern = new ScannerPattern(getAllianceColor());
    }

    public void BunnyHopStart() {
        bunnyPattern = new BunnyHopPattern(Color.kGreen, 5, 6, 0, false);

    }

    public void BunnyHopStop() {
        bunnyPattern = null;

    }

    public void setAlliance() {

        Color allianceColor = getAllianceColor();
        LEDPattern base = LEDPattern.solid(allianceColor);
        pattern = base.blink(Seconds.of(1.0));
    }

    public Color getAllianceColor() {
        Color allianceColor;
        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
            if (alliance.get() == Alliance.Red) {

                allianceColor = Color.kRed;
            } else {

                allianceColor = Color.kBlue;
            }
        } else { // No alliance. green
            allianceColor = Color.kGreen;
        }
        return allianceColor;
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
