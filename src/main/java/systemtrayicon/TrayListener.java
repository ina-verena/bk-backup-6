package systemtrayicon;

/**
 * Klassen, die die GuiApi nutzen wollen, muessen sich bei der GuiApi als Listener anmelden<br><b>
 * (guiApi.addGuiApiListener(...)</b><br>
 * Die GuiApi informiert alle Listener ueber User-Eingaben im Tray-Icon
 */
public interface TrayListener
{
   /** Statusanzeige Icon setzen */
  public static final String EVENT_CONFIGURATION = "configuration";

   /**
    * Ein User-Input muss bearbeitet werden<br>
    * Die moeglichen Befehle sind in der Klasse GuiApi als Konstanten definiert.
    * 
    * @param command Ein String, der das gewuenschte Kommando enthaelt
    */
   public void actionRequired ( String command );
}
