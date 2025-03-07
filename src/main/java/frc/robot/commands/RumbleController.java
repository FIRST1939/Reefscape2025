package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RumbleController extends Command {
    
    private final CommandXboxController controller;

    public RumbleController (CommandXboxController controller) {

        this.controller = controller;
    }

    @Override
    public void initialize () {

        this.controller.setRumble(RumbleType.kRightRumble, 0.5);
    }

    @Override
    public void end (boolean interrupted) {

        this.controller.setRumble(RumbleType.kRightRumble, 0.0);
    }
}
