### Speichern unter Windows
Das Archivbit ist ein Dateiattribut, das in Microsoft-Betriebssystemen genutzt wird, um neu angelegte oder veränderte Dateien zu kennzeichnen. Datensicherungsprogrammen kann damit signalisiert werden, dass die Datei noch nicht gesichert bzw. seit der letzten Sicherung modifiziert wurde.

Je nach Backupstrategie wird das Archivbit unterschiedlich behandelt.

__Das Vollbackup__:  
Sichert alle Dateien, egal welchen Wert das Archivbit hat und setzt das Archivbit zurück.

__Die differenzielle Sicherung__:  
Sichert alle Dateien, die sich seit der letzten Vollsicherung verändert haben. Gesichert werden alle Dateien mit einem gesetzten Archivbit. Das Archivbit bleibt dabei unverändert.

__Das inkrementelle Backup__:  
Sichert alle Daten mit einem Archivbit und setzt die Archivbits zurück. Dadurch werden immer nur die veränderten Dateien seit der letzten Sicherung gespeichert.

***

### Speichern unter Linux

https://www.digitalocean.com/community/tutorials/how-to-use-rsync-to-sync-local-and-remote-directories-de

Rsync ist ein sehr flexibles, netzwerkfähiges Synchronisierungstool. Aufgrund seiner Allgegenwärtigkeit unter Linux- und Unix-ähnlichen Systemen und seiner Beliebtheit als Tool für Systemskripte, ist es in den meisten Linux-Distributionen standardmäßig enthalten.

Um den Inhalt von dir1 mit dir2 auf demselben System zu synchronisieren, wird folgendes comand eingesetzt:

```
rsync -r source destination
```
Die Option -r bedeutet rekursiv, das für die Verzeichnissynchronisation erforderlich ist.

```
rsync -a dir1/ dir2
```

Die Option -a ist ein Kombinations-Flag. Sie steht für „Archiv“ und synchronisiert rekursiv und erhält symbolische Links, spezielle und Gerätedateien, Modifizierungszeiten, Gruppe, Eigentümer und Berechtigungen.

Um zwei Verzeichnisse wirklich synchron zu halten, ist es notwendig, Dateien aus dem Zielverzeichnis zu löschen, wenn sie aus der Quelle entfernt werden. Standardmäßig löscht rsync nichts aus dem Zielverzeichnis.

```
rsync -a --delete dir1/ dir2
```

Schließlich kann die Option --backup von rsync verwendet werden, um Backups von wichtigen Dateien zu speichern. Sie wird in Verbindung mit der Option --backup-dir verwendet, die das Verzeichnis angibt, in dem die Backup-Dateien gespeichert werden sollen.

```
rsync -a --delete --backup --backup-dir=/path/to/backups /path/to/source destination
```

Dort werden die überschriebenen oder gelöschten Dateien als Kopie gespeichert. Damit ist eine Rekonstruktion der Dateien möglich
