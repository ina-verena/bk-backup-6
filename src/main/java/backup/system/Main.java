package backup.system;

import backup.system.backup.prozess.Make_Backup;
import backup.system.drive.handler.Find_Drive;
import backup.system.drive.handler.SearchDrive;
import backup.system.file.handler.ConfigData;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        ConfigData configData = new ConfigData();
        Find_Drive drive = new Find_Drive();
        Make_Backup make_backup = new Make_Backup();
        SearchDrive searchDrive = new SearchDrive(drive);

//        searchDrive.start();


        //Choose one of the listed drivers
        File currentDrive = drive.chooseDrive(drive.getDriveList());
        //initialize the driver with a config data
        configData.initConfigData(Paths.get(currentDrive + "config"));
        //get path of the config file on the external driver
        Path pathOfConfigOnExternal = Paths.get(currentDrive + "config");
        //create .Backup dir on user drive
        make_backup.createBackupDir();
        //create dir backup structure on user drive
        make_backup.createDriveDir(pathOfConfigOnExternal);
        //copies config data from external drive into backup structure on user drive
        make_backup.copyConfig(pathOfConfigOnExternal, currentDrive);
        //make a backup from external drive into backup structure on user drive
        Files.walkFileTree(Paths.get("E:/"), make_backup);

    }
}
