package backup.system.backup.prozess;

import backup.system.uuid.handler.Get_UUID;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Make_Backup implements FileVisitor<Path> {


    //TODO Files.walkFileTree()
    @Override
    public FileVisitResult preVisitDirectory(
      Path dir, BasicFileAttributes attrs) {
        return null;
    }

    @Override
    public FileVisitResult visitFile(
      Path file, BasicFileAttributes attrs) {
        return null;
    }

    @Override
    public FileVisitResult visitFileFailed(
      Path file, IOException exc) {       
        return null;
    }

    @Override
    public FileVisitResult postVisitDirectory(
      Path dir, IOException exc) {    
        return null;
    }

    /**
     * Diese Methode kontrolliert das Dateisystem des externen Laufwerkes
     * und vergleicht diese mit dem Verzeichnis des externen Laufwerkes auf dem user Laufwerk
     * und füllt bei Änderungen die Backup-Liste mit dem Pfad des Files als String
     *
     * @return Object
     */
    public Object startVisitor(){
        return null;
    }

    /**
     * Diese Methode erzeugt eine Liste mit den Pfaden der Files als String,
     * die das Backup benötigt, um die Veränderungen innerhalb des Filesystems zu erkennen
     *
     * @param quelle position
     * @param ziel position
     * @return String Liste
     */
    public List<String> createBackupList(Path quelle, Path ziel){
        return null;
    }

    /**
     * Diese Methode startet das Backup und kopiert
     * alle Inhalte des externen Laufwerkes auf das User Laufwerk
     */
    public void startBackup(){}

    /**
     * Diese Methode ändert das Attribut des übergebenen files zu hidden
     * @param filePath Pfad des Files
     */
    private void hideFile(Path filePath){
        try {
            Files.setAttribute(filePath, "dos:hidden", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Diese Methode erzeugt den Backup-über-Ordner auf dem User Laufwerk
     */
    //TODO: Wechsel Buchstabe C:/ zu U:/
    public void createBackupDir(){

        try {
            Files.createDirectories(Paths.get("C:/.Backup"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        hideFile(Paths.get("C:/.Backup"));
    }

    /**
     * Diese Methode legt ein Verzeichnis auf dem User Laufwerk an,
     * in der die Backup-Struktur jeder externen Laufwerke erzeugt wird.
     * Es wird ein Backup über Ordner erstellt.
     * in diesem Ordner werden die Laufwerke mit den jeweiligen UUIDs als Ordnername erzeugt.
     * In den Laufwerkordner wird die zugehörigen Config Dateien kopiert sowie der Backup-Ordner erstellt
     *
     * @param path der Config Datei des externen Laufwerkes
     * @param aDrive Der Buchstabe des Laufwerkes
     */
    //TODO: Wechsel Buchstabe C:/ zu U:/
    public void createDriveDir(Path path, File aDrive){
        Get_UUID get_uuid = new Get_UUID();
        String uuid = get_uuid.getUUIDAsString(path);

        try {
            Files.createDirectories(Paths.get("C:/.Backup/" + uuid));
            Files.createDirectories(Paths.get("C:/.Backup/" + uuid + "/backup"));
            Files.copy(Paths.get(aDrive + "config"), Paths.get("C:/.Backup/" + uuid + "/config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Diese Methode kopiert ein File von
     * einer Quellposition zu einer Zielposition.
     * Dabei kontrolliert sie aber noch, ob das File Objekt ein Verzeichnis oder eine Datei ist
     *
     * @param sourceLocation Quellposition
     * @param targetLocation Zielposition
     */
    public void copy(File sourceLocation, File targetLocation){
        //Not implemented yet

    }

    /**
     * Diese Methode kopiert ein Verzeichnis von der Quellposition zu der Zielposition
     *
     * @param source Quellposition
     * @param target Zielposition
     */
    public void copyDirectory(File source, File target){
        //Not Implemented yet

    }

    /**
     * Diese Methode kopiert ein File von der Quellposition zur Zielposition
     *
     * @param source Quellposition
     * @param target Zielposition
     */
    public void copyFile(File source, File target){
        //Not implemented yet

    }
}
