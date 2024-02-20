package backup.system.drive.handler;

import java.io.File;
import java.util.UUID;

public class Drive {
    /**
     * This method lists all internal and external drives and puts them into an array
     * @return array of File's
     */
    public File[] listAllDrives() {
        File[] drives = File.listRoots();
        return drives;
    }

    public File[] getValidDriveList(){
        File[] drives = listAllDrives();
//        for (:) {
//
//        }
        return null;
    }

    /**
     * This method checks the
     * @param aDrive
     * @return
     */
    public long getUsableSpace(File aDrive) {
        long freeSpace = aDrive.getUsableSpace();
        return freeSpace;
    }

    public long getTotalSpace(File aDrive) {
        long totalSpace = aDrive.getTotalSpace();
        return totalSpace;
    }

}
