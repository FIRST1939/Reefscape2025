package frc.robot.subsystems.leds;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.LEDPattern;

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
    private AddressableLEDBufferView top_bar;

    private LEDPattern bunnyPattern;
    private LEDPattern topPattern;

    public LEDs() {

        this.ledStrip = new AddressableLED(LEDConstants.port);
        this.ledBuffer = new AddressableLEDBuffer(LEDConstants.leds);

        this.ledStrip.setLength(LEDConstants.leds);
        this.ledStrip.start();

        this.m_bunny_right = ledBuffer.createView(0, 39);
        this.m_bunny_left = ledBuffer.createView(LEDConstants.leds - 39, LEDConstants.leds - 1).reversed();
        this.top_bar = ledBuffer.createView(40, LEDConstants.leds - 40);
    }

    @Override
    public void periodic() {

        try {

            LEDPattern black = LEDPattern.solid(Color.kBlack);
            black.applyTo(ledBuffer);

            if (bunnyPattern != null) {

                bunnyPattern.applyTo(m_bunny_left);
                bunnyPattern.applyTo(m_bunny_right);
            }

            if (topPattern != null) {

                topPattern.applyTo(top_bar);
            }

            ledStrip.setData(ledBuffer);
        }

        catch (Exception e) {
        }
    }

    public void setAlliancePattern() {

        this.topPattern = new ScannerPattern(this.getAllianceColor());
        this.bunnyPattern = new BunnyHopPattern(this.getAllianceColor(), 6, 5, 0, false);
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
        } else {

            allianceColor = Color.kGreen;
        }

        return allianceColor;
    }

    public void setCoralProcessing() {

        LEDPattern base = LEDPattern.solid(Color.kYellow);
        LEDPattern pattern = base.blink(Seconds.of(0.5));

        this.bunnyPattern = pattern;
        this.topPattern = pattern;
    }

    public void setAlgaeProcessing() {

        LEDPattern base = LEDPattern.solid(Color.kSeaGreen);
        LEDPattern pattern = base.blink(Seconds.of(0.5));

        this.bunnyPattern = pattern;
        this.topPattern = pattern;
    }

    public void setCoralHolding() {

        LEDPattern pattern = LEDPattern.solid(Color.kYellow);

        this.bunnyPattern = pattern;
        this.topPattern = pattern;
    }

    public void setAlgaeHolding() {

        LEDPattern pattern = LEDPattern.solid(Color.kSeaGreen);
        this.bunnyPattern = pattern;
        this.topPattern = pattern;
    }

    public void setRainbowPattern() {

        LEDPattern base = LEDPattern.rainbow(255, 64);
        LEDPattern pattern = base.scrollAtRelativeSpeed(Percent.per(Second).of(65));

        this.bunnyPattern = pattern;
        this.topPattern = pattern;
    }

    public void setElevatorProgress(DoubleSupplier elevatorHeight, double targetHeight) {

        LEDPattern base = LEDPattern.solid(Color.kPink);
        topPattern = base.mask(LEDPattern.progressMaskLayer(() -> elevatorHeight.getAsDouble() / targetHeight));
    }

}
