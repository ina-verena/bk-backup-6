package logpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Displays messages as a table.<br>
 * Using different colurs depending of message type<br>
 * See constant values of this class
 * 
 * @author hillermann
 */
public class LogPanel extends JPanel
{
   /** Recomended: Any graphical Class implements interface Serializable */
   private static final long serialVersionUID = -4060186281560549441L;

   /** Message Info dispayed in black */
   public static final String MESSAGE_INFO = "Info";
   /** Message Debug dispayed in blue */
   public static final String MESSAGE_DEBUG = "Debug";
   /** Message Warning dispayed in yellow */
   public static final String MESSAGE_WARNING = "Warning";
   /** Message Error dispayed in red */
   public static final String MESSAGE_ERROR = "Error";

   private static LogPanel logPanelReference;
   
   private SimpleDateFormat   simpleDateFormat = new SimpleDateFormat ( " yyyy.MM.dd - HH:mm:ss " );

   // Basiseinstellungen fuer alle grafischen Objekte
   private GridBagConstraints gbc              = new GridBagConstraints ( 0,     // int gridx
                                                                          0,                                          // int gridy
                                                                          1,                                          // int gridwidth
                                                                          1,                                          // int gridheight
                                                                          0,                                          // double weightx
                                                                          0,                                          // double weighty
                                                                          GridBagConstraints.PAGE_START,              // int anchor
                                                                          GridBagConstraints.BOTH,                    // int fill
                                                                          new Insets ( 2, 2, 2, 2 ),                  // Insets insets
                                                                          0,                                          // int ipadx
                                                                          1 );                                        // int ipady

   private JPanel         gridBagPanel;
   private Component dummy;
   private int               yCounter         = 0; // Zeilenangabe fuer die Meldungen

   /**
    * Das LogPanel ist ein Singleton, daher kann kein Objekt direkt angelegt werden.<br>
    * Die Methode legt ein Objekt des LogPanel an, wenn noch nicht vorhanden und<br>
    * liefert die Referenz auf das LogPanel-Singleton zurueck.
    * 
    * @return EinLogPanel-Objekt als Singleton
    */
   public static synchronized LogPanel getLogPanel ()
   {
      if ( logPanelReference == null )
      {
         logPanelReference = new LogPanel ();
      }
      return logPanelReference;
   }
   
   /**
    * The LogPanel will show messages in different colours.<br>
    * Colours depend on the type of param "type".<br>
    * Possible values are:<br>
    * INFO - black - information message<br>
    * DEBUG - blue - debug message<br>
    * WARNING - orange - warning message<br>
    * ERROR - red - error message<br>
    * <br>
    * 
    * @param type
    *           the type of the message defined as constants GuiManager<br>
    * @param message
    *           the message to be displayed
    */
   public void addLogEntry ( String type, String message )
   {
      this.internAddMessage ( type, message );
      this.repaint ();
   }
   
   /**
    * Simply logs a message adding a time stamp.
    * Type of message will be:<br>
    * INFO - black - information message<br>
    * 
    * @param message
    *           the message to be displayed
    */
   public void addLogEntry ( String message )
   {
      this.internAddMessage ( LogPanel.MESSAGE_INFO, message );
      this.repaint ();
   }

   /**
    * Standardkonstruktor erzeugt ein JPanel, das in einer beliebigen GUI eingefuegt werden kann
    * Da es sich um ein Singleton handelt, darf der Konstruktor nicht public sein!
    */
   private LogPanel ()
   {
      this.setLayout ( new BorderLayout () );

      // Inneres Panel, das gescrollt werden soll
      // Darauf werden die Nachrichten dargestellt
      gridBagPanel = new JPanel ( new GridBagLayout () )
      {

         public Dimension getPreferredSize ()
         {
            Dimension d = super.getPreferredSize ();
            d.width = 0;
            return d;
         }
      };
      gridBagPanel.setBackground ( Color.lightGray );

      // Die ScrollPane nimmt die gesamte Flaeche ein
      JScrollPane jsp = new JScrollPane ( gridBagPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
      jsp.setBackground ( Color.RED );
      add ( jsp, BorderLayout.CENTER );

      // Ein Dummy-Element fuer den freien Platz an der letzten moeglichen Position
      // einfuegen
      addDummy ();
   }

   private void addDummy ()
   {
      gbc.gridx = 0;
      gbc.gridy = 511; // Leider sind nur 512 Zeilen moeglich
      gbc.weighty = 0.1;
      dummy = new JLabel ( " " );
      gridBagPanel.add ( dummy, gbc );
      gbc.weighty = 0;
   }

   private synchronized void internAddMessage ( String type, String message )
   {
      Color color;

      if ( type == null )
         type = LogPanel.MESSAGE_INFO;

      switch ( type )
      {
         case LogPanel.MESSAGE_INFO:
            color = Color.black;
            break;
         case LogPanel.MESSAGE_DEBUG:
            color = Color.blue;
            break;
         case LogPanel.MESSAGE_WARNING:
            color = Color.orange;
            break;
         case LogPanel.MESSAGE_ERROR:
            color = Color.red;
            break;
         default:
            color = Color.black;
            type = LogPanel.MESSAGE_INFO;
      }

      JLabel label = new JLabel ( simpleDateFormat.format ( new Date () ) + " [" + type + "]: " );
      label.setForeground ( color );
      label.setBackground ( new Color ( 0xe0e0e0 ) );
      label.setOpaque ( true );
      gbc.gridx = 0;
      gbc.gridy = yCounter;
      gbc.weightx = 0;

      gridBagPanel.add ( label, gbc );

      JTextArea text = new JTextArea ( message );
      text.setEditable ( false );
      text.setLineWrap ( true );
      text.setWrapStyleWord ( true );
      text.setBackground ( new Color ( 0xe0e0e0 ) );
      gbc.gridx = 1;
      gbc.gridy = yCounter;
      gbc.weightx = 0.1;
      gridBagPanel.add ( text, gbc );

      yCounter++;

      // Hoehe des Panels aus der aktuellen Hoehe aller Komponenten berechnen
      gridBagPanel.doLayout ();

      // Autoscroll
      gridBagPanel.scrollRectToVisible ( text.getBounds () );
      gridBagPanel.scrollRectToVisible ( text.getBounds () );

      // System.out.println umleiten auf EventPanel
      /*
       * try { System.setOut(new java.io.PrintStream(System.out, true, "cp850")); } catch(Exception e) { e.printStackTrace(); }
       */
   }
}
