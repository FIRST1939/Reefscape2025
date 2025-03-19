package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RumbleController extends Command {
    
    private final CommandXboxController controller;
    private final RumbleType rumbleType;

    public RumbleController (CommandXboxController controller, RumbleType rumbleType) {

        this.controller = controller;
        this.rumbleType = rumbleType;
    }

    @Override
    public void initialize () {

        this.controller.setRumble(this.rumbleType, 0.5);
    }

    @Override
    public void end (boolean interrupted) {

        this.controller.setRumble(this.rumbleType, 0.0);
    }
}
