import backup.system.drive.handler.Find_Drive;
import backup.system.file.handler.ConfigData;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JRadioButton;

public class Gui extends JFrame {

	private JPanel contentPane;

	Find_Drive find_drive = new Find_Drive();
	ConfigData configData = new ConfigData();

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
		});
		
		start.setLayout(null);
		btnNewButton.setBounds(67, 30, 207, 29);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		start.add(btnNewButton);
		
		JButton btnBackupStarten = new JButton("Backup starten");
		btnBackupStarten.setBounds(67, 89, 207, 29);
		btnBackupStarten.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBackupStarten.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_running");
		});
		start.add(btnBackupStarten);
		
		JButton btnBackupWiederherstellen = new JButton("Backup wiederherstellen");
		btnBackupWiederherstellen.setBounds(67, 207, 207, 29);
		btnBackupWiederherstellen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBackupWiederherstellen.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_drivers");
		});
		start.add(btnBackupWiederherstellen);
		
		JButton btnBackupsAuflisten = new JButton("Backups auflisten");
		btnBackupsAuflisten.setBounds(67, 148, 207, 29);
		btnBackupsAuflisten.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBackupsAuflisten.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_backups");
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
     		   		
     		     		
    		for (String drive: find_drive.getDriveListAsString()) {
    			JButton entry = new JButton(drive + "                           ");
    			entry.setBounds(10, 5, 89, 22);
         		entry.setFont(new Font("Tahoma", Font.PLAIN, 18));
         		entry.addActionListener(n -> {
        			configData.initConfigData(Paths.get(drive + "config"));
        		});
    			listcontainer.add(entry);
    		}
     		
//  Panel welches das Backup einrichten lässt        
        JPanel initial = new JPanel();
        initial.setBackground(SystemColor.activeCaption);
        main.add(initial, "name_initial");
        
        JLabel lblBackupEinrichten = new JLabel("Backup einrichten");
        lblBackupEinrichten.setForeground(Color.WHITE);
        lblBackupEinrichten.setFont(new Font("Tahoma", Font.PLAIN, 18));
        initial.add(lblBackupEinrichten);
        
// Panel welches angezeigt wird, wenn das Backup läuft        
        JPanel running = new JPanel();
        running.setBackground(SystemColor.activeCaption);
        main.add(running, "name_running");
        
        JLabel lblBackupLuft = new JLabel("Backup läuft");
        lblBackupLuft.setForeground(Color.WHITE);
        lblBackupLuft.setFont(new Font("Tahoma", Font.PLAIN, 18));
        running.add(lblBackupLuft);
        
// Panel für die Bestätigung das das Backup erfolgreich war        
        JPanel finish = new JPanel();
        finish.setBackground(SystemColor.activeCaption);
        main.add(finish, "name_finish");
        
        JLabel lblNewLabel_1 = new JLabel("Backup erstellt");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        finish.add(lblNewLabel_1);
        
// Panel für die Log Meldungen 
        JPanel log = new JPanel();
        log.setBackground(SystemColor.activeCaption);
        main.add(log, "name_log");
        
        JLabel lblLogReport = new JLabel("Log / Report");
        lblLogReport.setForeground(Color.WHITE);
        lblLogReport.setFont(new Font("Tahoma", Font.PLAIN, 18));
        log.add(lblLogReport);
        
// Panel für die Anleitung des Programmes        
        JPanel help = new JPanel();
        help.setBackground(SystemColor.activeCaption);
        main.add(help, "name_help");
        
        JLabel lblAnleitung = new JLabel("Anleitung");
        lblAnleitung.setForeground(Color.WHITE);
        lblAnleitung.setFont(new Font("Tahoma", Font.PLAIN, 18));
        help.add(lblAnleitung);
	}
}
