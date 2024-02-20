package backup.system.backup.prozess;

import backup.system.file.handler.ConfigData;
import backup.system.uuid.handler.Get_UUID;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.List;

public class Make_Backup implements FileVisitor<Path> {

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

    public Object startVisitor(){
        return null;
    }

    public List<String> createBackupList(Path quelle, Path ziel){return null;}

    public void startBackup(){}


    private void hideFile(Path filePath){
        try {
            Files.setAttribute(filePath, "dos:hidden", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDir(){

        try {
            Files.createDirectories(Paths.get("C:/.Backup"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        hideFile(Paths.get("C:/.Backup"));
    }

    public void createDriveDir(Path path, ConfigData configData){
        Get_UUID get_uuid = new Get_UUID();
        String uuid = get_uuid.getUUIDAsString(path);

        try {
            Files.createDirectories(Paths.get("C:/.Backup/" + uuid));
            Files.createDirectories(Paths.get("C:/.Backup/" + uuid + "/backup"));
//            configData.setLastModified(Long.parseLong(LocalDateTime.now().toString()));
            configData.createConfigData(Paths.get("C:/.Backup/" + uuid + "/config"), configData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
