package backup.system.services;

import backup.system.model.ConfigData;

public class SearchDriveService extends Thread{
    FindDriveService find_driveService;

    public SearchDriveService(FindDriveService find_driveService){
        this.find_driveService = find_driveService;
    }

    /**
     * Ein Thread der in regelmäßigen Abschnitten kontrolliert,
     * ob ein Laufwerk angeschlossen oder entfernt wurde
     */
    public void run() {
        ConfigData configData = new ConfigData();
        while (true) {
            find_driveService.visualizeDriveList(find_driveService.getValidDriveList(configData));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
