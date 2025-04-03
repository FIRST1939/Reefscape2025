package frc.robot.subsystems;

import java.nio.file.Path;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ReefDisplay extends SubsystemBase {

    private final CvSource source;
    private Mat currentImage;

    public ReefDisplay() {

        this.source = CameraServer.putVideo("Reef Display", 612, 792);

        Path fallbackPath = Filesystem.getDeployDirectory().toPath()
            .resolve("reef_positions")
            .resolve("0.jpg");

        this.currentImage = Imgcodecs.imread(fallbackPath.toString());
    }

    @Override
    public void periodic() {

        if (this.currentImage != null && !this.currentImage.empty()) {

            this.source.putFrame(this.currentImage);
        }
    }

    public void display(int index) {

        Path imagePath = Filesystem.getDeployDirectory().toPath()
            .resolve("reef_positions")
            .resolve(index + ".jpg");

        Mat newImage = Imgcodecs.imread(imagePath.toString());

        if (!newImage.empty()) {

            this.currentImage = newImage;
        }
    }
}
