package backup.system.drive.handler;

import java.io.File;
import java.util.Scanner;

/**
 * Diese Klasse hat alle Funktionen, die für die Laufwerkerkennung vonnöten sind
 */
public class Find_Drive {
    /**
     * Diese Methode listet alle aktuell angeschlossenen Laufwerk auf
     * und fügt den Buchstaben der Laufwerke als File in ein File Array
     * @return File Array
     */
    public File[] getDriveList() {
        File[] drives = File.listRoots();
        return drives;
    }

    /**
     * Diese Methode interagiert mit dem User,
     * welcher sich per Auflistung für ein Laufwerk entscheidet
     * @param drives File Array
     * @return File - Laufwerkbuchstabe
     */
    public File chooseDrive(File[] drives){
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        System.out.println("Choose drive");
        for (int i = 0; i < drives.length; i++){
            System.out.println(counter + ": " + drives[i]);
            counter++;
        }

        return drives[scanner.nextInt()-1];

    }

    /**
     * Diese Methode interriert über das Array der Laufwerke und
     * kontrolliert jedes Laufwerk nach einer Config Datei mit einer UUID
     * @return File Array
     */
    public File[] getValidDriveList(){
        File[] drives = getDriveList();
//        for (:) {
//
//        }
        return null;
    }

    /**
     * Diese Methode gibt den benutzbaren Speicherplatz
     * eines Laufwerkes in Bytes als long an
     * @param aDrive kontrolliertes Laufwerk
     * @return long Wert
     */
    public long getUsableSpace(File aDrive) {
        long freeSpace = aDrive.getUsableSpace();
        return freeSpace;
    }

    /**
     * Diese Methode gibt den gesamten Speicherplatz
     * eines Laufwerkes in Bytes als long an
     * @param aDrive kontrolliertes Laufwerk
     * @return long Wert
     */
    public long getTotalSpace(File aDrive) {
        long totalSpace = aDrive.getTotalSpace();
        return totalSpace;
    }

}
