### System Idee

---

- Die Lehrer haben alle Schülerdaten zentral auf USB-DatenSticks
- Die Sticks werden mit Bitlocker verschlüsselt
- Die Sticks werden fertig vorbereitet von der Schulleitung zur Verfügung gestellt

---

- Backup des Sticks soll automatisiert erfolgen, ohne das der Lehrer eingreifen muss
- Sicherung erfolgt auf dem Userspace (Laufwerk U:\) im Schulnetz
- Stick soll beim einstecken im Schulnetz automatisch erkannt werden
- Es soll automatisch erkannt werden, ob ein Backup notwendig ist
- Ist es notwendig soll die _revers incrementelle_ Sicherung erfolgen
- Wird die Sicherung abgebrochen soll sie beim nächsten einstecken wiederholt werden

---

- Bei Verlust des Sticks kann eine Wiederherstellung auf einen neuen Stick erfolgen
- Die gesicherten Daten sollen Versioniert werden
- Die Rekunstruktion soll von einem beliebigen Zeitpunkt erfolgen können
- Optional: inzelne Dateien/Verzeichnis rekunstruktieren

---

#### Zusätzliche Ideen

- Möglichkeit zur manuellen Syncronisation?
- Ordner auf dem Stick für Dateien die nicht syncronisiert werden sollen?

---

### Rahmenbedingungen

- Bitlocker ist auf den Rechnern deaktiviert, ein Admin muss den Stick initialisieren
- Java muss nicht auf den Rechnern installiert sein
- Programm soll eine .exe sein (Muss sich jemand drum kümmern)
- Reicht der Speicherplatz des Users aus?
- Dateien dürfen nicht gesperrt durch aktive Programme und Virenscanner sein
- Agiele Entwicklung: Nur je ein Feature planen und ausführen
