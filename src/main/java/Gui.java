import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Gui extends JFrame {

	private JPanel contentPane;

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

// Hier werden die einzelnen Panels/Cards erstellt -------------------------------------------------
		
		JPanel test = new JPanel();
		main.add(test, "name_test");
		
		JPanel start = new JPanel();
		start.setBackground(SystemColor.activeCaption);
		main.add(start, "name_start");
		
// Danach werden sie mit Inhal gefüllt -------------------------------------------------------------		
		
		JLabel lblNewLabel = new JLabel("TEST");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		test.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Backup einrichten");
		btnNewButton.addActionListener(e -> {
			CardLayout cardLayout = (CardLayout) main.getLayout();
			cardLayout.show(main, "name_test");
		});
		
		start.setLayout(null);
		btnNewButton.setBounds(67, 30, 207, 29);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		start.add(btnNewButton);
		
		JButton btnBackupStarten = new JButton("Backup starten");
		btnBackupStarten.setBounds(67, 89, 207, 29);
		btnBackupStarten.setFont(new Font("Tahoma", Font.PLAIN, 16));
		start.add(btnBackupStarten);
		
		JButton btnBackupWiederherstellen = new JButton("Backup wiederherstellen");
		btnBackupWiederherstellen.setBounds(67, 207, 207, 29);
		btnBackupWiederherstellen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		start.add(btnBackupWiederherstellen);
		
		JButton btnBackupsAuflisten = new JButton("Backups auflisten");
		btnBackupsAuflisten.setBounds(67, 148, 207, 29);
		btnBackupsAuflisten.setFont(new Font("Tahoma", Font.PLAIN, 16));
		start.add(btnBackupsAuflisten);
		
		JButton btnLogStatus = new JButton("Log / Status");
		btnLogStatus.setBounds(67, 266, 207, 29);
		btnLogStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		start.add(btnLogStatus);
		
		JButton btnInfoHilfe = new JButton("Info / Hilfe");
		btnInfoHilfe.setBounds(67, 325, 207, 29);
		btnInfoHilfe.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
	}
}
