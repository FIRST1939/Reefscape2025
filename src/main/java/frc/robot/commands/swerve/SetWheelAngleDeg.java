// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.swerve;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.Swerve;
import swervelib.SwerveModule;

/**
 * An example command that uses an example subsystem.
 */
public class SetWheelAngleDeg extends Command
{

  private final Swerve swerve;
  private final double angle;

  /**
   * Used to drive a swerve robot in full field-centric mode.  vX and vY supply translation inputs, where x is
   * torwards/away from alliance wall and y is left/right. headingHorzontal and headingVertical are the Cartesian
   * coordinates from which the robot's angle will be derived— they will be converted to a polar angle, which the robot
   * will rotate to.
   *
   * @param swerve            The swerve drivebase subsystem.
   * @param vX                DoubleSupplier that supplies the x-translation joystick input.  Should be in the range -1
   *                          to 1 with deadband already accounted for.  Positive X is away from the alliance wall.
   * @param vY                DoubleSupplier that supplies the y-translation joystick input.  Should be in the range -1
   *                          to 1 with deadband already accounted for.  Positive Y is towards the left wall when
   *                          looking through the driver station glass.
   * @param headingHorizontal DoubleSupplier that supplies the horizontal component of the robot's heading angle. In the
   *                          robot coordinate system, this is along the same axis as vY. Should range from -1 to 1 with
   *                          no deadband.  Positive is towards the left wall when looking through the driver station
   *                          glass.
   * @param headingVertical   DoubleSupplier that supplies the vertical component of the robot's heading angle.  In the
   *                          robot coordinate system, this is along the same axis as vX.  Should range from -1 to 1
   *                          with no deadband. Positive is away from the alliance wall.
   */
  public SetWheelAngleDeg(Swerve swerve, Double angle)
  {
    this.swerve = swerve;
    this.angle=  angle;
    addRequirements(swerve);
  }

  @Override
  public void initialize()
  {
    swerve.getSwerveDrive().getModules();
     for (SwerveModule swerveModule : swerve.getSwerveDrive().getModules()) {

 //swerveModule.getAngleMotor().configurePIDWrapping(-180, 180);
 swerveModule.setAngle(angle);
}
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }


}
