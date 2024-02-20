package backup.system;

import backup.system.backup.prozess.Make_Backup;
import backup.system.drive.handler.Find_Drive;
import backup.system.file.handler.ConfigData;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws Exception {
        ConfigData configData = new ConfigData();
        Find_Drive drive = new Find_Drive();
        Make_Backup make_backup = new Make_Backup();
        UUID uuid = UUID.randomUUID();
//        configData.setUuid(uuid.toString());
//        configData.setLastModified(2123132141);
//        configData.createConfigData("C:/Development/config", configData);
        configData.initConfigData(Paths.get("C:/Development/config"));
        make_backup.createDir();
        make_backup.createDriveDir(Paths.get("C:/Development/config"), configData);

//        System.out.println(configData.searchConfigFile(Paths.get("C:/Development/config")));

//        configData = configData.loadFile(Paths.get("C:/Development/config"));
//        System.out.println(configData.getUuid());
//        System.out.println(configData.getLastModified());

//        FileSystemView fsv = FileSystemView.getFileSystemView();
//        File[] drives = drive.listAllDrives();
//        if (drives != null && drives.length > 0) {
//            for (File aDrive : drives) {
//                System.out.println("Drive Letter: " + aDrive);
//                System.out.println("\tType: " + fsv.getSystemTypeDescription(aDrive));
//                System.out.println("\tTotal space: " + drive.getTotalSpace(aDrive));
//                System.out.println("\tFree space: " + drive.getUsableSpace(aDrive));
//                System.out.println();
//            }
//        }
    }
}
