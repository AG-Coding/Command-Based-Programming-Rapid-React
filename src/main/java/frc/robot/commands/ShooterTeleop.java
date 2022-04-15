package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;

public class ShooterTeleop extends CommandBase {
    private VisionSubsystem m_vision;
    private ShooterSubsystem m_shooter;
    private IndexerSubsystem m_indexer;
    private DriveSubsystem m_drive;

    public ShooterTeleop() {
        addRequirements(m_vision);
        addRequirements(m_shooter);
        addRequirements(m_drive);
        addRequirements(m_indexer);
        m_vision = new VisionSubsystem();
        m_shooter = new ShooterSubsystem();
        m_indexer = new IndexerSubsystem();
        m_drive = new DriveSubsystem();
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        m_drive.updateSpeed(0, 0, m_vision.steeringAssist(), false);
        m_shooter.shoot(m_shooter.getClampedRPM());
        new WaitCommand(1);
        m_drive.updateSpeed(0, 0, 0, false);
        m_indexer.extend();
        new WaitCommand(1);
    }

    public void end() {
        m_shooter.stop();
        m_indexer.retract();
    }
}
