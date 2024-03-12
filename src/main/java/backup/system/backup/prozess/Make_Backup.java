package backup.system.backup.prozess;

import backup.system.drive.handler.Find_Drive;
import backup.system.uuid.handler.Get_UUID;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class Make_Backup implements FileVisitor<Path> {

    // TODO CHANGE C:/ ZU U:/
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        String[] splitPath = dir.toString().split(":", 2);
        Path target = Paths.get("C:" + splitPath[1]);

        //Ist die Directory im Ziel vorhanden? nein -> copyDir
        if(!Files.exists(target)){
            copy(new File(dir.toUri()), new File(target.toUri()));
            System.out.println("Directory has been copied");
        }

        //In Quelle nein und Ziel ja? Im Ziel löschen
        if (!Files.exists(dir) && Files.exists(target)){
            deleteFile(target);
            System.out.println("Directory was deleted");
        }

        //In Quelle und ziel ja? continue
        if (Files.exists(dir) && Files.exists(target)){
            return FileVisitResult.CONTINUE;
        }

        return FileVisitResult.CONTINUE;
    }

    //TODO CHANGE C:/ ZU U:/
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String[] splitPath = file.toString().split(":", 2);
        Path target = Paths.get("C:" + splitPath[1]);
        File targetFile = new File(target.toUri());
        File sourceFile = new File(file.toUri());

        //Ist File im Ziel vorhanden? nein -> copy
        if(!Files.exists(target)){
            copy(sourceFile, targetFile);
            System.out.println("File has been copied");
        }

        //In Quelle und ziel ja? vergleiche Attribut lastModified. lastModified ungleich? -> copy sonst continue
        if (Files.exists(file) && Files.exists(target)){
            FileTime fileTime = attrs.lastModifiedTime();
            if (new Date(fileTime.toMillis()).equals(new Date(targetFile.lastModified()))){
                return FileVisitResult.CONTINUE;
            } else {
                copy(sourceFile, targetFile);
                System.out.println("File has been copied");
            }
        }

        //In Quelle nein und Ziel ja? Im Ziel löschen
        if (!Files.exists(file) && Files.exists(target)){
            deleteFile(target);
            System.out.println("File was deleted");
            return FileVisitResult.CONTINUE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.out.println("Failed to access file: " + file.toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
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
     * und setzt dessen Attribut auf hidden
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
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

    /**
     * Diese Methode kopiert ein Verzeichnis von der Quellposition zu der Zielposition
     *
     * @param source Quellposition
     * @param target Zielposition
     */
    public void copyDirectory(File source, File target){
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
            copy(new File(source, f), new File(target, f));
        }
    }

    /**
     * Diese Methode kopiert ein File von der Quellposition zur Zielposition
     *
     * @param source Quellposition
     * @param target Zielposition
     */
    public void copyFile(File source, File target){
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(target)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This Method deletes a file or directory of the given path
     * @param path to delete
     */
    public void deleteFile(Path path){
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
