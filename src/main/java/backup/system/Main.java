package backup.system;

import backup.system.backup.prozess.Make_Backup;
import backup.system.drive.handler.Find_Drive;
import backup.system.file.handler.ConfigData;

import java.io.File;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        ConfigData configData = new ConfigData();
        Find_Drive drive = new Find_Drive();
        Make_Backup make_backup = new Make_Backup();
        File currentDrive = drive.chooseDrive(drive.getDriveList());
        configData.initConfigData(Paths.get(currentDrive + "config"));
        make_backup.createBackupDir();
        make_backup.createDriveDir(Paths.get(currentDrive + "config"), currentDrive);

    }
}
