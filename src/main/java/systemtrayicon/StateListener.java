package systemtrayicon;

import java.awt.Image;

/**
 * Klassen, die die GuiApi nutzen wollen, muessen sich bei der GuiApi als
 * Listener anmelden<br>
 * (guiApi.addGuiApiListener(...)<br>
 * Die GuiApi informiert alle IconChangedListener ueber ein neues Icon, das vom
 * JFrame benutzt<br>
 * werden kann, um den aktuellen Betriebszustand anzuzeigen
 */
public interface StateListener {
	/** Statusanzeige Icon setzen */
	public static final String STATE_DEFAULT = "default";
	/** Statusanzeige Icon setzen */
	public static final String STATE_WAITING_FOR_STICK = "searching";
	/** Statusanzeige Icon setzen */
	public static final String STATE_STICK_FOUND = "present";
	/** Statusanzeige Icon setzen */
	public static final String STATE_PERORMING_BACKUP = "performing";
	/** Statusanzeige Icon setzen */
	public static final String STATE_DONE = "done";
	/** Statusanzeige Icon setzen */
	public static final String STATE_ERROR = "error";

	/**
	 * Die GUI-API veraendert das angezeigte Icon dynamisch zur Anzeige der
	 * Aktivitaeten des Programms.<br>
	 * Wenn ein Programmfenster angezeigt wird, sollte das Icon an den JFrame
	 * uebergeben werden.<br>
	 * in bestimmten Betriebszustaenden wird das Icon animiert und daher
	 * kontinuierlich veraendert.
	 * 
	 * @param currentIcon Das Icon, das im Fenster und in der Taskleiste angezeigt
	 *                    werden soll.
	 */
	public void setIcon(Image currentIcon);
}
