package backup.system;

import backup.system.services.BackupService;
import backup.system.services.FindDriveService;
import backup.system.services.SearchDriveService;
import backup.system.model.ConfigData;
import frontend.Gui;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        ConfigData configData = new ConfigData();
        FindDriveService drive = new FindDriveService();
        BackupService backupService = new BackupService(new Gui());
        SearchDriveService searchDriveService = new SearchDriveService(drive);

//        searchDrive.start();


        //Choose one of the listed drivers
        File currentDrive = drive.chooseDrive(drive.getDriveList());

        //initialize the driver with a config data
        configData.initConfigData(Paths.get(currentDrive + "config"));

        //get path of the config file on the external driver
        Path pathOfConfigOnExternal = Paths.get(currentDrive + "config");
        backupService.setPathOfConfig(pathOfConfigOnExternal);

        //create .Backup dir on user drive
        backupService.createBackupDir();

//        //create dir backup structure on user drive
        backupService.createDriveDir(pathOfConfigOnExternal);

//        //copies config data from external drive into backup structure on user drive
        backupService.copyConfig(pathOfConfigOnExternal);

        //make a backup from external drive into backup structure on user drive
//        backupService.startBackup(String.valueOf(currentDrive), backupService);

    }
}
