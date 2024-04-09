package logpanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class LogPanelTester {
	private JFrame frame;

	public static void main(String[] args) {
		new LogPanelTester();
	}

	public LogPanelTester() {
	   LogPanel logPanel1 = LogPanel.getLogPanel ();

	   createExampleFrame();
		frame.add(logPanel1, BorderLayout.CENTER);
		frame.setVisible(true);

		// Meldungen im LogPanel speichern
		logPanel1.addLogEntry(LogPanel.MESSAGE_INFO, "Das LogPanel wurde angelegt");
		logPanel1.addLogEntry("Ohne Parameter wird Standard-Info gezeigt");
		logPanel1.addLogEntry(LogPanel.MESSAGE_DEBUG,
				"Langer Text im Debug-Modus skdskdj fhskj skjd hksdjh fksjdh fksj fdhksjd hfskdj hskdfj hskdj fhksjdh skdjh ksdjf hk");
		logPanel1.addLogEntry(LogPanel.MESSAGE_WARNING, "Ich fuehle mich heute nicht gut");
		logPanel1.addLogEntry(LogPanel.MESSAGE_ERROR, "Da ist ein Fehler passiert");
		
		// Ein zweites LogPanel holen und Meldung in der gleichen GUI anzeigen, wie LogPanel1
		// Dieser Code knn in jeder Klasse des gleichen Programms genutzt werden
		LogPanel logPanel2 = LogPanel.getLogPanel ();
		logPanel2.addLogEntry("LogPanel2 sollte auf das gleiche LogPanel zugreifen, wie LogPanel1");
		
		// Eventuell sind statische Methoden ja doch einfacher? Dann muesste man sich nicht immer erst ein Objekt der Klasse holen.
		// Einfach nur "LogPanel.addLogEntry()" und der Keks ist gegessen.
		// Alternativ kann auch System.out.println() einfach in das LogPanel umgeleitet werden. Dann ist die Protokollierung wie "native Java-Konsole"
      LogPanel.getLogPanel ().addLogEntry(LogPanel.MESSAGE_DEBUG, "Natuerlich koennen auch spezifische Message-Typen angegeben werden.");
	}

	// Hilfsmethode(n)
	private void createExampleFrame() {
		frame = new JFrame("Test der GUI-Komponenten");
		frame.setBounds(200, 200, 600, 400);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
