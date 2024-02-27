package backup.system.drive.handler;

import backup.system.file.handler.ConfigData;

public class SearchDrive extends Thread{
    Find_Drive find_drive;

    public SearchDrive(Find_Drive find_drive){
        this.find_drive = find_drive;
    }

    /**
     * Ein Thread der regelmäßig Abschnitten kontrolliert,
     * ob ein Laufwerk angeschlossen oder entfernt wurde
     */
    public void run() {
        ConfigData configData = new ConfigData();
        while (true) {
            find_drive.visualizeDriveList(find_drive.getValidDriveList(configData));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
