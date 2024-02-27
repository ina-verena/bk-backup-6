package backup.system.drive.handler;

import backup.system.file.handler.ConfigData;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Diese Klasse hat alle Funktionen, die für die Laufwerkerkennung vonnöten sind
 */
public class Find_Drive {
    /**
     * Diese Methode listet alle aktuell angeschlossenen Laufwerk auf
     * und fügt den Buchstaben der Laufwerke als File in ein File Array
     *
     * @return File Array
     */
    public File[] getDriveList() {
        File[] drives = File.listRoots();
//        for (int i = 0; i < drives.length; i++) {
//            if (drives[i].toString().equals("C:\\") || drives[i].toString().equals("U:\\")){
//                drives[i] = null;
//            }
//        }
        return drives;
    }

    public void visualizeDriveList(File[] drives){
        for (int i = 0; i < drives.length; i++){
            System.out.println(drives[i]);
        }
    }

    /**
     * Diese Methode interagiert mit dem User,
     * welcher sich per Auflistung für ein Laufwerk entscheidet
     * @param drives File Array
     * @return File - Laufwerkbuchstabe
     */
    public File chooseDrive(File[] drives){
        Scanner scanner = new Scanner(System.in);
        FileSystemView fsv = FileSystemView.getFileSystemView();
        int counter = 1;
        System.out.println("Choose drive");
        for (File drive : drives) {
            System.out.println(counter + ": " + drive);
            counter++;
        }

        return drives[scanner.nextInt()-1];

    }

    /**
     * Diese Methode interriert über das Array der Laufwerke und
     * kontrolliert jedes Laufwerk nach einer Config Datei mit einer UUID
     * @return File Array
     */
    public File[] getValidDriveList(ConfigData configData){
        File[] drives = getDriveList();
        File[] validDriveList = new File[drives.length];
        for (int i = 0; i < drives.length; i++) {
            if (!configData.doesConfigFileExist(Paths.get(drives[i] + "config"))){
                validDriveList[i] = drives[i];
            }
        }
        return validDriveList;
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
