package systemtrayicon;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Die Klasse f�gt ein Icon zum System-Tray hinzu.<br>
 * Ueber das Icon kann mit Linksklick der Maus ein Standard-Menue mit
 * Basisfunktionen<br>
 * fuer die Programmsteuerung geoeffnet werden. Ein Rechtklick auf das Icon
 * oeffnet die GUI, die aber separat programmiert werden muss. Das Interface f�r
 * die Anmeldung am TrayIcon ist noch in Arbeit ...<br>
 * <br>
 * Das Aussehen des TrayIcon kann je nach Status des Backup-Programms veraendert
 * werden.<br>
 * Die zur Verfuegung stehenden Stati sind in den Konstanten STATE_xxx der
 * Klasse festgelegt.<br>
 * <br>
 * Ein StatusListener kann angemeldet werden, so dass die Icons dynymisch auch
 * fuer weitere grafische Komponenten als Icons zur Verf�gung gestellt
 * werden.<br>
 * Wenn z.B. ein Fenster minimiert wird, kann dadurch in der Taskleiste der
 * Staus des Programms sichtbar gemacht werden.<br>
 * Der Staus <b>STATE_PERORMING_BACKUP</b> ist animiert, so dass ersichtlich
 * wird, dass das Programm aktiv ist.
 */

public class TrayUtility {
	private static TrayUtility trayUtilityInstance;

	private ArrayList<TrayListener> trayListenerList = new ArrayList<TrayListener>();
	private ArrayList<StateListener> stateListenerList = new ArrayList<StateListener>();

	private TrayIcon trayIcon;
	private SystemTray systemTray;
	private PopupMenu popupMenu;

	private Image iRed;
	private Image iGreen;
	private Image iYellow;
	private Image iBlue;
	private Image iGrey;
	private Image iCopy1;
	private Image iCopy2;
	private Image iCopy3;
	private Image iCopy4;

	private Thread imageThread; // F�r animiertes Icon

	public static synchronized TrayUtility getTrayUtility() {
		if (trayUtilityInstance == null) {
			trayUtilityInstance = new TrayUtility();
		}
		return trayUtilityInstance;
	}

	/**
	 * TrayLstener will be informed about actions from TrayMenu.<br>
	 * Possible actions are defined in interface {@link TrayListener}
	 * 
	 * @param trayListener Listener to be informed
	 */
	public void addTrayListener(TrayListener trayListener) {
		trayListenerList.add(trayListener);
	}

	/**
	 * TrayLstener will be informed about actions from TrayMenu.<br>
	 * Possible actions are defined in interface {@link TrayListener}
	 * 
	 * @param trayListener Listener to be informed
	 */
	public void addStateListener(StateListener stateListener) {
		stateListenerList.add(stateListener);
		stateListener.setIcon(iGrey);
	}

	/**
	 * Change TrayIcons color according to backup progress
	 * 
	 * @param state The current state. Constant from TrayIconUtil
	 */
	public void setTrayIconState(String state) {
		switch (state) {
		case StateListener.STATE_DEFAULT:
			showInitializing();
			break;
		case StateListener.STATE_WAITING_FOR_STICK:
			showWaitingForStick();
			break;
		case StateListener.STATE_STICK_FOUND:
			showStickFound();
			break;
		case StateListener.STATE_PERORMING_BACKUP:
			showPerformingBackup();
			break;
		case StateListener.STATE_DONE:
			showNothingToDo();
			break;
		case StateListener.STATE_ERROR:
			showError();
			break;
		default:
			showInitializing();
			break;
		}
	}

	///////////////////////////////////////////////////////////////////////
	// PRIVATE part of this class
	///////////////////////////////////////////////////////////////////////
	private TrayUtility() {
		setLookAndFeel();
		initSystemTray();
		loadImages();
		initTrayIcon();
	}

	private void initSystemTray() {
		if (!SystemTray.isSupported()) {
			JOptionPane.showMessageDialog(null, "SystemTray is not supported", "USBackup", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		systemTray = SystemTray.getSystemTray();
	}

	private void loadImages() {
		iGrey = createImage("images/bgrey.gif");
		iYellow = createImage("images/byellow.gif");
		iBlue = createImage("images/bblue.gif");
		iCopy1 = createImage("images/bcopy1.gif");
		iCopy2 = createImage("images/bcopy2.gif");
		iCopy3 = createImage("images/bcopy3.gif");
		iCopy4 = createImage("images/bcopy4.gif");
		iGreen = createImage("images/bgreen.gif");
		iRed = createImage("images/bred.gif");
	}

	/**
	 * Create image object from image file
	 * 
	 * @param path Absolute or relative path for image to be loaded
	 * @return the image object - null if no image found
	 */
	private Image createImage(String path) {
		URL imageURL = ClassLoader.getSystemClassLoader().getResource(path);
		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			JOptionPane.showMessageDialog(null, "TrayImage not found", "USBackup", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return null; // Der bloede Compiler will das so, auch wenn es keinen Sinn macht
		} else {
			return (new ImageIcon(imageURL)).getImage();
		}
	}

	private void initTrayIcon() {
		// Create TrayIcon Object
		trayIcon = new TrayIcon(iGrey, "Auto-Backup by BK-Nord");
		trayIcon.setImageAutoSize(true);

		// Do layout synchronized with GUI
		SwingUtilities.invokeLater(() -> createAndShowGUI());
	}

	private void createAndShowGUI() {

		// Create main menu
		popupMenu = new PopupMenu();

		// Create pop up menu components
		MenuItem aboutItem = new MenuItem("Programm-Info");
		MenuItem configItem = new MenuItem("Backup konfigurieren");
		MenuItem exitItem = new MenuItem("Programm beenden");

		// Add components to pop up menu
		popupMenu.add(aboutItem);
		popupMenu.addSeparator();
		popupMenu.add(configItem);
		popupMenu.addSeparator();
		popupMenu.addSeparator();
		popupMenu.add(exitItem);

		trayIcon.setPopupMenu(popupMenu);

		try {
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			JOptionPane.showMessageDialog(null, "TrayIcon could not be added.", "USBackup", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		/*
		 * trayIcon.addMouseListener ( new MouseListener () { public void mouseReleased
		 * ( MouseEvent e ) { } public void mousePressed ( MouseEvent e ) { } public
		 * void mouseExited ( MouseEvent e ) { } public void mouseEntered ( MouseEvent e
		 * ) { } public void mouseClicked ( MouseEvent e ) { if ( e.getButton () ==
		 * MouseEvent.BUTTON1 ) // Wenn linker Maus-Button ... { try // ... dann
		 * Rechtsklick an der gleichen Stelle ausloesen { Robot robot = new Robot ();
		 * robot.mousePress ( InputEvent.BUTTON3_DOWN_MASK ); robot.mouseRelease (
		 * InputEvent.BUTTON3_DOWN_MASK ); } catch ( Exception e1 ) { e1.printStackTrace
		 * (); } } } } );
		 */

		aboutItem.addActionListener(e -> {
			JOptionPane.showMessageDialog(null,
					"<html><H1>BK-Backup</h1>\n" + "Version 0.1.01\n" + "Berufskolleg Alsdorf\n"
							+ "52477 Alsdorf, Heidweg 2\n" + "<html><b>www.bk-alsdorf.de</b>\n" + "\u00A9 21FIA1",
					"BK-Backup", JOptionPane.INFORMATION_MESSAGE);
		});

		exitItem.addActionListener(e -> {
			systemTray.remove(trayIcon);
			System.exit(0);
		});

		configItem.addActionListener(e -> {
			for (TrayListener listener : trayListenerList)
				if (listener != null)
					listener.actionRequired(TrayListener.EVENT_CONFIGURATION);
		});
	}

	/*
	 * Set Look & Feel to current system style. Will be "Metal", if no system style
	 * is supported.
	 */
	private void setLookAndFeel() {
		try /* Use an appropriate Look and Feel */
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace(); // Dann nehmen wir halt das default
		}
	}

	private void showInitializing() {
		if (imageThread != null)
			imageThread.interrupt();
		trayIcon.setImage(iGrey);
		callStateListeners(trayIcon.getImage());
		trayIcon.setToolTip("Backup muss konfiguriert werden");
	}

	private void showWaitingForStick() {
		if (imageThread != null)
			imageThread.interrupt();
		trayIcon.setImage(iBlue);
		callStateListeners(trayIcon.getImage());
		trayIcon.setToolTip("Warte auf g\u00FCltigen USB-Stick");
	}

	private void showStickFound() {
		if (imageThread != null)
			imageThread.interrupt();
		trayIcon.setImage(iYellow);
		callStateListeners(trayIcon.getImage());
		trayIcon.setToolTip("Stick f\u00FCr Backup gefunden");
	}

	private void showPerformingBackup() {
		if (imageThread != null)
			imageThread.interrupt();
		trayIcon.setToolTip("Backup wird durchgef\u00FChrt");

		imageThread = new Thread() {
			public void run() {
				trayIcon.setImage(iYellow);
				callStateListeners(trayIcon.getImage());
				try {
					while (true) {
						sleep(500);
						trayIcon.setImage(iCopy1);
						callStateListeners(trayIcon.getImage());
						sleep(500);
						trayIcon.setImage(iCopy2);
						callStateListeners(trayIcon.getImage());
						sleep(500);
						trayIcon.setImage(iCopy3);
						callStateListeners(trayIcon.getImage());
						sleep(500);
						trayIcon.setImage(iCopy4);
						callStateListeners(trayIcon.getImage());
					}
				} catch (InterruptedException e) {
					imageThread = null;
				}
			}
		};
		imageThread.start();
	}

	private void showError() {
		if (imageThread != null)
			imageThread.interrupt();
		trayIcon.setToolTip("Ein Fehler ist aufgetreten!\nSiehe Ereignisprotokoll.");

		imageThread = new Thread() {
			public void run() {
				trayIcon.setImage(iGrey);
				callStateListeners(trayIcon.getImage());
				try {
					while (true) {
						sleep(500);
						trayIcon.setImage(iRed);
						callStateListeners(trayIcon.getImage());
						sleep(500);
						trayIcon.setImage(iGrey);
						callStateListeners(trayIcon.getImage());
					}
				} catch (InterruptedException e) {
					imageThread = null;
				}
			}
		};
		imageThread.start();
	}

	private void showNothingToDo() {
		if (imageThread != null)
			imageThread.interrupt();
		trayIcon.setImage(iGreen);
		trayIcon.setToolTip("Backup wurde heute schon durchgef\u00FChrt");
		callStateListeners(trayIcon.getImage());
	}

	private void callStateListeners(Image image) {
		for (StateListener listener : stateListenerList) {
			if (listener != null)
				listener.setIcon(image);
		}
	}
}
