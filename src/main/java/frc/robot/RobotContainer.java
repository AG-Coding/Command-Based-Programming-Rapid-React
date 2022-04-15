// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.commands.ShooterTeleop;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubSystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
 
  // Intiating All the subsystems
  public final DriveSubsystem m_drive = new DriveSubsystem();
  public final ShooterSubsystem m_shooter = new ShooterSubsystem();
  public final VisionSubsystem m_vision = new VisionSubsystem();
  public final IndexerSubsystem m_indexer = new IndexerSubsystem();
  public final IntakeSubSystem m_intake = new IntakeSubSystem();
  public final ClimberSubsystem m_climber = new ClimberSubsystem();

  public static GenericHID m_controller = new GenericHID(0);

  public RobotContainer() {    
    configureButtonBindings();
    
  }

  // Just realized that we could probably use While held instead. Too lazy to change it.

  private void configureButtonBindings() {

    //Drive

    // Invert Drive
    new JoystickButton(m_controller, 3)
                      .whenPressed(new InstantCommand(m_drive::invertDrive, m_drive));

    // Turns on Intake if held.
    new JoystickButton(m_controller, 2)
                      .whenHeld(new InstantCommand(m_intake::intakeBall, m_intake))
                              .whenReleased(m_intake::stop, m_intake);
    
    // Reverse Intake if held
    new JoystickButton(m_controller, 11)
                      .whenHeld(new InstantCommand(m_intake::spitOutBall, m_intake))
                              .whenReleased(m_intake::stop, m_intake);

     //Start Intake Deployment
    new JoystickButton(m_controller, 9)
                      .whenPressed(new InstantCommand(m_intake::startDeployment, m_intake)); 

    //End Intake Deployment
    new JoystickButton(m_controller, 10)
                      .whenPressed(new InstantCommand(m_intake::finishDeployment, m_intake)); 

    // Raising Climber
    new JoystickButton(m_controller, 12) 
                      .whenHeld(new InstantCommand(m_climber::raise, m_climber))
                              .whenReleased(m_climber::stop, m_climber);

    // Lower Climber
    new JoystickButton(m_controller, 4)
                      .whenHeld(new InstantCommand(m_climber::lower, m_climber))
                              .whenReleased(m_climber::stop, m_climber);

    // Shoot
    new JoystickButton(m_controller, 1)
                    .whenHeld(new ShooterTeleop());
  }

  public void teleopDrive() {
    m_drive.setDefaultCommand(new RunCommand(
      () -> m_drive.updateSpeed(m_controller.getRawAxis(0), m_controller.getRawAxis(1), m_controller.getRawAxis(2), true)
    ));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }

}
