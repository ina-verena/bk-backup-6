package frontend;

import backup.system.services.BackupService;
import backup.system.services.FindDriveService;
import logpanel.LogPanel;
import backup.system.model.ConfigData;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Gui extends JFrame {

	private JPanel contentPane;
	LogPanel logPanel = LogPanel.getLogPanel();

	FindDriveService find_driveService = new FindDriveService();
	ConfigData configData = new ConfigData();

	BackupService backupService = new BackupService(this);



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
// Aufbau der Struktur	---------------------------------------------------------------------------
		
		setBackground(SystemColor.activeCaption);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 500);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel main = new JPanel(new CardLayout(0, 0));
		main.setBackground(SystemColor.inactiveCaption);
		contentPane.add(main, BorderLayout.CENTER);


	
// Panel für das Startmenü mit den vielen Buttons
		JPanel start = new JPanel();
		start.setBackground(SystemColor.activeCaption);
		main.add(start, "name_start");
		
		JButton btnNewButton = new JButton("Backup einrichten");
		btnNewButton.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_drivers");
			logPanel.addLogEntry(LogPanel.MESSAGE_INFO, "Backups wurde eingerichtet");
		});
		
		start.setLayout(null);
		btnNewButton.setBounds(67, 30, 207, 29);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		start.add(btnNewButton);
		
        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(24, 275, 279, 28);
        progressBar.setStringPainted(true); // Aktiviere die Textanzeige

		JButton btnBackupStarten = new JButton("Backup starten");
		btnBackupStarten.setBounds(67, 89, 207, 29);
		btnBackupStarten.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBackupStarten.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_running");
			File currentDrive = find_driveService.chooseDrive(find_driveService.getDriveList());
			Path pathOfConfigOnExternal = Paths.get(currentDrive + "config");
			backupService.setPathOfConfig(pathOfConfigOnExternal);

 	        new Thread(() -> {
				backupService.startBackup(String.valueOf(currentDrive), backupService);
 	                progressBar.setValue(backupService.getLengthInPercent()); // Setze den Fortschrittswert
 	                if (backupService.getLengthInPercent() == 100) {
 	                    progressBar.setString("Fertig!"); // Endnachricht
 	                }
 	                try {
 	                    Thread.sleep(5); // Wartezeit für die Animation
 	                } catch (InterruptedException ex) {
 	                    ex.printStackTrace();
 	                }

 	        }).start();
			 logPanel.addLogEntry(LogPanel.MESSAGE_INFO, "Backups wurde gestartet");
		});
		start.add(btnBackupStarten);
		
		JButton btnBackupWiederherstellen = new JButton("Backup wiederherstellen");
		btnBackupWiederherstellen.setBounds(67, 207, 207, 29);
		btnBackupWiederherstellen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBackupWiederherstellen.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_drivers");
			logPanel.addLogEntry(LogPanel.MESSAGE_WARNING, "Es gibt noch keine Funktion um Backups wiederherzustellen");
		});
		start.add(btnBackupWiederherstellen);
		
		JButton btnBackupsAuflisten = new JButton("Backups auflisten");
		btnBackupsAuflisten.setBounds(67, 148, 207, 29);
		btnBackupsAuflisten.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBackupsAuflisten.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_backups");
			logPanel.addLogEntry(LogPanel.MESSAGE_INFO, "Backups sollen aufgelistet werden");

		});
		start.add(btnBackupsAuflisten);
		
		JButton btnLogStatus = new JButton("Log / Status");
		btnLogStatus.setBounds(67, 266, 207, 29);
		btnLogStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogStatus.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_log");
		});
		start.add(btnLogStatus);
		
		JButton btnInfoHilfe = new JButton("Info / Hilfe");
		btnInfoHilfe.setBounds(67, 325, 207, 29);
		btnInfoHilfe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnInfoHilfe.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_help");
			logPanel.addLogEntry(LogPanel.MESSAGE_WARNING, "Hilfe wurde aufgerufen, es gibt aber noch keine");
		});
		start.add(btnInfoHilfe);
		
		JLabel headerText = new JLabel("BK-Backup");
		headerText.setAlignmentY(Component.TOP_ALIGNMENT);
		headerText.setHorizontalTextPosition(SwingConstants.CENTER);
		headerText.setHorizontalAlignment(SwingConstants.CENTER);
		headerText.setForeground(Color.WHITE);
		headerText.setFont(new Font("Tahoma", Font.PLAIN, 24));
		headerText.setAlignmentX(0.5f);
		contentPane.add(headerText, BorderLayout.NORTH);
		
		JPanel footer = new JPanel();
		footer.setBackground(SystemColor.activeCaption);
		contentPane.add(footer, BorderLayout.SOUTH);
		footer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
// Footer ------------------------------------------------------------------------------
		
		JButton btnStartmen = new JButton("Startmenü");
		btnStartmen.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_start");
		});
		
		btnStartmen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		footer.add(btnStartmen);
		
		JButton btnBeenden = new JButton("Beenden");
		footer.add(btnBeenden);
		btnBeenden.addActionListener(e -> {
			System.out.println(e.getActionCommand());
			System.exit(0);
		});
		
		btnBeenden.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
// Intiales Panel/Card mit dem das Programm startet		
		
		CardLayout cardLayout = (CardLayout) main.getLayout();
        cardLayout.show(main, "name_start");
        
// Panel welches die Backups auflistet        
        JPanel backups = new JPanel();
        backups.setBackground(SystemColor.activeCaption);
        main.add(backups, "name_backups");
        
        JLabel lblBackupsList = new JLabel("Backup List");
        lblBackupsList.setForeground(Color.WHITE);
        lblBackupsList.setFont(new Font("Tahoma", Font.PLAIN, 18));
        backups.add(lblBackupsList);

// Panel welches die Laufwerke auflistet
     		JPanel drivers = new JPanel();
     		drivers.setBackground(SystemColor.activeCaption);
     		main.add(drivers, "name_drivers");
     		drivers.setLayout(null);

     		JLabel lblNewLabel = new JLabel("Drivers List");
     		lblNewLabel.setBounds(111, 5, 89, 22);
     		lblNewLabel.setForeground(SystemColor.text);
     		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
     		drivers.add(lblNewLabel);	
     		
     		JPanel listcontainer = new JPanel();
     		listcontainer.setBackground(SystemColor.activeCaption);
     		listcontainer.setBounds(10, 37, 306, 338);
     		drivers.add(listcontainer);
     		listcontainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
     		   		
    		for (String drive: find_driveService.getDriveListAsString()) {
    			
    			JButton entry = new JButton(drive + "                           ");
    			entry.setBounds(10, 5, 89, 22);
         		entry.setFont(new Font("Tahoma", Font.PLAIN, 18));
         		entry.addActionListener(n -> {
        			configData.initConfigData(Paths.get(drive + "config"));

        			cardLayout.show(main, "name_initial");
        		});
    			listcontainer.add(entry);
    		}
     		
//  Panel welches das Backup einrichten lässt        
        JPanel initial = new JPanel();
        initial.setBackground(SystemColor.activeCaption);
        main.add(initial, "name_initial");
        initial.setLayout(null);
        
        JLabel lblBackupEinrichten = new JLabel("Backup einrichten");
        lblBackupEinrichten.setBounds(91, 10, 145, 22);
        lblBackupEinrichten.setForeground(Color.WHITE);
        lblBackupEinrichten.setFont(new Font("Tahoma", Font.PLAIN, 18));
        initial.add(lblBackupEinrichten);
        
        JLabel lblUuidWirdErstellt = new JLabel("UUID wird erstellt ...");
        lblUuidWirdErstellt.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblUuidWirdErstellt.setBounds(10, 70, 316, 40);
        lblUuidWirdErstellt.setForeground(Color.WHITE);
        lblUuidWirdErstellt.setFont(new Font("Tahoma", Font.PLAIN, 20));
        initial.add(lblUuidWirdErstellt);
        
        JLabel lblOrdnerWerdenAngelegt = new JLabel("Ordner werden angelegt ...");
        lblOrdnerWerdenAngelegt.setForeground(Color.WHITE);
        lblOrdnerWerdenAngelegt.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblOrdnerWerdenAngelegt.setAlignmentX(0.5f);
        lblOrdnerWerdenAngelegt.setBounds(10, 120, 316, 40);
        initial.add(lblOrdnerWerdenAngelegt);
        
        JLabel lblBackupWirdGestartet = new JLabel("Backup wird gestartet ...");
        lblBackupWirdGestartet.setForeground(Color.WHITE);
        lblBackupWirdGestartet.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblBackupWirdGestartet.setAlignmentX(0.5f);
        lblBackupWirdGestartet.setBounds(10, 174, 316, 40);
        initial.add(lblBackupWirdGestartet);
        
// Panel welches angezeigt wird, wenn das Backup läuft        
        JPanel running = new JPanel();
        running.setBackground(SystemColor.activeCaption);
        main.add(running, "name_running");
        running.setLayout(null);
        
        JLabel lblBackupLuft = new JLabel("Backup läuft");
        lblBackupLuft.setBounds(112, 5, 101, 22);
        lblBackupLuft.setForeground(Color.WHITE);
        lblBackupLuft.setFont(new Font("Tahoma", Font.PLAIN, 18));
        running.add(lblBackupLuft);
        
        running.add(progressBar);
        
        try {
        	BufferedImage myPicture = ImageIO.read(new File("src/assets/sanduhr.png"));
        	JLabel sanduhr = new JLabel(new ImageIcon(myPicture));
	        sanduhr.setBounds(112, 91, 112, 145);
	        
        	int sanduhrWidth = sanduhr.getWidth();
        	int sanduhrHeight = sanduhr.getHeight();
        	Image scaledImage = myPicture.getScaledInstance(sanduhrWidth, sanduhrHeight, Image.SCALE_SMOOTH);
			
        	ImageIcon scaledIcon = new ImageIcon(scaledImage);
        	sanduhr.setIcon(scaledIcon);
        	
	        running.add(sanduhr);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        
        
// Panel für die Bestätigung das das Backup erfolgreich war        
        JPanel finish = new JPanel();
        finish.setBackground(SystemColor.activeCaption);
        main.add(finish, "name_finish");
        finish.setLayout(null);
        
        JLabel lblBackupAbgeschlossen = new JLabel("Backup abgeschlossen");
        lblBackupAbgeschlossen.setBounds(43, 142, 248, 59);
        lblBackupAbgeschlossen.setForeground(Color.WHITE);
        lblBackupAbgeschlossen.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblBackupAbgeschlossen.setAlignmentX(0.5f);
        finish.add(lblBackupAbgeschlossen);
        
// Panel für die Log Meldungen 
	
	logPanel.setBackground(SystemColor.activeCaption);
	main.add(logPanel, "name_log");

	logPanel.addLogEntry(LogPanel.MESSAGE_INFO, "Das LogPanel wurde angelegt");
     
// Panel für die Anleitung des Programmes        
        JPanel help = new JPanel();
        help.setBackground(SystemColor.activeCaption);
        main.add(help, "name_help");
        
        JLabel lblAnleitung = new JLabel("Anleitung");
        lblAnleitung.setForeground(Color.WHITE);
        lblAnleitung.setFont(new Font("Tahoma", Font.PLAIN, 18));
        help.add(lblAnleitung);
	}

	public void changeProgressBar(int value) {
		backupService.setLengthInPercent(value);
		System.out.println("PROGRESS " + value);
	}
}
