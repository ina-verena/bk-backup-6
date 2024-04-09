package systemtrayicon;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import systemtrayicon.StateListener;
import systemtrayicon.TrayListener;

public class GuiTest implements TrayListener, StateListener {

   private JFrame frame;
	private TrayUtility trayUtility;

	public static void main(String[] args) {
		new GuiTest();
	}

	public GuiTest() {
      // JFrame für das Standard-Menue (ist anfangs unsichtbar)
      prepareDefaultFrame();

      // IconUtility
		// - Referenz auf das Singleton
		// - Anmelden, um User-Eingaben und Aktualisierungen des Icon zu erhalten
	   trayUtility = TrayUtility.getTrayUtility();
	   trayUtility.addStateListener(this);
	   trayUtility.addTrayListener(this);
	}

	// Methoden des Interface TrayActionListener:
	// - User-Eingabe erfordert Aktion
	public void actionRequired(String command) {
	   System.out.println ( command );
		switch (command) {
		case TrayListener.EVENT_CONFIGURATION:
			frame.setVisible ( true );
			break;
		default:
		}
	}

	// Methode des Interface IconChangedListener:
	// - Das Icon wurde veraendert
	public void setIcon(Image currentIcon) {
		frame.setIconImage(currentIcon);
	}

	// Hilfsmethode(n)
	private void prepareDefaultFrame() {
		frame = new JFrame("Test des TrayIcons");
		frame.setBounds(200, 200, 600, 400);
		
		frame.setDefaultCloseOperation ( JFrame.DO_NOTHING_ON_CLOSE );
		frame.addWindowListener ( new WindowAdapter() { public void windowClosing ( WindowEvent e ) { frame.setVisible ( false ); } } );

		frame.setLayout ( new GridLayout ( 0, 1 ) );
		
		JButton btnDefault = new JButton ( "show icon STATE_DEFAULT" );
		btnDefault.addActionListener ( e -> trayUtility.setTrayIconState ( StateListener.STATE_DEFAULT ) );
      frame.add ( btnDefault );

      JButton btnWaiting = new JButton ( "show icon STATE_WAITING_FOR_STICK" );
      btnWaiting.addActionListener ( e -> trayUtility.setTrayIconState ( StateListener.STATE_WAITING_FOR_STICK ) );
      frame.add ( btnWaiting );
      
      JButton btnFound = new JButton ( "show icon STATE_STICK_FOUND" );
      btnFound.addActionListener ( e -> trayUtility.setTrayIconState ( StateListener.STATE_STICK_FOUND ) );
      frame.add ( btnFound );
      
      JButton btnPerforming = new JButton ( "show icon STATE_PERORMING_BACKUP" );
      btnPerforming.addActionListener ( e -> trayUtility.setTrayIconState ( StateListener.STATE_PERORMING_BACKUP ) );
      frame.add ( btnPerforming );
      
      JButton btnDone = new JButton ( "show icon STATE_DONE" );
      btnDone.addActionListener ( e -> trayUtility.setTrayIconState ( StateListener.STATE_DONE ) );
      frame.add ( btnDone );
      
      JButton btnError = new JButton ( "show icon STATE_ERROR" );
      btnError.addActionListener ( e -> trayUtility.setTrayIconState ( StateListener.STATE_ERROR ) );
      frame.add ( btnError );
      
      frame.pack ();
	}
}
